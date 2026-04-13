package com.example.schoolmanagement.student.service;

import com.example.schoolmanagement.student.domain.model.entity.Student;
import com.example.schoolmanagement.student.domain.repository.StudentRepository;
import com.example.schoolmanagement.student.dto.response.GetAllStudentsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllStudents {

    private final StudentRepository studentRepository;

    public GetAllStudentsResponse execute() {
        List<Student> students = studentRepository.findAll();

        List<GetAllStudentsResponse.StudentItem> items = students.stream()
                .map(s -> new GetAllStudentsResponse.StudentItem(
                        s.getId(), s.getFirstName(), s.getLastName(), s.getGradeLevel()))
                .toList();

        return new GetAllStudentsResponse(items, items.size());
    }
}
