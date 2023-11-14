package com.example.rqchallenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RqChallengeApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldGetAllEmployees() throws Exception {
        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(24)));
    }

    @Test
    void shouldGetOneEmployee() throws Exception {
        mockMvc.perform(get("/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tiger Nixon"))
                .andExpect(jsonPath("$.salary").value("320800"))
                .andExpect(jsonPath("$.age").value("61"));
    }

    @Test
    void shouldGetEmployeesByNameSearch() throws Exception {
        mockMvc.perform(get("/employee/search/paul"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0]name").value("Paul Byrd"));
    }

    @Test
    void shouldGetHighestSalaryOfEmployees() throws Exception {
        mockMvc.perform(get("/employee/highestSalary"))
                .andExpect(status().isOk())
                .andExpect(content().string("725000"));
    }

    @Test
    void shouldGetTopTenHighestEarningEmployeeNames() throws Exception {
        mockMvc.perform(get("/employee/topTenHighestEarningEmployeeNames"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect(jsonPath("$[*]", containsInAnyOrder(
                        "Paul Byrd",
                        "Yuri Berry",
                        "Charde Marshall",
                        "Cedric Kelly",
                        "Tatyana Fitzpatrick",
                        "Brielle Williamson",
                        "Jenette Caldwell",
                        "Quinn Flynn",
                        "Rhona Davidson",
                        "Tiger Nixon")));
    }

    @Test
    void shouldCreateEmployee() throws Exception {

        Map<String, Object> input = new HashMap<>();
        input.put("name", "marc");
        input.put("salary", "100");
        input.put("age", "25");

        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2728"));
    }

    @Test
    void shouldDeleteEmployee() throws Exception {
        mockMvc.perform(delete("/employee/6"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully! Record has been deleted"));
    }

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("url.base", wireMockServer::baseUrl);
    }
}
