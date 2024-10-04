package com.tekraj.schoolmanagement;

import com.tekraj.schoolmanagement.controller.StudentController;
import com.tekraj.schoolmanagement.entity.Student;
import com.tekraj.schoolmanagement.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test@gmail.com", password = "test@123", roles = {"ADMIN"})
public class StudentControllerTest {

	 @Mock
	    private StudentRepository studentRepository;

	    @InjectMocks
	    private StudentController studentController;

	    private MockMvc mockMvc;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
	    }

	    @Test
	    void listStudents() throws Exception {
	        // Given
	        Student student1 = new Student();
	        student1.setId(1L);
	        student1.setName("John Doe");
	        student1.setAddress("123 Main St");
	        student1.setParentContactInfo("123456789");
	        student1.setGrade(10);
	        student1.setDateOfBirth(LocalDate.of(2010, 1, 1));

	        Student student2 = new Student();
	        student2.setId(2L);
	        student2.setName("Jane Smith");
	        student2.setAddress("456 Elm St");
	        student2.setParentContactInfo("987654321");
	        student2.setGrade(11);
	        student2.setDateOfBirth(LocalDate.of(2009, 2, 2));

	        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));

	        // When & Then
	        mockMvc.perform(get("/admin/students"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("admin/students/index"))
	                .andExpect(model().attributeExists("students"))
	                ;
	    }

	    @Test
	    void showCreateForm() throws Exception {
	        mockMvc.perform(get("/admin/students/add"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("admin/students/create-form"))
	                .andExpect(model().attributeExists("student"));
	    }

	    @Test
	    void createStudent() throws Exception {
	        Student student = new Student();
	        student.setName("New Student");
	        student.setAddress("789 Oak St");
	        student.setParentContactInfo("555666777");
	        student.setGrade(9);
	        student.setDateOfBirth(LocalDate.of(2011, 3, 3));

	        // When
	        mockMvc.perform(post("/admin/students/store")
	                        .param("name", student.getName())
	                        .param("address", student.getAddress())
	                        .param("parentContactInfo", student.getParentContactInfo())
	                        .param("grade", String.valueOf(student.getGrade()))
	                        .param("dateOfBirthFormatted", "2011-03-03")) // You may need to adapt this based on your form setup
	                .andExpect(status().is3xxRedirection())
	                .andExpect(redirectedUrl("/admin/students"));

	        // Verify
	        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
	        verify(studentRepository).save(studentCaptor.capture());
	        assertEquals("New Student", studentCaptor.getValue().getName());
	    }

	    @Test
	    void showUpdateForm() throws Exception {
	        // Given
	        Student existingStudent = new Student();
	        existingStudent.setId(1L);
	        existingStudent.setName("Existing Student");
	        existingStudent.setAddress("111 Pine St");
	        existingStudent.setParentContactInfo("444555666");
	        existingStudent.setGrade(8);
	        existingStudent.setDateOfBirth(LocalDate.of(2012, 4, 4));

	        when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));

	        // When & Then
	        mockMvc.perform(get("/admin/students/edit/1"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("admin/students/update-form"))
	                .andExpect(model().attributeExists("student"));
	    }

	    @Test
	    void updateStudent() throws Exception {
	        // Given
	        Student existingStudent = new Student();
	        existingStudent.setId(1L);
	        existingStudent.setName("Existing Student");
	        existingStudent.setAddress("111 Pine St");
	        existingStudent.setParentContactInfo("444555666");
	        existingStudent.setGrade(8);
	        existingStudent.setDateOfBirth(LocalDate.of(2012, 4, 4));

	        when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));

	        // When
	        mockMvc.perform(post("/admin/students/update/1")
	                        .param("name", "Updated Student")
	                        .param("address", "222 Maple St")
	                        .param("parentContactInfo", "777888999")
	                        .param("grade", "9")
	                        .param("dateOfBirthFormatted", "2012-04-04"))
	                .andExpect(status().is3xxRedirection())
	                .andExpect(redirectedUrl("/admin/students"));

	        // Verify
	        verify(studentRepository).save(existingStudent);
	        assertEquals("Updated Student", existingStudent.getName());
	    }

	    @Test
	    void deleteStudent() throws Exception {
	        // Given
	        Student existingStudent = new Student();
	        existingStudent.setId(1L);
	        when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));

	        // When
	        mockMvc.perform(get("/admin/students/delete/1"))
	                .andExpect(status().is3xxRedirection())
	                .andExpect(redirectedUrl("/admin/students"));

	        // Verify
	        verify(studentRepository).delete(existingStudent);
	    }

	    @Test
	    void deleteNonExistingStudent() throws Exception {
	        // Given
	        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

	        // When
	        mockMvc.perform(get("/admin/students/delete/1"))
	                .andExpect(status().is3xxRedirection())
	                .andExpect(redirectedUrl("/admin/students"));

	        // Verify
	        verify(studentRepository, never()).delete(any());
	    }
}


