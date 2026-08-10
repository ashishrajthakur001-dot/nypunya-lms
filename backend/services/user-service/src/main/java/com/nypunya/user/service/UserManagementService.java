package com.nypunya.user.service;

import com.nypunya.user.dto.UserCreateRequest;
import com.nypunya.user.entity.User;
import com.nypunya.user.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserManagementService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserManagementService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository; this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User create(UserCreateRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        if (repository.findByEmailIgnoreCase(email).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        User user = new User();
        user.setEmail(email);
        user.setFullName(request.getFullName().trim());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        try {
            return repository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Email already registered");
        }
    }
}
