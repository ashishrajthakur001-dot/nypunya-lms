package com.nypunya.user.controller;

import com.nypunya.user.dto.UserCreateRequest;
import com.nypunya.user.entity.User;
import com.nypunya.user.security.RoleGuard;
import com.nypunya.user.service.UserManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserManagementService managementService;
    private final RoleGuard roleGuard;

    public UserController(UserManagementService managementService, RoleGuard roleGuard) {
        this.managementService = managementService; this.roleGuard = roleGuard;
    }

    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("authenticated", authentication != null && authentication.isAuthenticated());
        result.put("userId", authentication == null ? null : authentication.getName());
        result.put("roles", authentication == null ? new String[0] : authentication.getAuthorities().stream().map(a -> a.getAuthority()).toArray(String[]::new));
        return result;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> create(@Valid @RequestBody UserCreateRequest request, Authentication authentication) {
        if (!roleGuard.hasRole(authentication, "ADMIN")) throw new AccessDeniedException("Admin role required");
        User user = managementService.create(request);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("id", user.getId()); result.put("email", user.getEmail()); result.put("fullName", user.getFullName()); result.put("role", user.getRole());
        return result;
    }
}
