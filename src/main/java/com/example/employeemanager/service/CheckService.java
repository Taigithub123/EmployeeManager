package com.example.employeemanager.service;

import com.example.employeemanager.dto.CheckDTO;
import com.example.employeemanager.entity.Check;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


public interface CheckService {
    Check checkIn(int code);

    Check checkOut(int code);
//    List<CheckDTO> getListSelectDay(String start, String end, Long id);
List<CheckDTO> getListSelectDay(String start, String end,Long id);
}