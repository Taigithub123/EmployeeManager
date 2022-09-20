package com.example.employeemanager.dto;

import com.example.employeemanager.entity.RoleEnum;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Data
@Setter
public class RoleDTO  {
    private Integer id;
    private RoleEnum name;
}
