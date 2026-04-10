package com.example.schoolmanagement.config;

import com.example.schoolmanagement.student.Student;
import com.example.schoolmanagement.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final StudentRepository studentRepository;

    @Override
    public void run(String... args) {
        if (studentRepository.count() > 0) {
            log.info("Database already has students, skipping seed data");
            return;
        }

        List<Student> students = List.of(
                new Student(null, "Ana", "Silva", "Grade 10"),
                new Student(null, "James", "Smith", "Grade 11"),
                new Student(null, "Maria", "Garcia", "Grade 9"),
                new Student(null, "Luca", "Rossi", "Grade 12"),
                new Student(null, "Sophie", "Martin", "Grade 10")
        );

        studentRepository.saveAll(students);
        log.info("Loaded {} students into the database", students.size());
    }
}

