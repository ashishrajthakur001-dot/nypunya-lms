package com.nypunya.user.service;

import com.nypunya.user.dto.LoginRequest;
import com.nypunya.user.entity.User;
import com.nypunya.user.security.JwtService;
import com.nypunya.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository users, PasswordEncoder encoder, JwtService jwtService) {
        this.users = users; this.encoder = encoder; this.jwtService = jwtService;
    }

    public String login(LoginRequest request) {
        User user = users.findByEmailIgnoreCase(request.getEmail().trim())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if (!user.isEnabled() || !encoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return jwtService.issue(user.getId(), user.getEmail(), user.getRole().name());
    }
}
