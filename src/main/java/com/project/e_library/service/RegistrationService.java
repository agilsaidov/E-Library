package com.project.e_library.service;

import com.project.e_library.Id.IdGenerator;
import com.project.e_library.dto.request.RegistrationInitiateRequest;
import com.project.e_library.dto.request.RegistrationVerifyRequest;
import com.project.e_library.exception.DuplicateResourceException;
import com.project.e_library.exception.InvalidOtpException;
import com.project.e_library.exception.InvalidPayloadException;
import com.project.e_library.exception.RegistrationNotFoundException;
import com.project.e_library.model.LibUser;
import com.project.e_library.model.TempRegistrationData;
import com.project.e_library.repo.LibUserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final LibUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final MailSenderService mailSenderService;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String REGISTRATION_PREFIX = "registration:";
    private static final int REGISTRATION_EXPIRATION = 5;

    public void initiateRegistration(RegistrationInitiateRequest riRequest) {
        if(riRequest == null || riRequest.getEmail() == null || riRequest.getPassword() == null) {
            throw new InvalidPayloadException("Email and password are required");
        }

        if(userRepo.existsByEmail(riRequest.getEmail())) {
            throw new DuplicateResourceException("The given email is already in use");
        }

        String otp = otpService.generateOtp();
        TempRegistrationData tempRegistrationData = new TempRegistrationData();

        tempRegistrationData.setEmail(riRequest.getEmail());
        tempRegistrationData.setPassword(passwordEncoder.encode(riRequest.getPassword()));
        tempRegistrationData.setOtpCode(otp);

        redisTemplate.opsForValue().set(REGISTRATION_PREFIX + riRequest.getEmail(),
                tempRegistrationData,
                REGISTRATION_EXPIRATION,
                TimeUnit.MINUTES
        );

        mailSenderService.sendMail(riRequest.getEmail(),
                "Account Verification",
                   "Your OTP is: " + otp
        );
    }



    @Transactional
    public void registerUser(RegistrationVerifyRequest rvRequest) {
        if(rvRequest == null || rvRequest.getEmail() == null || rvRequest.getOtp() == null) {
            throw new InvalidPayloadException("Request payload is empty or not complete");
        }
        if(userRepo.existsByEmail(rvRequest.getEmail())) {
            throw new DuplicateResourceException("The given email is already in database");
        }


        TempRegistrationData tempRegistrationData = (TempRegistrationData) redisTemplate.opsForValue().get(REGISTRATION_PREFIX + rvRequest.getEmail());

        if(tempRegistrationData == null) {
            throw new RegistrationNotFoundException("Registration session not found or expired. Please restart the registration process.");
        }

        if(tempRegistrationData.getOtpCode() == null || tempRegistrationData.getEmail() == null || tempRegistrationData.getPassword() == null) {
            throw new InvalidPayloadException("User registration payload is empty, Please restart the registration process.");
        }

        if(rvRequest.getOtp().equals(tempRegistrationData.getOtpCode())) {
            LibUser user = new LibUser();
            user.setUserId(IdGenerator.generateUserId());
            user.setEmail(tempRegistrationData.getEmail());
            user.setPassword(tempRegistrationData.getPassword());
            userRepo.save(user);

            redisTemplate.delete(REGISTRATION_PREFIX + rvRequest.getEmail());

        }else {
            throw new InvalidOtpException("Invalid OTP code");
        }

    }
}
