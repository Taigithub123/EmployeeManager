package com.example.employeemanager.webservice;

import com.example.employeemanager.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WebClient1Test {
private static final String baseURL="http://jsonplaceholder.typicode.com";
private WebClient webClient= WebClient.create(baseURL);
WebClient1 webClient1=new WebClient1(webClient);
@Test
void retrieveAllUser(){
    List<Employee> employeeList=webClient1.retrieveAllUser();
    System.out.println("list346346"+""+employeeList);
    assertTrue(employeeList.size()>0);
}
@Test
    void getById(){
    int employeeId=2;
    Employee employee=webClient1.retrieveGetById(employeeId);
    System.out.println("dfgdfg"+employee.getTitle());
}
@Test
    void add(){
    Employee employee=new Employee(10,0,"taideptrai",true);
    Employee employee1 = webClient1.addNewEmployee(employee);
    System.out.println("sdfsdf"+employee1);
    assertTrue(employee1.getId()!=null);
}
@Test
    void update(){
    int employeeId=2;
    Employee employee=new Employee(2,0,"taideptrainhat",false);
    Employee employee1 = webClient1.updateNewEmployee(employeeId,employee);
    System.out.println("sdfsdf"+employee1);
}
@Test
    void delete(){
    Employee employee = new Employee(0,2,null,false);
    String message = webClient1.deleteEmployeeById(employee.getId().intValue());
    System.out.println("serwer"+message);
}
}