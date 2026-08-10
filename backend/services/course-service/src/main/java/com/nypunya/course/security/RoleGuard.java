package com.nypunya.course.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class RoleGuard {
    public boolean hasRole(Authentication authentication, String role) {
        return authentication != null && authentication.isAuthenticated()
                && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }
}
