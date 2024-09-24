package com.tekraj.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tekraj.todo.model.User;

public interface UserRepository  extends JpaRepository<User,Long>{
	  User findByEmail(String email);
}
