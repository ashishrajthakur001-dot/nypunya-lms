package com.nypunya.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class RootController {

    @GetMapping("/")
    public Map<String, Object> root() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("service", "nypunya-lms-backend");
        response.put("status", "UP");
        response.put("message", "NypunyaLMS Spring Boot backend is running");
        response.put("health", "/api/health");
        return response;
    }

    @GetMapping("/api/health")
    public Map<String, Object> health() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "UP");
        response.put("service", "user-service");
        return response;
    }
}
