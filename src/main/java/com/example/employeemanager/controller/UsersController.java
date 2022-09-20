package com.example.employeemanager.controller;

import com.example.employeemanager.dto.*;
import com.example.employeemanager.entity.RefreshToken;
import com.example.employeemanager.entity.Role;
import com.example.employeemanager.entity.RoleEnum;
import com.example.employeemanager.entity.User;
import com.example.employeemanager.exception.*;
import com.example.employeemanager.projection.UserCount;
import com.example.employeemanager.repository.RoleRepository;
import com.example.employeemanager.repository.UserRepository;
import com.example.employeemanager.security.services.RefreshTokenService;
import com.example.employeemanager.security.services.UserDetailsImpl;
import com.example.employeemanager.service.CheckService;
import com.example.employeemanager.service.EmailService;
import com.example.employeemanager.service.UserService;
import com.example.employeemanager.utils.JwtUtils;
import com.example.employeemanager.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
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

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UsersController {
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    private final Utils utils;

    private final UserService userService;

    private final RefreshTokenService refreshTokenService;
    private final EmailService emailService;
    private final CheckService checkService;

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
    @PostMapping("auth/refreshtoken")
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
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
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

//    @GetMapping()
//    public User getUser(Principal principal) {
//        return userService.findByUsername(principal.getName());
//    }
    @GetMapping("all")
    public ResponseEntity<List<UserDTO>> all() {
        return ResponseEntity.ok(userService.findAll());
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
    @GetMapping("find-name/{name}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<List<UserDTO>> find(@PathVariable String name){
        return ResponseEntity.ok(userService.findByName(name));
    }
//    @GetMapping("sort")
//    @Secured({"ROLE_ADMIN"})
//    public ResponseEntity<List<User>> sortName(){
//        return ResponseEntity.ok(userService.sortName());
//    }
    @GetMapping("count")
    public ResponseEntity<List<UserCount>> count(){
    return new ResponseEntity<>(userService.countUserByUserName(),  HttpStatus.OK);
    }
    @GetMapping("find-page")
    public ResponseEntity<Page<User>> findPage(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                               @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                               @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
                                               @RequestParam("name") String fullname){
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("id").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("id").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        return ResponseEntity.ok().body(userService.findByNamePage(fullname,pageable));
    }
    @GetMapping("find-slice")
    public ResponseEntity<Slice<User>> findSlice(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                                @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
                                                @RequestParam("name") String fullname){
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("id").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("id").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        return ResponseEntity.ok().body(userService.findByNameSlice(fullname,pageable));
    }
    @GetMapping("Remind")
    public void CheckRemin() {
    List<CheckDTO> checkInToday = checkService.getCheckinsBetweenDates(
            LocalDateTime.now().minusDays(1L),
            LocalDateTime.now());
    List<UserDTO> listEmCheckInToday = new ArrayList<>();
    for (CheckDTO checkDTO : checkInToday) {
        listEmCheckInToday.add(checkDTO.getUser());
    }
    List<UserDTO> userDTOList = userService.findAll();
//    if (userDTOList == null) {
//        return;
//    }
    for (UserDTO userDTO : userDTOList) {
        if (!listEmCheckInToday.contains(userDTO)) {
            StringBuilder content = new StringBuilder("<h1>Hi Elon Musk");
            content.append(userDTO.getFullName());
            content.append(
                    "!<br> It's 17:30PM <br> Don't forget to checkout when you leave work, or -20.000$!!!</h1>");
            log.info("sdf"+userDTO.getEmail());
            emailService.sendMail1(userDTO.getEmail(), "Checkin reminder", content.toString());
        }
    }
}
}
