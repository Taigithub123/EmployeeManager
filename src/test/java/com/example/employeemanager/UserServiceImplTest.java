package com.example.employeemanager;

import com.example.employeemanager.service.serviceiml.UserServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(UserServiceImpl.class)
//@SpringBootTest
public class UserServiceImplTest {
//    Employee employee = new Employee(1, "Todo Sample 1", null);
//    when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
//    Optional<Employee> resultOpt = employeeRepository.findById(1L);
//    Employee result = resultOpt.get();
//    assertEquals(1, result.getId());
//    assertEquals("Todo Sample 1", result.getName());
//    assertNull(result.getProjects());
}
