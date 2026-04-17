package com.example.schoolmanagement.config;

import com.example.schoolmanagement.auth.domain.model.Role;
import com.example.schoolmanagement.auth.domain.model.entity.User;
import com.example.schoolmanagement.auth.domain.repository.UserRepository;
import com.example.schoolmanagement.student.domain.model.entity.Student;
import com.example.schoolmanagement.student.domain.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedUsers();
        seedStudents();
    }

    private void seedUsers() {
        if (userRepository.count() > 0) {
            log.info("Database already has users, skipping seed user");
            return;
        }

        User admin = User.builder()
                .firstName("Admin")
                .lastName("User")
                .email("admin@school.com")
                .password(passwordEncoder.encode("password123"))
                .role(Role.ADMIN)
                .build();

        userRepository.save(admin);
        log.info("Seeded admin user: admin@school.com / password123");
    }

    private void seedStudents() {
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
