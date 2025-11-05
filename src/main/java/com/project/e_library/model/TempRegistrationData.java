package com.project.e_library.model;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class TempRegistrationData {
    private String email;
    private String password;
    private String otpCode;
}
