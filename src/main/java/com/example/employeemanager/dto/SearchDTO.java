package com.example.employeemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchDTO {
    private long timeStart;
    private long timeEnd;
}
