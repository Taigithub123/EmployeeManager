package com.example.employeemanager.projection;

public interface CheckClose {
     Long getId();
     String getType();
     User1 getUser();
     interface User1 {
         String getFullName();
         String getUsername();
     }

 }
