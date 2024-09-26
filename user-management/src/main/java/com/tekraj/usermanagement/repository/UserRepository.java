package com.tekraj.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tekraj.usermanagement.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}