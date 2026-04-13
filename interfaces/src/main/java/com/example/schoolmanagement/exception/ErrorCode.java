package com.example.schoolmanagement.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND("NOT_FOUND"),
    STUDENT_NOT_FOUND("STUDENT_NOT_FOUND"),
    VALIDATION_ERROR("VALIDATION_ERROR"),
    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
    AUTHENTICATION_FAILED("AUTHENTICATION_FAILED"),
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS"),
    ACCESS_DENIED("ACCESS_DENIED");

    private final String code;
}

