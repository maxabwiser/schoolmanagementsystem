package com.example.schoolmanagement.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler({
            IllegalArgumentException.class,
            InvalidStudentDataException.class,
            StudentAlreadyExistsException.class
    })
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public <E extends RuntimeException> Map<String, String> handleBadRequestException(E ex) {
        log.info("Bad request: {}", ex.getMessage(), ex);
        return Map.of("error", "badRequest", "message", ex.getMessage());
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public <E extends RuntimeException> Map<String, String> handleNotFoundException(E ex) {
        log.info("Resource not found: {}", ex.getMessage(), ex);
        return Map.of("error", "notFound", "message", ex.getMessage());
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public <E extends Exception> Map<String, String> handleInternalServerError(E ex) {
        log.error("Internal server error: {}", ex.getMessage(), ex);
        return Map.of("error", "internalError", "message", "An unexpected error occurred");
    }
}
