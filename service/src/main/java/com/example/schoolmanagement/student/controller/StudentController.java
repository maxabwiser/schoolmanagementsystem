package com.example.schoolmanagement.student.controller;

import com.example.schoolmanagement.student.dto.request.CreateStudentRequest;
import com.example.schoolmanagement.student.dto.request.UpdateStudentRequest;
import com.example.schoolmanagement.student.dto.response.CreateStudentResponse;
import com.example.schoolmanagement.student.dto.response.GetAllStudentsResponse;
import com.example.schoolmanagement.student.dto.response.GetStudentResponse;
import com.example.schoolmanagement.student.dto.response.UpdateStudentResponse;
import com.example.schoolmanagement.student.service.CreateStudent;
import com.example.schoolmanagement.student.service.DeleteStudent;
import com.example.schoolmanagement.student.service.GetAllStudents;
import com.example.schoolmanagement.student.service.GetStudent;
import com.example.schoolmanagement.student.service.UpdateStudent;
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

    private final CreateStudent createStudent;
    private final GetStudent getStudent;
    private final GetAllStudents getAllStudents;
    private final UpdateStudent updateStudent;
    private final DeleteStudent deleteStudent;

    @GetMapping
    public GetAllStudentsResponse getAllStudents() {
        return getAllStudents.execute();
    }

    @GetMapping("/{id}")
    public GetStudentResponse getStudentById(@PathVariable Long id) {
        return getStudent.execute(id);
    }

    @PostMapping
    public ResponseEntity<CreateStudentResponse> createStudent(@Valid @RequestBody CreateStudentRequest request) {
        CreateStudentResponse response = createStudent.execute(request);
        return ResponseEntity
                .created(URI.create("/v1/api/students/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{id}")
    public UpdateStudentResponse updateStudent(@PathVariable Long id, @Valid @RequestBody UpdateStudentRequest request) {
        return updateStudent.execute(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        deleteStudent.execute(id);
    }
}
