package com.example.employeemanager;

import com.example.employeemanager.dto.CheckDTO;
import com.example.employeemanager.entity.Check;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmployeeManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagerApplication.class, args);
    }

}
