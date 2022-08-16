package com.example.employeemanager.repository;

import com.example.employeemanager.entity.Role;
import com.example.employeemanager.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}