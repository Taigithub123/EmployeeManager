//package com.example.employeemanager.controller;
//
//import com.example.employeemanager.dto.EmailMessage;
//import com.example.employeemanager.entity.User;
//import com.example.employeemanager.service.EmailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("email")
//public class EmailController {
//
//    @Autowired
//    private EmailService emailService;
//
//    @PostMapping("/send-email")
//    public ResponseEntity<String> sendEmail(@RequestBody EmailMessage emailMessage) {
//        this.emailService.sendEmail(emailMessage.getCode(), emailMessage.getEmail(), emailMessage.getFullName());
//        return ResponseEntity.ok("Success");
//    }
//}
