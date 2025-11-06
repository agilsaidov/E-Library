package com.project.e_library.exception;

public class PasswordResetNotFoundException extends RuntimeException {
    public PasswordResetNotFoundException(String message) {
        super(message);
    }
}
