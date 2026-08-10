package com.nypunya.lms.service;

import com.nypunya.lms.dto.UserResponse;
import com.nypunya.lms.entity.Role;
import com.nypunya.lms.entity.User;
import com.nypunya.lms.repository.UserRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository repository;
    public UserService(UserRepository repository){this.repository=repository;}
    @Transactional(readOnly=true)
    public Page<UserResponse> list(Role role, Pageable pageable){
        Page<User> page = role == null ? repository.findAll(pageable) : repository.findByRole(role.name(), pageable);
        return page.map(UserResponse::new);
    }
    @Transactional(readOnly=true)
    public UserResponse get(Long id){ return new UserResponse(repository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"))); }
}
