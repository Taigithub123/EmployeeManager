package com.example.employeemanager.dto;

import lombok.Data;

@Data
public class EmailMessage {
    private int code;
    private String email;
    private String fullName;
}
