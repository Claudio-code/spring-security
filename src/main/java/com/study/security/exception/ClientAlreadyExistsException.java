package com.study.security.exception;

public class ClientAlreadyExistsException extends BusinessException {
    private static final String MESSAGE = "This client already exists.";

    public ClientAlreadyExistsException() {
        super(MESSAGE);
    }
}
