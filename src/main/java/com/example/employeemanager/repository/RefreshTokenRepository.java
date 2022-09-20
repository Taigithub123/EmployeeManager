package com.example.employeemanager.repository;

import com.example.employeemanager.entity.RefreshToken;
import com.example.employeemanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Override
    Optional<RefreshToken> findById(Long id);
    Optional<RefreshToken> findByToken(String token);
    @Modifying
    int deleteByUser(User user);
}
