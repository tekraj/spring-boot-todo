package com.tekraj.schoolmanagement.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tekraj.schoolmanagement.entity.Student;
import com.tekraj.schoolmanagement.repository.StudentRepository;

@Controller
@RequestMapping("/admin/students")
public class StudentController {
    
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping({"", "/"})
    public String listStudents(Model model) {
        List<Student> students = studentRepository.findAll().stream()
                .map(student -> {
                    student.setDateOfBirthFormatted(student.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    return student;
                }).collect(Collectors.toList());
        
        model.addAttribute("students", students);
        return "admin/students/index"; 
    }

    @GetMapping("/add")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        return "admin/students/create-form"; 
    }

    @PostMapping("/store")
    public String createStudent(@ModelAttribute Student student, Model model) {
        studentRepository.save(student);
        return "redirect:/admin/students"; 
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
        	Student studentData = student.get();
        	studentData.setDateOfBirthFormatted(studentData.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            model.addAttribute("student", studentData);
            return "admin/students/update-form"; 
        } else {
            return "redirect:/admin/students"; 
        }
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student updatedStudent, Model model) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();
            student.setName(updatedStudent.getName());
            student.setAddress(updatedStudent.getAddress());
            student.setParentContactInfo(updatedStudent.getParentContactInfo());
            student.setGrade(updatedStudent.getGrade());
            if (updatedStudent.getDateOfBirthFormatted() != null && !updatedStudent.getDateOfBirthFormatted().isEmpty()) {
                try {
                    LocalDate dateOfBirth = LocalDate.parse(updatedStudent.getDateOfBirthFormatted());
                    student.setDateOfBirth(dateOfBirth);
                } catch (DateTimeParseException e) {
                    model.addAttribute("error", "Invalid date format. Please use 'yyyy-MM-dd'.");
                    model.addAttribute("student", existingStudent);
                    return "admin/students/update"; // Return to the update form with error
                }
            }
            studentRepository.save(student); 
        }
        return "redirect:/admin/students";  
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if(existingStudent.isPresent()) {
            studentRepository.delete(existingStudent.get());
        }
        return "redirect:/admin/students";  
    }
}
