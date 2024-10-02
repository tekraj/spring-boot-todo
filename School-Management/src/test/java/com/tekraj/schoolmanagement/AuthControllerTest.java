package com.tekraj.schoolmanagement;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tekraj.schoolmanagement.controller.AuthController;
import com.tekraj.schoolmanagement.entity.User;
import com.tekraj.schoolmanagement.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=AuthController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})  

public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void testRegisterUser_Success() throws Exception {
        // Mock the behavior of password encoder
        Mockito.when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        // Perform POST request with valid form data
        mockMvc.perform(post("/register")
                .param("username", "john.doe")
                .param("password", "password123")
                .param("confirmPassword", "password123")
        )
                .andExpect(status().is3xxRedirection())  // Expect a redirection status
                .andExpect(redirectedUrl("/login"));  // Expect to be redirected to /login

        // Verify that the UserService was called to save the user
        Mockito.verify(userService, Mockito.times(1)).saveUser(Mockito.any(User.class));
    }

    @Test
    public void testRegisterUser_PasswordMismatch() throws Exception {
        // Perform POST request where passwords do not match
        mockMvc.perform(post("/register")
                .param("username", "john.doe")
                .param("password", "password123")
                .param("confirmPassword", "password456")
        )
                .andExpect(status().isOk())  
                .andExpect(model().attributeExists("error"))  
                .andExpect(model().attribute("error", "Passwords do not match."))  
                .andExpect(model().attributeExists("user"))  
                .andExpect(model().attribute("user", Mockito.any(User.class))); 
    }
}
