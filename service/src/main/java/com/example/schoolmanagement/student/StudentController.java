package com.example.schoolmanagement.student;

import com.example.schoolmanagement.student.dto.request.CreateStudentRequest;
import com.example.schoolmanagement.student.dto.request.UpdateStudentRequest;
import com.example.schoolmanagement.student.dto.response.CreateStudentResponse;
import com.example.schoolmanagement.student.dto.response.GetAllStudentsResponse;
import com.example.schoolmanagement.student.dto.response.GetStudentResponse;
import com.example.schoolmanagement.student.dto.response.UpdateStudentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public GetAllStudentsResponse getAllStudents() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public GetStudentResponse getStudentById(@PathVariable Long id) {
        return studentService.findById(id);
    }

    @PostMapping
    public ResponseEntity<CreateStudentResponse> createStudent(@Valid @RequestBody CreateStudentRequest request) {
        CreateStudentResponse response = studentService.create(request);
        return ResponseEntity
                .created(URI.create("/v1/api/students/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{id}")
    public UpdateStudentResponse updateStudent(@PathVariable Long id, @Valid @RequestBody UpdateStudentRequest request) {
        return studentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
    }
}
