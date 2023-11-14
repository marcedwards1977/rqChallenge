package com.example.rqchallenge.employees.repo;

import com.example.rqchallenge.employees.model.Data;
import com.example.rqchallenge.employees.model.Employee;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class EmployeeAPI {

    private final WebClient webClient;

    public EmployeeAPI(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Employee> getAllEmployees() {
        Mono<Data<List<Employee>>> response = webClient.get()
                .uri("/api/v1/employees")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
        return response.block().getData();
    }

    public Employee getEmployeeById(String id) {
        Mono<Data<Employee>> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/employee/{id}")
                        .build(id)
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
        return response.block().getData();
    }

    public Employee createEmployee(Map<String, Object> employeeInput) {
        Mono<Data<Employee>> response = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/create")
                        .build())
                .body(fromValue(employeeInput))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
        return response.block().getData();
    }

    public String deleteEmployee(String id) {
        Mono<Data> response = webClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/delete/{id}")
                        .build(id)
                )
                .retrieve()
                .bodyToMono(Data.class);
        return response.block().getMessage();
    }
}
