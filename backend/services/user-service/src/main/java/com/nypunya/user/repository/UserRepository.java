package com.nypunya.user.repository;

import com.nypunya.user.entity.Role;
import com.nypunya.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);
    Page<User> findByRole(Role role, Pageable pageable);
}
