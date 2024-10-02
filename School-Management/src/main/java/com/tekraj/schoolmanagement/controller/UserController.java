package com.tekraj.schoolmanagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tekraj.schoolmanagement.entity.User;
import com.tekraj.schoolmanagement.enums.Role;
import com.tekraj.schoolmanagement.repository.UserRepository;

@Controller
@RequestMapping("/admin/users")
public class UserController {
	@Autowired
    private UserRepository userRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@GetMapping({"","/"})
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll(); 
        model.addAttribute("users", users);
        return "admin/users/index"; 
    }
	@GetMapping("/add")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        return "admin/users/create-form"; 
    }
	@PostMapping("/store")
	 public String createUser(@ModelAttribute User user, Model model) {
		 if (!user.getPassword().equals(user.getConfirmPassword())) {
	            model.addAttribute("error", "Passwords do not match.");
	            return "admin/users/create-form"; 
	        }
		 	user.setPassword(passwordEncoder.encode(user.getPassword()));
	        userRepository.save(user);
	        return "redirect:/admin/users";
    }
	@GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
        	User userData = user.get();
        	userData.setPassword(null);
            model.addAttribute("user",userData);
            model.addAttribute("roles", Role.values());
            return "admin/users/update-form"; 
        } else {
            return "redirect:/admin/users"; 
        }
    }
	
	@PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User updatedUser,Model model) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setRole(updatedUser.getRole());
            if(user.getPassword()!=null && !updatedUser.getPassword().isEmpty()) {
            	if(user.getPassword().matches(user.getConfirmPassword())) {
            		user.setPassword(passwordEncoder.encode(user.getPassword()));
            	}else {
            		 model.addAttribute("error", "Passwords do not match.");
            		 model.addAttribute("user",user);
            		 return "admin/users/update-form";
            	}
            }
            userRepository.save(user); 
        }
        return "redirect:/admin/users";  
    }
	
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
		 Optional<User> existingUser = userRepository.findById(id);
		 if(existingUser.isPresent()) {
			 userRepository.delete(existingUser.get());
		 }
		 return "admin/users/index";
	}
}
