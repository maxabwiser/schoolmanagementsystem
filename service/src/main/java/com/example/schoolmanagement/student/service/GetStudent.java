package com.example.schoolmanagement.student.service;

import com.example.schoolmanagement.exception.StudentNotFoundException;
import com.example.schoolmanagement.student.domain.model.entity.Student;
import com.example.schoolmanagement.student.domain.repository.StudentRepository;
import com.example.schoolmanagement.student.dto.response.GetStudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetStudent {

    private final StudentRepository studentRepository;

    public GetStudentResponse execute(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        return new GetStudentResponse(
                student.getId(), student.getFirstName(), student.getLastName(), student.getGradeLevel());
    }
}
