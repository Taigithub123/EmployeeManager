package com.example.employeemanager.webservice;

import com.example.employeemanager.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
@Slf4j
public class WebClient1 {
//    WebClient webClient = WebClient
//            .builder()
//            .baseUrl("https://jsonplaceholder.typicode.com/posts")
//            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//            .build();
    private final WebClient webClient;

    public WebClient1(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Employee> retrieveAllUser(){
        return  webClient.get().uri("todos")
                .retrieve()
                .bodyToFlux(Employee.class)
                .collectList()
                .block();

    }
    public Employee retrieveGetById(int employeeId){
        return  webClient.get().uri("todos/{id}",employeeId)
                .retrieve()
                .bodyToMono(Employee.class)
                .block();
    }
    public Employee addNewEmployee(Employee employee){
        return webClient.post().uri("todos")
                .bodyValue(employee)
                .retrieve()
                .bodyToMono(Employee.class)
                .block();
    }
    public Employee updateNewEmployee(int id, Employee employee){

            return webClient.put().uri("todos/{id}", id)
                    .bodyValue(employee)
                    .retrieve()
                    .bodyToMono(Employee.class)
                    .block();
    }
    public String deleteEmployeeById(int id) {
            return webClient.delete().uri("todos/{id}", id)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

    }

}
