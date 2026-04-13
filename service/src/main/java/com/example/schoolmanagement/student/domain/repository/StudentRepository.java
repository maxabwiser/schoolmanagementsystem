package com.example.schoolmanagement.student.domain.repository;

import com.example.schoolmanagement.student.domain.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
