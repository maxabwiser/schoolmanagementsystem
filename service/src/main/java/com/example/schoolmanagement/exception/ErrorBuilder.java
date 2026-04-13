package com.example.schoolmanagement.exception;
import com.example.schoolmanagement.exception.dto.ErrorItem;
import lombok.experimental.UtilityClass;
import java.util.HashMap;
import java.util.Map;
@UtilityClass
public class ErrorBuilder {
    private static final HashMap<Class<? extends Exception>, String> exceptionToErrorMap = new HashMap<>();
    static {
        exceptionToErrorMap.put(StudentNotFoundException.class, ErrorCode.STUDENT_NOT_FOUND.getCode());
        exceptionToErrorMap.put(IllegalArgumentException.class, ErrorCode.ILLEGAL_ARGUMENT.getCode());
        exceptionToErrorMap.put(AuthenticationFailedException.class, ErrorCode.AUTHENTICATION_FAILED.getCode());
        exceptionToErrorMap.put(UserAlreadyExistsException.class, ErrorCode.USER_ALREADY_EXISTS.getCode());
    }
    public static <E extends Exception> ErrorItem build(E ex) {
        return build(ex, Map.of());
    }
    public static <E extends Exception> ErrorItem build(E ex, Map<String, String> metadata) {
        String errorCode = exceptionToErrorMap.getOrDefault(ex.getClass(), ErrorCode.INTERNAL_SERVER_ERROR.getCode());
        String errorMessage = ex.getMessage();
        return new ErrorItem(errorCode, errorMessage, metadata);
    }
}
