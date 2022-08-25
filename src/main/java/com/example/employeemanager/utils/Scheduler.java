//package com.example.employeemanager.utils;
//
//import com.example.employeemanager.dto.CheckDTO;
//import com.example.employeemanager.dto.UserDTO;
//import com.example.employeemanager.service.CheckService;
//import com.example.employeemanager.service.EmailService;
//import com.example.employeemanager.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//@Component
//public class Scheduler {
//    @Autowired
//    CheckService checkService;
//    @Autowired
//    UserService userService;
//    @Autowired
//    EmailService emailService;
//    @Scheduled(cron = "0 21 15 ? * MON-FRI")
//    public void checkinReminder() {
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String formatDateTime = now.format(formatter);
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//
//        // Convert Timestamp object to LocalDateTime object
//        LocalDateTime localDateTime = timestamp.toLocalDateTime();
//        Calendar c = Calendar.getInstance();
//        c.setTime(Timestamp.valueOf(formatDateTime));
//        c.add(Calendar.DATE, 1);
//         Timestamp a=new Timestamp(c.getTimeInMillis());
//
////        List<CheckDTO> checkinToday = checkService.getCheckinsBetweenDates(Timestamp.valueOf(formatDateTime), a);
//        List<UserDTO> listEmCheckinToday = new ArrayList<>();
//        for (CheckDTO checkin : checkinToday) {
//            listEmCheckinToday.add(checkin.getUser());
//        }
//        List<UserDTO> listE = userService.findAll();
//
//        for (UserDTO em : listE) {
//            if (!listEmCheckinToday.contains(em)) {
//                StringBuilder content = new StringBuilder("<h1>Hi ");
//                content.append(em.getFullName());
//                content.append(
//                        "!<br> It's 17:30PM <br> Don't forget to checkout when you leave work, or -20.000vnđ!!!</h1>");
//                emailService. sendMail1(em.getEmail(), "Checkin reminder", content.toString());
//            }
//        }
//    }
//}
