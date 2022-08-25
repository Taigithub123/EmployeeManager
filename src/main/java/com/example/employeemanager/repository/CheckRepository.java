package com.example.employeemanager.repository;

import com.example.employeemanager.dto.CheckDTO;
import com.example.employeemanager.entity.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface CheckRepository extends JpaRepository<Check, Long> {
    List<Check> findAllByUserBetween(Date start, Date end);

//    @Query("select c from Check c where c.timeCheck between ?1 and ?2")
//    List<Check> findByTimeCheckBetween(LocalDateTime timeCheckStart, LocalDateTime timeCheckEnd);
    @Query("SELECT c FROM Check c  WHERE timeCheck BETWEEN :startDate AND :endDate AND user_id = :id")
    List<Check> getCheckinsBetweenDatesById(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("id") Long id);
    @Query(value = "SELECT * FROM checks   WHERE time_check BETWEEN :startDate AND :endDate ", nativeQuery = true)
    List<Check> getErrorEmployeeInMonth(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query("SELECT c FROM Check c  WHERE timeCheck BETWEEN :startDate AND :endDate ")
    List<Check> getCheckinsBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    <T> List<T> findBy(Class<T> classType);
}