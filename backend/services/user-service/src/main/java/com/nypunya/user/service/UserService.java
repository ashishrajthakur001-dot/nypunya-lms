package com.nypunya.user.service;

import com.nypunya.user.entity.User;
import com.nypunya.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository repository;
    public UserService(UserRepository repository) { this.repository = repository; }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return repository.findByEmailIgnoreCase(email.trim())
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }
}
