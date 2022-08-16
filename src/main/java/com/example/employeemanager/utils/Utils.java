package com.example.employeemanager.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Utils {
    public int getFourDigit() {
        Random r = new Random();
        return  1000 + r.nextInt(10000);
    }
}
