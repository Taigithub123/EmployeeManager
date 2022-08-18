package com.example.employeemanager.service;

import com.example.employeemanager.entity.User;
import org.springframework.http.ResponseEntity;

public interface EmailService {
    String sendEmail(User user);
}
