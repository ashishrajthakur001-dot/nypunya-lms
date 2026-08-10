package com.nypunya.user.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "uk_user_email", columnNames = "email"), indexes = @Index(name = "idx_user_role", columnList = "role"))
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email @NotBlank @Column(nullable = false, length = 254)
    private String email;
    @NotBlank @Column(name = "password_hash", nullable = false, length = 100)
    private String passwordHash;
    @NotBlank @Column(name = "full_name", nullable = false, length = 160)
    private String fullName;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private Role role = Role.STUDENT;
    @Column(nullable = false)
    private boolean enabled = true;

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}
