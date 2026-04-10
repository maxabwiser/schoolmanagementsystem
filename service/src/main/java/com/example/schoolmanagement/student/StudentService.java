package com.example.schoolmanagement.student;

import com.example.schoolmanagement.exception.StudentNotFoundException;
import com.example.schoolmanagement.student.dto.request.CreateStudentRequest;
import com.example.schoolmanagement.student.dto.request.UpdateStudentRequest;
import com.example.schoolmanagement.student.dto.response.CreateStudentResponse;
import com.example.schoolmanagement.student.dto.response.GetAllStudentsResponse;
import com.example.schoolmanagement.student.dto.response.GetStudentResponse;
import com.example.schoolmanagement.student.dto.response.UpdateStudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public GetAllStudentsResponse findAll() {
        List<Student> students = studentRepository.findAll();

        List<GetAllStudentsResponse.StudentItem> items = students.stream()
                .map(s -> new GetAllStudentsResponse.StudentItem(
                        s.getId(), s.getFirstName(), s.getLastName(), s.getGradeLevel()))
                .toList();

        return new GetAllStudentsResponse(items, items.size());
    }

    public GetStudentResponse findById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        return new GetStudentResponse(
                student.getId(), student.getFirstName(), student.getLastName(), student.getGradeLevel());
    }

    public CreateStudentResponse create(CreateStudentRequest request) {
        Student student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setGradeLevel(request.getGradeLevel());

        Student saved = studentRepository.save(student);

        return new CreateStudentResponse(
                saved.getId(), saved.getFirstName(), saved.getLastName(), saved.getGradeLevel());
    }

    public UpdateStudentResponse update(Long id, UpdateStudentRequest request) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));

        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setGradeLevel(request.getGradeLevel());

        Student saved = studentRepository.save(existing);

        return new UpdateStudentResponse(
                saved.getId(), saved.getFirstName(), saved.getLastName(), saved.getGradeLevel());
    }

    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }
}

