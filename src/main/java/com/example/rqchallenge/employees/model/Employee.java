package com.example.rqchallenge.employees.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
public class Employee {


    private Long id;
    @JsonAlias({"employee_name"})
    private String name;
    @JsonAlias({"employee_salary"})
    private Integer salary;
    @JsonAlias({"employee_age"})
    private Integer age;
    @JsonInclude(NON_NULL)
    @JsonAlias({"profile_image"})
    private String profileImage;

}
