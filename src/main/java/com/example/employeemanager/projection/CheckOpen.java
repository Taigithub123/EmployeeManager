package com.example.employeemanager.projection;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface CheckOpen {
    Long getId();
    LocalDateTime getTimeCheck();
    String getType();
    @Value("#{target.timeCheck.toString() + ' ' + target.Type}")
    String getTimeType();
}
