package com.example.employeemanager.repository;

import com.example.employeemanager.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    User findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    List<User> findByFullNameOrderByFullNameAsc(String fullName, Sort sort);

    Optional<User> findByCode(int code);
//    @Query("select m from User m where m.FullName like ?1")
    List<User> findByFullNameLike(String name);




}