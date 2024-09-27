package com.tekraj.schoolmanagement.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tekraj.schoolmanagement.entity.Student;
import com.tekraj.schoolmanagement.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Transactional
	public void softDelete(Long id) {
		Optional<Student> optionalStudent = studentRepository.findById(id);
		if(optionalStudent.isPresent()) {
			Student student = optionalStudent.get();
			student.setDeletedAt(LocalDate.now());
			studentRepository.save(student);
		}
	}
}
