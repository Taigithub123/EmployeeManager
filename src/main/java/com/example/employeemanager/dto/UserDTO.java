package com.example.employeemanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Data
public class UserDTO {
    private String username;
    private String fullName;
    private String email;
    private int code;
    @JsonIgnore
    private List<CheckDTO> checkins = new ArrayList<CheckDTO>();
    private Set<RoleDTO> roles ;
}
