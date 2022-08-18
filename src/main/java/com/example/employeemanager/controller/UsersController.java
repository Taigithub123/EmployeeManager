package com.example.employeemanager.controller;

import com.example.employeemanager.dto.*;
import com.example.employeemanager.entity.RefreshToken;
import com.example.employeemanager.entity.Role;
import com.example.employeemanager.entity.RoleEnum;
import com.example.employeemanager.entity.User;
import com.example.employeemanager.exception.*;
import com.example.employeemanager.repository.RoleRepository;
import com.example.employeemanager.repository.UserRepository;
import com.example.employeemanager.security.services.RefreshTokenService;
import com.example.employeemanager.security.services.UserDetailsImpl;
import com.example.employeemanager.service.EmailService;
import com.example.employeemanager.service.UserService;
import com.example.employeemanager.utils.JwtUtils;
import com.example.employeemanager.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UsersController {
    AuthenticationManager authenticationManager;

    UserRepository userRepository;

    RoleRepository roleRepository;

    PasswordEncoder encoder;

    JwtUtils jwtUtils;

    Utils utils;

    UserService userService;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    EmailService emailService;

    @PostMapping("auth/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        return ResponseEntity.ok(new JwtResponse(jwtToken, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles));
    }
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("auth/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignupRequest signUpRequest) {
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        user.setFullName(signUpRequest.getFullName());
        user.setCode(utils.getFourDigit());

        Set<Role> roles = new HashSet<>();

        if (signUpRequest.getRole() == 1) {
            Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                    .orElseThrow(() -> new BadRequestException(
                            new SysError(Errors.ERROR_ROLE_NOT_FOUND, new ErrorParam(Errors.ROLE))));
            roles.add(adminRole);
        } else {
            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new BadRequestException(
                            new SysError(Errors.ERROR_ROLE_NOT_FOUND, new ErrorParam(Errors.ROLE))));
            roles.add(userRole);
        }
        user.setRoles(roles);
        User save = userRepository.save(user);
        emailService.sendEmail(save);
        return ResponseEntity.ok("User registered successfully!");
    }

//    @GetMapping("/{fullName}")
//    @Secured("ROLE_ADMIN")
//    public ResponseEntity<?> findUserSort(@PathVariable("fullName") String fullName,
//                                          @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
//        Sort sortable = null;
//        if (sort.equals("ASC")) {
//            sortable = Sort.by("fullName").ascending();
//        }
//        if (sort.equals("DESC")) {
//            sortable = Sort.by("fullName").descending();
//        }
//        return ResponseEntity.ok(userService.findByFullNameSort(fullName, sortable));
//    }

    @GetMapping()
    public User getUser(Principal principal) {
        return userService.findByUsername(principal.getName());
    }

    @PostMapping("add")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> addUser(@RequestBody SignupRequest request) {
        // Create new user's account
        User user = new User(request.getUsername(),
                request.getEmail(),
                encoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setCode(utils.getFourDigit());
        Set<Role> roles = new HashSet<>();

        if (request.getRole() == 1) {
            Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                    .orElseThrow(() -> new BadRequestException(
                            new SysError(Errors.ERROR_ROLE_NOT_FOUND, new ErrorParam(Errors.ROLE))));
            roles.add(adminRole);
        } else {
            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new BadRequestException(
                            new SysError(Errors.ERROR_ROLE_NOT_FOUND, new ErrorParam(Errors.ROLE))));
            roles.add(userRole);
        }
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("Add successful!");
    }

    @GetMapping("update/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<User> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @PutMapping("update/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User user1) {
        return ResponseEntity.ok().body(userService.updateUser(id, user1));
    }

    @DeleteMapping("{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//    @GetMapping("find/{name}")
//    @Secured({"ROLE_ADMIN"})
//    public ResponseEntity<List<User>> find(@PathVariable String name){
//        return ResponseEntity.ok(userService.findByName(name));
//    }
//    @GetMapping("sort")
//    @Secured({"ROLE_ADMIN"})
//    public ResponseEntity<List<User>> sortName(){
//        return ResponseEntity.ok(userService.sortName());
//    }
}
