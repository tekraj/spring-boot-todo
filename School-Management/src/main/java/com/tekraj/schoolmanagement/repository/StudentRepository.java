package com.tekraj.schoolmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tekraj.schoolmanagement.entity.Student;

public interface StudentRepository extends JpaRepository<Student,Long> {

}
