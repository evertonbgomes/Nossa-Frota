package com.progweb.frota.repository;

import com.progweb.frota.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
    List<User> findByRole(String role);
}
