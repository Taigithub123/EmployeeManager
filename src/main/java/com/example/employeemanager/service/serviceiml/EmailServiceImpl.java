package com.example.employeemanager.service.serviceiml;

import com.example.employeemanager.entity.User;
import com.example.employeemanager.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class EmailServiceImpl implements EmailService {
    private final SimpleMailMessage message = new SimpleMailMessage();
    private final Date date = new Date();
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public String sendEmail(User user) {
        message.setTo(user.getEmail());
        message.setSentDate(date);
        message.setSubject("Mã nhân viên của bạn");
        message.setText("Hello " + user.getFullName());
        message.setText("Your id_code " + user.getCode());
        javaMailSender.send(message);
        return "Sent message successfull";
    }
}
