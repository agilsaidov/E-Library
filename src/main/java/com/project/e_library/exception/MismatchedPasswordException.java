package com.project.e_library.exception;

public class MismatchedPasswordException extends RuntimeException {
    public MismatchedPasswordException(String message) {
        super(message);
    }
}
