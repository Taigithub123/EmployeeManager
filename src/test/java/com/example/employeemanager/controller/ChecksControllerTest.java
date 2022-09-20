//package com.example.employeemanager.controller;
//
////import org.junit.jupiter.api.Test;
//import com.example.employeemanager.EmployeeManagerApplication;
//import com.example.employeemanager.entity.Check;
//import com.example.employeemanager.entity.User;
//import com.example.employeemanager.service.CheckService;
//import com.example.employeemanager.service.UserService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.mock;
//import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
////@RunWith(SpringRunner.class)
////@ContextConfiguration(classes= EmployeeManagerApplication.class)
//@WebMvcTest(ChecksController.class)
////@SpringBootTest
////@AutoConfigureMockMvc
//public class ChecksControllerTest {
//    @MockBean
//    private CheckService checkService;
//    @Autowired
//    private MockMvc mvc;
//    @Test
//    public void getAll() throws Exception{
//        List<Check> allChecks = IntStream.range(0, 10)
//                .mapToObj(i -> new Check((long) i,null,null,"type-"+i,""+i))
//                .collect(Collectors.toList());
////        given(checkService.findAll()).willReturn(allChecks);
////        Mockito.when(checkService.findAll()).thenReturn(allChecks);
////        mvc.perform(get("/api/v1/checks/all").contentType(MediaType.APPLICATION_JSON))
////                .andExpect(status().isOk()) // Mong muốn Server trả về status 200
////                .andExpect(jsonPath("$", hasSize(10))); // Hi vọng server trả về List độ dài 10
//
//        mvc.perform(MockMvcRequestBuilders.get("/api/v1/checks/all"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                .andExpect(jsonPath("$", hasSize(2)));
//
//    }
//
//}