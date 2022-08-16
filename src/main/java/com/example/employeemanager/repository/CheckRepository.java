package com.example.employeemanager.repository;

import com.example.employeemanager.dto.CheckDTO;
import com.example.employeemanager.entity.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface CheckRepository extends JpaRepository<Check, Long> {
    List<Check> findAllByUserBetween(Date start, Date end);

//    @Query("select c from Check c where c.timeCheck between ?1 and ?2")
//    List<Check> findByTimeCheckBetween(LocalDateTime timeCheckStart, LocalDateTime timeCheckEnd);
    @Query("SELECT c FROM Check c  WHERE timeCheck BETWEEN :startDate AND :endDate AND user_id = :id")
    List<Check> getCheckinsBetweenDatesById(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("id") Long id);




//    @Query("select c from Check c where c.timeCheck = :timeCheck")
//    List<Check> findByTimeCheck(@Param("timeCheck") String timeCheck);

//    @Query(value = "SELECT users.full_name, checks.time_check, checks.type FROM `checks`, `users` WHERE time_check BETWEEN :timeCheckStart " +
//            "and :timeCheckEnd AND checks.user_id = users.id", nativeQuery = true)
//    List<Check> findByTimeCheckBetween(@Param("timeCheckStart") String timeCheckStart, @Param("timeCheckEnd") String timeCheckEnd);


//    List<Check> findByTimeCheck(LocalDateTime timeCheck);

//    @Query("select new com.example.employeemanager.dto.CheckRespone(u.fullName, c.timeCheck, c.type) " +
//            "FROM Check c INNER JOIN c.user u ON u.user_id = u.id u WHERE timeCheck BETWEEN :x AND :y ")
//    @Query("SELECT c FROM Check c JOIN c.user u WHERE CONCAT(u.fullName, c.)")
//     List<Check> getUserByDate(@Param("x") LocalDateTime dateBefore, @Param("y")LocalDateTime dateAfter);
//    @Query("FROM Checkin c WHERE timeCheck BETWEEN :x AND :y AND user_id= :id")
//    List<Check> getUserByDate(@Param("x") LocalDateTime dateBefore, @Param("y")LocalDateTime dateAfter,@Param("id") Long id);


//    SELECT u.full_name, c.time_check, c.type
//    FROM checks As c, users AS u
//    WHERE (time_check BETWEEN '2022-08-08 13:47:41'
//            AND '2022-08-08 14:37:52') AND u.id=c.user_id



}