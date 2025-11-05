package com.project.e_library.dto.request;

import lombok.Data;

@Data
public class RegistrationInitiateRequest {
    private String email;
    private String password;
}
