package com.example.employeemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {
    private String username;

    private String email;

    private String fullName;

    private String password;

    private int role;
}
