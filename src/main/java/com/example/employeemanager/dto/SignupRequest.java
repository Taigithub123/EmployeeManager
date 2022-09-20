package com.example.employeemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {
    @NotBlank(message = "username is mandatory")

    private String username;
    @NotBlank(message = "email is mandatory")
    @Email
    private String email;
    @NotBlank(message = "fullName is mandatory")
    private String fullName;
    @NotBlank(message = "password is mandatory")
    private String password;
    @NotNull(message = "role is mandatory")
    private int role;
}
