package com.example.employeemanager.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Data
@NoArgsConstructor
public class CheckDTO {
    private int code;
    private String status;
    private LocalDateTime timeCheck;
    private String type;
    private UserDTO user;

}
