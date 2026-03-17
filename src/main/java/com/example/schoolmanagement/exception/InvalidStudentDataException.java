package com.example.schoolmanagement.exception;

public class InvalidStudentDataException extends RuntimeException {

    public InvalidStudentDataException() {
        super("Invalid student data");
    }

    public InvalidStudentDataException(String message) {
        super(message);
    }
}

