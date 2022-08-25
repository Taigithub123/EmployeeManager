package com.example.employeemanager.controller;

import com.example.employeemanager.dto.EmailMessage;
import com.example.employeemanager.entity.User;
import com.example.employeemanager.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.Map;

@RestController
@RequestMapping("email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendmail")
    public ResponseEntity<String> sendEmail(@RequestBody Map<String, String> json) throws MessagingException {
        String subject = json.get("subject");
        String content = json.get("content");

        return new ResponseEntity<>(emailService.sendMail1("thaitaidang99@gmail.com", subject, content), HttpStatus.OK);
    }
}
