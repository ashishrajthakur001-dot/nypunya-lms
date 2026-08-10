package com.nypunya.lms.controller;

import com.nypunya.lms.dto.UserResponse;
import com.nypunya.lms.entity.Role;
import com.nypunya.lms.service.UserService;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;
    public UserController(UserService service){this.service=service;}
    @GetMapping public Page<UserResponse> list(@RequestParam(required=false) Role role, @RequestParam(defaultValue="0") @Min(0) int page, @RequestParam(defaultValue="20") @Min(1) @Max(100) int size){ return service.list(role, PageRequest.of(page,size,Sort.by("id").descending())); }
    @GetMapping("/{id}") public UserResponse get(@PathVariable Long id){ return service.get(id); }
}
