package com.project.e_library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final SecureRandom random = new SecureRandom();
    private final PasswordEncoder encoder;

    public String generateOtp() {
        int otp = random.nextInt(900000) + 100000;
        return String.valueOf(otp);
    }

}
