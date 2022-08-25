package com.example.employeemanager.service;

import com.example.employeemanager.dto.CheckDTO;
import com.example.employeemanager.dto.CheckErrorDTO;
import com.example.employeemanager.entity.Check;
import com.example.employeemanager.projection.CheckClose;
import com.example.employeemanager.projection.CheckOpen;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


public interface CheckService {
    Check checkIn(int code);

    Check checkOut(int code);
//    List<CheckDTO> getListSelectDay(String start, String end, Long id);
    List<CheckDTO> getListSelectDay(String start, String end,Long id);
    List<CheckErrorDTO> getErrorEmployeeInMonth(String start, String end) throws ParseException;
    List<CheckDTO> getCheckinsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
    List<Check> findAll();
    List<CheckClose> findByCheckClose();
    List<CheckOpen> findByCheckOpen();
}
