package com.study.security.exception;

import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApiErrorsFormatter {
    private final List<String> errors;

    public ApiErrorsFormatter() {
        this.errors = new ArrayList<>();
    }

    public ApiErrorsFormatter(BusinessException exception) {
        this.errors = List.of(exception.getMessage());
    }

    public ApiErrorsFormatter(ResponseStatusException statusException) {
        this.errors = List.of(Objects.requireNonNull(statusException.getMessage()));
    }

    public ApiErrorsFormatter(BindingResult bindingResult) {
        this.errors = new ArrayList<>();
        bindingResult.getAllErrors()
                .forEach(error -> this.errors.add(error.getDefaultMessage()));
    }

    public List<String> getErrors() {
        return errors;
    }
}
