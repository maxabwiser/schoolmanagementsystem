package com.example.schoolmanagement.student.service;

import com.example.schoolmanagement.student.domain.model.entity.Student;
import com.example.schoolmanagement.student.domain.repository.StudentRepository;
import com.example.schoolmanagement.student.dto.request.CreateStudentRequest;
import com.example.schoolmanagement.student.dto.response.CreateStudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateStudent {

    private final StudentRepository studentRepository;

    public CreateStudentResponse execute(CreateStudentRequest request) {
        Student student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setGradeLevel(request.getGradeLevel());

        Student saved = studentRepository.save(student);

        return new CreateStudentResponse(
                saved.getId(), saved.getFirstName(), saved.getLastName(), saved.getGradeLevel());
    }
}
