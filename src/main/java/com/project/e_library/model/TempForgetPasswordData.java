package com.project.e_library.model;

import lombok.Data;

@Data
public class TempForgetPasswordData {
    private String email;
    private String otpCode;
}
