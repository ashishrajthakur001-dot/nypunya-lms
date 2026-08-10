package com.nypunya.lms.entity;

import javax.persistence.*;

@Entity
@Table(name="user_credentials")
public class UserCredentials {
    @Id private Long userId;
    @Column(nullable=false) private String passwordHash;
    @OneToOne @MapsId private User user;
    protected UserCredentials() {}
    public UserCredentials(User user, String passwordHash){this.user=user;this.passwordHash=passwordHash;}
    public Long getUserId(){return userId;} public String getPasswordHash(){return passwordHash;} public User getUser(){return user;}
}
