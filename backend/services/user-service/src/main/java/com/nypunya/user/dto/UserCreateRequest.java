package com.nypunya.user.dto;

import com.nypunya.user.entity.Role;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserCreateRequest {
    @Email @NotBlank private String email;
    @NotBlank @Size(min = 8, max = 72) private String password;
    @NotBlank @Size(max = 160) private String fullName;
    @NotNull private Role role;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
