package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.repo.EmployeeAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class EmployeeService {

    private final EmployeeAPI employeeAPI;

    public EmployeeService(EmployeeAPI employeeAPI) {
        this.employeeAPI = employeeAPI;
    }

    public List<Employee> getAllEmployees() {
        log.info("getAllEmployees()");
        return employeeAPI.getAllEmployees();
    }

    public Employee getEmployeeById(String id) {
        log.info("getEmployeeById( {} )", id);
        return employeeAPI.getEmployeeById(id);
    }

    public Employee createEmployee(Map<String, Object> employeeInput) {
        log.info("createEmployee( {} )", employeeInput.entrySet());
        return employeeAPI.createEmployee(employeeInput);
    }

    public String deleteEmployee(String id) {
        log.info("deleteEmployee( {} )", id);
        return employeeAPI.deleteEmployee(id);
    }

    public int getHighestSalaryOfEmployees() {
        log.info("getHighestSalaryOfEmployees()");
        return getAllEmployees()
                .stream()
                .map(Employee::getSalary)
                .mapToInt(v -> v)
                .max()
                .orElseThrow(RuntimeException::new);
    }

    public List<String> getTopTenHighestEarningEmployeeNames() {
        log.info("getTopTenHighestEarningEmployeeNames()");
        return getAllEmployees().stream()
                .sorted(comparing(Employee::getSalary, Comparator.reverseOrder()))
                .limit(10)
                .map(Employee::getName)
                .collect(toList());
    }

    public List<Employee> getEmployeesByNameSearch(String searchString) {
        log.info("getEmployeesByNameSearch( {} )", searchString);
        return getAllEmployees()
                .stream()
                .filter(e -> e.getName().toLowerCase().contains(searchString.toLowerCase()))
                .collect(toList());
    }
}
