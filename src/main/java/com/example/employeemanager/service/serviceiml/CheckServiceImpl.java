package com.example.employeemanager.service.serviceiml;

import com.example.employeemanager.dto.CheckDTO;
import com.example.employeemanager.dto.CheckErrorDTO;
import com.example.employeemanager.entity.Check;
import com.example.employeemanager.entity.User;
import com.example.employeemanager.projection.CheckClose;
import com.example.employeemanager.projection.CheckOpen;
import com.example.employeemanager.repository.CheckRepository;
import com.example.employeemanager.service.CheckService;
import com.example.employeemanager.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.employeemanager.constant.Constant.CHECK_IN;
import static com.example.employeemanager.constant.Constant.CHECK_IN_EARLY;
import static com.example.employeemanager.constant.Constant.CHECK_IN_LATE;
import static com.example.employeemanager.constant.Constant.CHECK_OUT;
import static com.example.employeemanager.constant.Constant.CHECK_OUT_EARLY;
import static com.example.employeemanager.constant.Constant.CHECK_OUT_LATE;

@Service
public class CheckServiceImpl implements CheckService {
    private final CheckRepository checkRepository;
    private final UserService userService;
    public CheckServiceImpl(CheckRepository checkRepository, UserService userService) {
        this.checkRepository = checkRepository;
        this.userService = userService;
    }
    @Autowired
    ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(CheckServiceImpl.class);

    @Override
    public Check checkIn(int code) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(LocalTime.now().format(formatter));
        LocalTime time2 = LocalTime.of(8, 30);
        Check check = new Check();
        User user = userService.findByCode(code);
        check.setUser(user);
        check.setTimeCheck(LocalDateTime.now());
        check.setType(CHECK_IN);
        if (time.isAfter(time2))
            check.setStatus(CHECK_IN_LATE);
        if (time.isBefore(time2))
            check.setStatus(CHECK_IN_EARLY);
        if (LocalTime.now().equals(time2))
            check.setStatus("Check in ok");

        return checkRepository.save(check);
    }

    @Override
    public Check checkOut(int code) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(LocalTime.now().format(formatter));
        LocalTime time2 = LocalTime.of(17, 30);
        User user = userService.findByCode(code);
        Check check = new Check();
        check.setUser(user);
        check.setTimeCheck(LocalDateTime.now());
        check.setType(CHECK_OUT);
        if (time.isAfter(time2))
            check.setStatus(CHECK_OUT_LATE);
        if (time.isBefore(time2))
            check.setStatus(CHECK_OUT_EARLY);
        if (LocalTime.now().equals(time2))
            check.setStatus("Check out ok");
        return checkRepository.save(check);
    }


    @Override
    public List<CheckDTO> getListSelectDay(String start, String end, Long id) {
        List<CheckDTO> result = new ArrayList<CheckDTO>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(start, dateTimeFormatter);
        LocalDateTime localDateTime2 = LocalDateTime.parse(end, dateTimeFormatter);
        List<Check> entities = checkRepository.getCheckinsBetweenDatesById(localDateTime, localDateTime2, id);
        for (Check entity : entities) {
            CheckDTO checkDTO = modelMapper.map(entity, CheckDTO.class);
            checkDTO.setCode(entity.getUser() != null ? entity.getUser().getCode() : null);
            checkDTO.getUser().setUsername(entity.getUser().getUsername());
            result.add(checkDTO);
        }
        return result;
    }

    @Override
    public List<CheckErrorDTO> getErrorEmployeeInMonth(String start, String end) throws ParseException {
        List<CheckErrorDTO> checkErrorDTOS = new ArrayList<>();
        List<Check> checks = checkRepository.getErrorEmployeeInMonth(start, end);
        for (Check entity : checks) {
            CheckErrorDTO checkErrorDTO = modelMapper.map(entity, CheckErrorDTO.class);
            checkErrorDTO.setCode(entity.getUser() != null ? entity.getUser().getCode() : null);
            checkErrorDTO.getUser().setUsername(entity.getUser().getUsername());
            checkErrorDTO.getUser().setFullName(entity.getUser().getFullName());
            checkErrorDTOS.add(checkErrorDTO);
        }
        return checkErrorDTOS;

    }

    @Override
    public List<CheckDTO> getCheckinsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        List<CheckDTO> checkDTO=new ArrayList<>();
        List<Check> entities = checkRepository.getCheckinsBetweenDates(startDate, endDate);
        for (Check entity : entities)
            checkDTO.add(modelMapper.map(entity, CheckDTO.class));
        return checkDTO;

    }

    @Override
    public List<Check> findAll() {
        return checkRepository.findAll();
    }

    //close projection
    @Override
    public List<CheckClose> findByCheckClose() {
        return checkRepository.findBy(CheckClose.class);
    }
    //open
    @Override
    public List<CheckOpen> findByCheckOpen() {
        return checkRepository.findBy(CheckOpen.class);
    }
}
