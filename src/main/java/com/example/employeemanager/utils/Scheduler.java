package com.example.employeemanager.utils;

import com.example.employeemanager.dto.CheckDTO;
import com.example.employeemanager.dto.UserDTO;
import com.example.employeemanager.service.CheckService;
import com.example.employeemanager.service.EmailService;
import com.example.employeemanager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class Scheduler {
    @Autowired
    CheckService checkService;
    @Autowired
    UserService userService;
    @Autowired
    EmailService emailService;
//    @Scheduled(cron = "0 58 22 ? * 1-7")
    @Scheduled(cron = "15 * * ? * *")
    public void checkinReminder() {
        List<CheckDTO> checkInToday = checkService.getCheckinsBetweenDates(
                LocalDateTime.now().minusDays(1L),
                LocalDateTime.now());
        List<UserDTO> listEmCheckInToday = new ArrayList<>();
        for (CheckDTO checkDTO : checkInToday) {
//            listEmCheckInToday.add(checkDTO.g)
            listEmCheckInToday.add(checkDTO.getUser());
        }
        List<UserDTO> userDTOList = userService.findAll();
//        if (userDTOList == null) {
//            return;
//        }
        for (UserDTO em : userDTOList) {
            if (!listEmCheckInToday.contains(em)) {
                StringBuilder content = new StringBuilder("<h1>Hi Elon Musk");
                content.append(em.getFullName());
                content.append(
                        "!<br> It's 17:30PM <br> Don't forget to checkout when you leave work, or -20.000$!!!</h1>");
                emailService.sendMail1(em.getEmail(), "Checkin reminder", content.toString());
            }
        }
    }
}
