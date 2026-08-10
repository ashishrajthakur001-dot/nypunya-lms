package com.nypunya.lms.entity;

import javax.persistence.*;

@Entity
@Table(name = "users", indexes = {@Index(name = "idx_users_email", columnList = "email")})
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true, length = 180) private String email;
    @Column(nullable = false, length = 120) private String fullName;
    @Column(nullable = false, length = 20) @Enumerated(EnumType.STRING) private Role role = Role.STUDENT;
    @Column(nullable = false) private boolean active = true;
    protected User() {}
    public User(String email, String fullName, Role role) { this.email=email; this.fullName=fullName; this.role=role; }
    public Long getId(){return id;} public String getEmail(){return email;} public String getFullName(){return fullName;} public Role getRole(){return role;} public boolean isActive(){return active;}
    public void setFullName(String v){this.fullName=v;} public void setRole(Role v){this.role=v;} public void setActive(boolean v){this.active=v;}
}
