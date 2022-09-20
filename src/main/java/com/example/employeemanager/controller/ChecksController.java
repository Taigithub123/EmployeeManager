package com.example.employeemanager.controller;

import com.example.employeemanager.dto.CheckDTO;
import com.example.employeemanager.dto.CheckErrorDTO;
import com.example.employeemanager.dto.DateDTO;
import com.example.employeemanager.service.CheckService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("api/v1/checks")
public class ChecksController {

    private final CheckService checkService;

    public ChecksController(CheckService checkService) {
        this.checkService = checkService;
    }

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
    @GetMapping("findError")
    public ResponseEntity<?> getListCheck(@RequestBody DateDTO dateDTO) throws ParseException {
        List<CheckErrorDTO> employee = checkService.getErrorEmployeeInMonth(dateDTO.getStartDate(), dateDTO.getEndDate());
        return ResponseEntity.ok().body(employee);
    }
    @GetMapping("all")
    @Cacheable("checkin")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(checkService.findAll());
    }
    @GetMapping("/close-projection")
    public ResponseEntity<?> getAllClose() {

        return ResponseEntity.ok(checkService.findByCheckClose());
    }
    @GetMapping("/open-projection")
    public ResponseEntity<?> getAllOpen() {

        return ResponseEntity.ok(checkService.findByCheckOpen());
    }

}
