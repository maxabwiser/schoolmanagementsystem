package com.example.schoolmanagement.student.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllStudentsResponse {
    private List<StudentItem> students;
    private int count;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentItem {
        private Long id;
        private String firstName;
        private String lastName;
        private String gradeLevel;
    }
}
