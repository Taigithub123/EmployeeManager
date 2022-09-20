//package com.example.employeemanager.controller;
//
//import com.example.employeemanager.dto.UserDTO;
//import com.example.employeemanager.repository.UserRepository;
//import com.example.employeemanager.service.CheckService;
//import com.example.employeemanager.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//@SpringBootTest
//@WebMvcTest(UsersController.class)
//public class UsersControllerTest {
//    @Autowired
//    private MockMvc mvc;
//    @Autowired
//    CheckService checkService;
//    @MockBean
//    private UserService userService;
//    @Autowired
//    private UserRepository userRepository;
//    @Test
//    public void find() throws Exception {
//    assertTrue(userRepository.existsById(4L));
//        mvc.perform(get("/employee-management/find/{id}",4)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//}