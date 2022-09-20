package com.example.employeemanager.service.serviceiml;

import com.example.employeemanager.convert.UserConvert;
import com.example.employeemanager.dto.UserDTO;
import com.example.employeemanager.entity.User;
import com.example.employeemanager.exception.*;
import com.example.employeemanager.projection.UserCount;
import com.example.employeemanager.repository.RoleRepository;
import com.example.employeemanager.repository.UserRepository;
import com.example.employeemanager.service.UserService;
import com.example.employeemanager.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserConvert userConvert;
    private final UserRepository userRepository;
    private final Utils utils;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;


    public UserServiceImpl(UserRepository userRepository, Utils utils, PasswordEncoder encoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.utils = utils;
        this.encoder = encoder;
        this.roleRepository = roleRepository;

    }


    @Override
    public List<User> findByFullNameSort(String fullName, Sort sort) {
        return userRepository.findByFullNameOrderByFullNameAsc(fullName, sort);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> all = userRepository.findAll();
        List<UserDTO> userDTOS = userConvert.toDto(all);
        return userDTOS;
    }
    //        List<CheckErrorDTO> checkErrorDTOS = new ArrayList<>();
//        List<Check> checks = checkRepository.getErrorEmployeeInMonth(start, end);
//        for (Check entity : checks) {
//            CheckErrorDTO checkErrorDTO = modelMapper.map(entity, CheckErrorDTO.class);
//            checkErrorDTO.setCode(entity.getUser() != null ? entity.getUser().getCode() : null);
//            checkErrorDTO.getUser().setUsername(entity.getUser().getUsername());
//            checkErrorDTO.getUser().setFullName(entity.getUser().getFullName());
//            checkErrorDTOS.add(checkErrorDTO);
//        }
//        return checkErrorDTOS;
    @Override
    public User findByCode(int code) {
        return userRepository.findByCode(code).orElseThrow(() -> {
            throw new ResourceNotFoundExeption("Cant not found user with code: " + code);
        });
    }


    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExeption("nhân viên không tồn tại" + id));
    }
    @Transactional(rollbackOn = Exception.class)
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

    @Override
    public List<UserDTO> findByName(String name) {
        List<UserDTO> userDTOS = new ArrayList<>();
        List<User> users=userRepository.findByFullNameContains(name);
        for (User user : users) {
            userDTOS.add(modelMapper.map(user, UserDTO.class));
        }
        return  userDTOS;
    }

    @Override
    public List<UserCount> countUserByUserName() {
        return userRepository.countTotalUsersByUsernameClass();
    }

    @Override
    @Cacheable("user")
    public Page<User> findByNamePage(String fullname, Pageable pageable) {
        return userRepository.findByFullNameContains(fullname, pageable);
    }

    @Override
    public Slice<User> findByNameSlice(String fullname, Pageable pageable) {
        return userRepository.findByFullNameLike(fullname,pageable);
    }
//    @Override
//    public List<User> sortName() {
//        List<User> sortedName = userRepository.findAll().stream()
//                .sorted(Comparator.comparing(User::getFullName))
//                .collect(Collectors.toList());
//        return sortedName;
//    }

    }

