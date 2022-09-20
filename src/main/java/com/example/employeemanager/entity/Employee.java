package com.example.employeemanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {
    private long userId;
    private Integer id;
    private String title;
    private boolean completed;
}
