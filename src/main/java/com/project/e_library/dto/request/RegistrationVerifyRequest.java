package com.project.e_library.dto.request;

import lombok.Data;

@Data
public class RegistrationVerifyRequest {
    private String email;
    private String otp;
}
