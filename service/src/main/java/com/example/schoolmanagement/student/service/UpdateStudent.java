package com.example.schoolmanagement.student.service;

import com.example.schoolmanagement.exception.StudentNotFoundException;
import com.example.schoolmanagement.student.domain.model.entity.Student;
import com.example.schoolmanagement.student.domain.repository.StudentRepository;
import com.example.schoolmanagement.student.dto.request.UpdateStudentRequest;
import com.example.schoolmanagement.student.dto.response.UpdateStudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateStudent {

    private final StudentRepository studentRepository;

    public UpdateStudentResponse execute(Long id, UpdateStudentRequest request) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setGradeLevel(request.getGradeLevel());

        Student saved = studentRepository.save(existing);

        return new UpdateStudentResponse(
                saved.getId(), saved.getFirstName(), saved.getLastName(), saved.getGradeLevel());
    }
}
