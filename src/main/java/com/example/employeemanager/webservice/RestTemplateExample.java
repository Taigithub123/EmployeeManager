package com.example.employeemanager.webservice;
import com.example.employeemanager.dto.UserDTO;
import com.example.employeemanager.entity.Employee;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class RestTemplateExample {
    static final String URL_EMPLOYEES = "https://jsonplaceholder.typicode.com/todos";
    static final String URL_EMPLOYEES_XML = "http://localhost:8080/api/v1/user/all.xml";
    static final String URL_EMPLOYEES_JSON = "http://localhost:8080/api/v1/user/all.json";
    static RestTemplate restTemplate = new RestTemplate();
    public static void main(String[] args) {
        callGetAllUserApi();
    }
    private static void callGetAllUserApi(){
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
//        UserDTO[] list = restTemplate.getForObject(URL_EMPLOYEES, UserDTO[].class);
//        if (list != null) {
//            for (UserDTO e : list) {
//                System.out.println("Employee: " + e.getFullName() + " - " + e.getEmail());
//            }
//        }
        // HttpHeaders
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        // Yêu cầu trả về định dạng XML
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("my_other_key", "my_other_value");

        // HttpEntity<Employee[]>: To get result as Employee[].
        HttpEntity<Employee[]> entity = new HttpEntity<Employee[]>(headers);

        // Gửi yêu cầu với phương thức GET, và các thông tin Headers.
        ResponseEntity<Employee[]> response = restTemplate.exchange(URL_EMPLOYEES, //
                HttpMethod.GET, entity, Employee[].class);
        HttpStatus statusCode = response.getStatusCode();
        System.out.println("Response Satus Code: " + statusCode);
        // Status Code: 200
        if (statusCode == HttpStatus.OK) {
            // Response Body Data
            Employee[] list = response.getBody();
            if (list != null) {
                for (Employee e : list) {
                    System.out.println("Employee: " +e);
                }
            }
        }
    }
}
