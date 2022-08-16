package com.example.employeemanager.service.serviceiml;

import com.example.employeemanager.dto.SignupRequest;
import com.example.employeemanager.entity.Role;
import com.example.employeemanager.entity.RoleEnum;
import com.example.employeemanager.entity.User;
import com.example.employeemanager.exception.*;
import com.example.employeemanager.repository.RoleRepository;
import com.example.employeemanager.repository.UserRepository;
import com.example.employeemanager.service.UserService;
import com.example.employeemanager.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    Utils utils;
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;



    @Override
    public List<User> findByFullNameSort(String fullName, Sort sort) {
        return userRepository.findByFullNameOrderByFullNameAsc(fullName, sort);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    @Override
    public User findByCode(int code) {
        return userRepository.findByCode(code).orElseThrow(() -> {
            throw new ResourceNotFoundExeption("Cant not found user with code: " + code);
        });
    }

//    @Override
//    public  String addUser(SignupRequest request) {
//        User  user= new User(request.getUsername(),
//                request.getEmail(),
//                encoder.encode(request.getPassword()));
//        user.setFullName(request.getFullName());
//        user.setCode(utils.getFourDigit());
//        Set<Role> roles = new HashSet<>();
//        if (request.getRole() == 1) {
//            Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
//                    .orElseThrow(() -> new BadRequestException(
//                            new SysError(Errors.ERROR_ROLE_NOT_FOUND, new ErrorParam(Errors.ROLE))));
//            roles.add(adminRole);
//        } else {
//            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
//                    .orElseThrow(() -> new BadRequestException(
//                            new SysError(Errors.ERROR_ROLE_NOT_FOUND, new ErrorParam(Errors.ROLE))));
//            roles.add(userRole);
//        }
//        user.setRoles(roles);
//
//     userRepository.save(user);
//     return "thêm thành cong";
//    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExeption("nhân viên không tồn tại" + id));
    }

    @Override
    public User updateUser(long id, User user) {
        User updateUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("nhân viên không tồn tại" + id));
        updateUser.setFullName(user.getFullName());
        updateUser.setEmail(user.getEmail());
        updateUser.setUsername(user.getUsername());
        return userRepository.save(updateUser);
    }

    @Override
    public void deleteUser(long id) {
        User deleteUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("nhân viên không tồn tại" + id));
        userRepository.delete(deleteUser);
    }

//    @Override
//    public List<User> findByName(String name) {
//        return userRepository.findByFullNameLike(name);
//    }
//
//    @Override
//    public List<User> sortName() {
//        List<User> sortedName = userRepository.findAll().stream()
//                .sorted(Comparator.comparing(User::getFullName))
//                .collect(Collectors.toList());
//        return sortedName;
//    }

}
