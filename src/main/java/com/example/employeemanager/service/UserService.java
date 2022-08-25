package com.example.employeemanager.service;

import com.example.employeemanager.dto.UserDTO;
import com.example.employeemanager.entity.User;
import com.example.employeemanager.projection.UserCount;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserService {

    List<User> findByFullNameSort(String fullName, Sort sort);

    User findByUsername(String username);
    List<UserDTO> findAll();
    User findByCode(int code);

//    String addUser(SignupRequest request);
    User findById(long id);
    User updateUser(long id, User user);
    void deleteUser(long id);
//    List<User> findByName(String name);
//    List<User> sortName();
    List<UserCount> countUserByUserName();
}
