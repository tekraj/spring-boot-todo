package com.tekraj.schoolmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tekraj.schoolmanagement.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	User findByEmail(String email);

}
