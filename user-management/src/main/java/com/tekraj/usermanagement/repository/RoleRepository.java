package com.tekraj.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tekraj.usermanagement.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}