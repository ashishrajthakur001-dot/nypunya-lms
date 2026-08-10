package com.nypunya.user.controller;

import com.nypunya.user.entity.User;
import com.nypunya.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;
    public UserController(UserService service) { this.service = service; }

    @GetMapping("/me")
    public Map<String, Object> me(@RequestHeader("Authorization") String authorization) {
        // The authentication filter establishes identity; this endpoint intentionally
        // returns only a minimal authenticated principal contract until the user DTO is expanded.
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("authenticated", true);
        return result;
    }
}
