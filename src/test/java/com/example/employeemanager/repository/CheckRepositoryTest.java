//package com.example.employeemanager.repository;
//
//import com.example.employeemanager.entity.Check;
//import org.assertj.core.api.Assertions;
//import org.junit.After;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.persistence.EntityManager;
//import javax.sql.DataSource;
//
//import static org.junit.jupiter.api.Assertions.*;
//@RunWith(SpringRunner.class)
//@DataJpaTest
//public class CheckRepositoryTest {
//    @Autowired
//    private CheckRepository checkRepository;
//    @Autowired
//    private DataSource dataSource;
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    @Autowired
//    private EntityManager entityManager;
//
//    @Test
//    public void findAll() {
//        Assertions.assertThat(dataSource).isNotNull();
//        Assertions.assertThat(jdbcTemplate).isNotNull();
//        Assertions.assertThat(entityManager).isNotNull();
//        Assertions.assertThat(checkRepository).isNotNull();
//    }
//    @Test
//    public void testTodoRepositoryByCode() {
//        checkRepository.save(new Check(1L,null,null));
//        checkRepository.save(new Check(0, "Todo-2", "Detail-2"));
//
//        Assertions.assertThat(checkRepository.findAll()).hasSize(2);
//
//    }
//
//    @After
//    public void clean() {
//        checkRepository.deleteAll();
//    }
//}