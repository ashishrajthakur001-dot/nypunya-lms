package com.nypunya.lms.dto;

import com.nypunya.lms.entity.User;
public class UserResponse {
    private final Long id; private final String email; private final String fullName; private final String role; private final boolean active;
    public UserResponse(User u){id=u.getId();email=u.getEmail();fullName=u.getFullName();role=u.getRole().name();active=u.isActive();}
    public Long getId(){return id;} public String getEmail(){return email;} public String getFullName(){return fullName;} public String getRole(){return role;} public boolean isActive(){return active;}
}
