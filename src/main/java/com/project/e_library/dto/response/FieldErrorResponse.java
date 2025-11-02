package com.project.e_library.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class FieldErrorResponse {
    private int status;
    private String error;
    private Map<String, String> fieldErrors;
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public FieldErrorResponse(HttpStatus status, Map<String, String> fieldErrors) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.fieldErrors = fieldErrors;
        this.message = "Validation failed";
        this.timestamp = LocalDateTime.now();
    }
}
