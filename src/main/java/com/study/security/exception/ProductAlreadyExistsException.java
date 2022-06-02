package com.study.security.exception;

public class ProductAlreadyExistsException extends BusinessException {
    private static final String MESSAGE = "This product already exists.";

    public ProductAlreadyExistsException() {
        super(MESSAGE);
    }
}
