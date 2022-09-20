package com.example.employeemanager.repository;

import com.example.employeemanager.dto.UserDTO;
import com.example.employeemanager.entity.User;
import com.example.employeemanager.projection.UserCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    User findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    List<User> findByFullNameOrderByFullNameAsc(String fullName, Sort sort);


    Optional<User> findByCode(int code);
//    @Query("select m from User m where m.FullName like ?1")
    List<User> findByFullNameContains(String name);
    @Query("SELECT new com.example.employeemanager.projection.UserCount(u.fullName, COUNT(u.fullName)) "
            + "FROM User AS u GROUP BY u.fullName ORDER BY u.fullName DESC")
    List<UserCount> countTotalUsersByUsernameClass();

    @Query("select u from User u where u.fullName like concat('%', ?1, '%')")
    Page<User> findByFullNameContains(String fullName, Pageable pageable);

    @Query("select u from User u where u.fullName like concat(?1, '%')")
    Slice<User> findByFullNameLike(String fullName, Pageable pageable);

}