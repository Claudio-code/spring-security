package com.study.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorsFormatter handleValidationExceptions(MethodArgumentNotValidException validException) {
        return new ApiErrorsFormatter(validException.getBindingResult());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorsFormatter handleIsbnAlreadyUsed(BusinessException businessException) {
        return new ApiErrorsFormatter(businessException);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorsFormatter> handleResponseStatusException(ResponseStatusException statusException) {
        return new ResponseEntity<>(new ApiErrorsFormatter(statusException), statusException.getStatus());
    }
}
