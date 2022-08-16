package com.example.employeemanager.controller;

import com.example.employeemanager.dto.CheckDTO;
import com.example.employeemanager.dto.DateDTO;
import com.example.employeemanager.dto.SearchDTO;
import com.example.employeemanager.entity.Check;
import com.example.employeemanager.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/checks")
public class ChecksController {
    @Autowired
    CheckService checkService;
    @GetMapping("in/{code}")
    public ResponseEntity<?> checkIn(@PathVariable int code) {
        return ResponseEntity.ok(checkService.checkIn(code));
    }

    @GetMapping("out/{code}")
    public ResponseEntity<?> checkOut(@PathVariable int code) {
        return ResponseEntity.ok(checkService.checkOut(code));
    }

    @GetMapping("{id}/findDate")
    public ResponseEntity<List<CheckDTO>> getListCheck(@RequestBody DateDTO dateDTO,@PathVariable long id) {
        return ResponseEntity.ok().body(checkService.getListSelectDay(dateDTO.getStartDate(),dateDTO.getEndDate(),id));
    }

}
