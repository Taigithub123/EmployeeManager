package com.example.employeemanager.service;

import com.example.employeemanager.dto.SignupRequest;
import com.example.employeemanager.entity.User;
import org.springframework.data.domain.Sort;

import java.security.Principal;
import java.util.List;

public interface UserService {

    List<User> findByFullNameSort(String fullName, Sort sort);

    User findByUsername(String username);

    User findByCode(int code);

//    String addUser(SignupRequest request);
    User findById(long id);
    User updateUser(long id, User user);
    void deleteUser(long id);
//    List<User> findByName(String name);
//    List<User> sortName();
}
