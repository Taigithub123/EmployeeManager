package com.example.employeemanager.service.serviceiml;

import com.example.employeemanager.entity.User;
import com.example.employeemanager.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class EmailServiceImpl implements EmailService {
    private final SimpleMailMessage message = new SimpleMailMessage();
    private final Date date = new Date();
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    @Async
    public String sendEmail(User user) {
        message.setTo(user.getEmail());
        message.setSentDate(date);
        message.setSubject("Mã nhân viên của bạn");
        message.setText("Hello " + user.getFullName());
        message.setText("Your id_code " + user.getCode());
        javaMailSender.send(message);
        return "Sent message successfull";
    }

    @Override
    @Async
    public String sendMail1(String email, String subject, String content) {
        message.setTo(email);
        message.setSentDate(date);
        message.setSubject(subject);
        message.setText(content);

        String htmlMsg = content + "<br><h2><b>Have a nice day!</b><br><font color=purple><b>Komu<b></font></h2>"
                + "<br><img src='https://ncc.asia/images/logo/logo.png'>";
        message.setText(htmlMsg);
        javaMailSender.send(message);
        return "Sent message successfull";

    }
}
