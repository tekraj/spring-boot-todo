package com.tekraj.schoolmanagement;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.tekraj.schoolmanagement.controller.HomeController;

@WebMvcTest(controllers=HomeController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})  
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/"))        	
                .andExpect(status().isOk())  
                .andExpect(view().name("index")) 
                .andExpect(model().attribute("title", "Home Page"));
    }

    @Test
    public void testAbout() throws Exception {
        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())  
                .andExpect(view().name("index"))  
                .andExpect(model().attribute("title", "About Page"));  
    }
}
