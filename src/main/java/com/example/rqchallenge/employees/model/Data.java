package com.example.rqchallenge.employees.model;

@lombok.Data
public class Data<T> {

    String status;
    T data;
    String message;

}
