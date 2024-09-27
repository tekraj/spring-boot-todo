package com.tekraj.schoolmanagement.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tekraj.schoolmanagement.entity.User;
import com.tekraj.schoolmanagement.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	@Autowired
	private  UserRepository userRepository;
	
	@Transactional
	public void softDelete(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setDeletedAt(LocalDate.now());
			userRepository.save(user);
		}		
	}
	
	public void saveUser(User user) {
        userRepository.save(user); // Save the user in the database
    }
	
}
