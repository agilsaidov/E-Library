package com.project.e_library.exception;

import com.project.e_library.dto.ErrorResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String message = String.format("Invalid value for parameter '%s'", e.getName());

        ErrorResponse error = new ErrorResponse(
                "INVALID_PARAMETER_TYPE",
                message,
                LocalDateTime.now()
                );
       return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException e) {
        String message = "An error occurred while trying to access the database";
        ErrorResponse error = new ErrorResponse(
                "DATABASE_ERROR",
                message,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(InvalidSearchParameterException.class)
    public ResponseEntity<ErrorResponse> handleInvalidSearchParameterException(InvalidSearchParameterException e) {
        ErrorResponse error = new ErrorResponse(
                "INVALID_SEARCH_EXCEPTION",
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParameter(MissingServletRequestParameterException ex) {
        ErrorResponse error = new ErrorResponse(
                "MISSING_PARAMETER",
                "Required parameter '" + ex.getParameterName() + "' is missing",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                "USER_NOT_FOUND",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
