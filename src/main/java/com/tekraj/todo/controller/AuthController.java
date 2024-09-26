package com.tekraj.todo.controller;

import com.tekraj.todo.dto.UserDto;
import com.tekraj.todo.model.User;
import com.tekraj.todo.service.CustomUserDetailsService;
import com.tekraj.todo.service.UserService;
import com.tekraj.todo.util.JwtUtil;

import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@GetMapping("/login")
	public String showLoginForm() {
		return "index";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		UserDto user = new UserDto();
		model.addAttribute("user", user);
		return "register";
	}

	@PostMapping("/login")
	public String createAuthenticationToken(@RequestBody UserDto authRequest) throws Exception {
		User user = userService.findByEmail(authRequest.getEmail());
		if (user == null) {
			throw new Exception("User not found");
		}
		
		String hashedPassword = passwordEncoder.encode(authRequest.getPassword());

		if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
			return hashedPassword;
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());

		return jwtUtil.generateToken(userDetails.getUsername());
	}

	@PostMapping("/register")
	public String registerUser(@RequestBody User user) {
		userService.registerUser(user);
		return "User registered successfully";
	}

}
