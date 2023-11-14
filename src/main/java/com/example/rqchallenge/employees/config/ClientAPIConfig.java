package com.example.rqchallenge.employees.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.HttpHeaders.ACCEPT;

@Configuration
public class ClientAPIConfig {

    @Bean
    public WebClient webClient(@Value("${url.base}") String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
