package com.example.schoolmanagement.student.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStudentResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String gradeLevel;
}

