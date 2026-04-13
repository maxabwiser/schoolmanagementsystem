package com.example.schoolmanagement.student.service;

import com.example.schoolmanagement.exception.StudentNotFoundException;
import com.example.schoolmanagement.student.domain.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteStudent {

    private final StudentRepository studentRepository;

    public void execute(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }
}
