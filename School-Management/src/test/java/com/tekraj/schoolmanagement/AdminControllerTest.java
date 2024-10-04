package com.tekraj.schoolmanagement;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test@gmail.com", password = "test@123", roles = {"ADMIN"})
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testDashboard() throws Exception {
        mockMvc.perform(get("/admin"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/index"));
    }
}
