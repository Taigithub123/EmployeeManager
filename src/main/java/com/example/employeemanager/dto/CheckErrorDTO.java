package com.example.employeemanager.dto;

import com.example.employeemanager.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Data
@NoArgsConstructor
public class CheckErrorDTO {
    private int code;
    private String status;
    private LocalDateTime timeCheck;
    private String type;
    private String fullName;
    private String email;
    @JsonIgnore
    private User user;
}
