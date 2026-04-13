package com.example.schoolmanagement.exception;

import com.example.schoolmanagement.exception.dto.ErrorItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorItem handleNotFoundException(StudentNotFoundException ex) {
        log.info("Resource not found: {}", ex.getMessage(), ex);
        return ErrorBuilder.build(ex);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ErrorItem handleAuthenticationFailed(AuthenticationFailedException ex) {
        log.info("Authentication failed: {}", ex.getMessage(), ex);
        return ErrorBuilder.build(ex);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ErrorItem handleUserAlreadyExists(UserAlreadyExistsException ex) {
        log.info("User already exists: {}", ex.getMessage(), ex);
        return ErrorBuilder.build(ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorItem handleValidationException(MethodArgumentNotValidException ex) {
        log.info("Validation error: {}", ex.getMessage(), ex);
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fe -> fe.getField(),
                        fe -> fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "invalid",
                        (a, b) -> a
                ));
        return new ErrorItem(ErrorCode.VALIDATION_ERROR.getCode(), "Validation failed", fieldErrors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorItem handleIllegalArgumentException(IllegalArgumentException ex) {
        log.info("Illegal argument: {}", ex.getMessage(), ex);
        return ErrorBuilder.build(ex);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorItem handleInternalServerError(Exception ex) {
        log.error("Internal server error: {}", ex.getMessage(), ex);
        return ErrorBuilder.build(ex);
    }
}
