package com.example.employeemanager.service;

import com.example.employeemanager.dto.UserDTO;
import com.example.employeemanager.entity.User;
import com.example.employeemanager.projection.UserCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    List<UserDTO> findByName(String name);
//    List<User> sortName();
    List<UserCount> countUserByUserName();
    Page<User> findByNamePage(String fullname, Pageable pageable);
//void CheckRemin();
    Slice<User> findByNameSlice(String fullname, Pageable pageable);
}
