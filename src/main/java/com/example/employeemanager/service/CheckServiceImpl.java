package com.example.employeemanager.service;

import com.example.employeemanager.dto.CheckDTO;
import com.example.employeemanager.entity.Check;
import com.example.employeemanager.entity.User;
import com.example.employeemanager.repository.CheckRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.employeemanager.constant.Constant.CHECK_IN;
import static com.example.employeemanager.constant.Constant.CHECK_OUT;

@Service
public class CheckServiceImpl implements CheckService {
    @Autowired
    CheckRepository checkRepository;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public Check checkIn(int code) {
        User user = userService.findByCode(code);
        Date date=new Date();
        Check check = new Check();
        check.setUser(user);
        check.setTimeCheck(LocalDateTime.now());
        check.setType(CHECK_IN);
        return checkRepository.save(check);
    }

    @Override
    public Check checkOut(int code) {
        User user = userService.findByCode(code);
        Date date=new Date();
        Check check = new Check();
        check.setUser(user);
        check.setTimeCheck(LocalDateTime.now());
        check.setType(CHECK_OUT);
        return checkRepository.save(check);
    }



    @Override
    public List<CheckDTO> getListSelectDay(String start, String end,Long id) {
//        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime localDateTime= LocalDateTime.parse(start, dateTimeFormatter);
//        LocalDateTime localDateTime2= LocalDateTime.parse(end, dateTimeFormatter);
//        List<Check> checks=checkRepository.findByTimeCheckBetween(localDateTime,localDateTime2);
//        checks.forEach(p->{
//        p.getUser();
//        p.getTimeCheck();
//        p.getType();
//        });
//        return checks;
        List<CheckDTO> result = new ArrayList<CheckDTO>();
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime= LocalDateTime.parse(start, dateTimeFormatter);
        LocalDateTime localDateTime2= LocalDateTime.parse(end, dateTimeFormatter);
        List<Check> entities = checkRepository.getCheckinsBetweenDatesById(localDateTime, localDateTime2,id);
        for (Check entity : entities){
            CheckDTO checkDTO= modelMapper.map(entity, CheckDTO.class);
            checkDTO.setCode(entity.getUser() != null ? entity.getUser().getCode() : null );
            checkDTO.getUser().setUsename(entity.getUser().getUsername());
        result.add(checkDTO);
        }
        return result;
    }
//    @Override
//    public List<Check> findcheck(String timeCheck) {
//        return checkRepository.findByTimeCheck(timeCheck);
//    }

//    @Override
//    public List<Check> getListSelectDay(LocalDateTime start, LocalDateTime end) {
//        return checkRepository.getUserByDate(start, end);
//    }


}
