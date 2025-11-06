package com.project.e_library.service;

import com.project.e_library.dto.request.ForgetPasswordInitiateRequest;
import com.project.e_library.dto.request.ForgetPasswordVerifyRequest;
import com.project.e_library.exception.InvalidOtpException;
import com.project.e_library.exception.PasswordResetNotFoundException;
import com.project.e_library.exception.InvalidPayloadException;
import com.project.e_library.exception.UserNotFoundException;
import com.project.e_library.model.LibUser;
import com.project.e_library.model.TempForgetPasswordData;
import com.project.e_library.repo.LibUserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ForgetPasswordService {

    private final LibUserRepo libUserRepo;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, Object> redisTemplate;
    private final MailSenderService mailSenderService;
    private final OtpService otpService;

    private static final String PASSWORD_RESET_PREFIX = "password_reset:";
    private static final int RESET_EXPIRATION = 5;

    public void initiate(ForgetPasswordInitiateRequest request) {
        if(request.getEmail() == null || request.getEmail().isEmpty()){
            throw new InvalidPayloadException("Email is required");
        }

        if(!libUserRepo.existsByEmail(request.getEmail())){
            throw new UserNotFoundException("Email not found in database");
        }

        String otp = otpService.generateOtp();

        TempForgetPasswordData tempData = new TempForgetPasswordData();
        tempData.setEmail(request.getEmail());
        tempData.setOtpCode(otp);

        redisTemplate.opsForValue().set(
                PASSWORD_RESET_PREFIX + tempData.getEmail(),
                tempData,
                RESET_EXPIRATION,
                TimeUnit.MINUTES
        );


        mailSenderService.sendMail(request.getEmail(),
                "Reset Password Request",
                "Your OTP code to reset password: " + otp
        );
    }


    @Transactional
    public void verify(ForgetPasswordVerifyRequest request) {
        if (request == null ||
                request.getEmail() == null ||
                request.getOtpCode() == null ||
                request.getNewPassword() == null) {
            System.out.println(request.getEmail());
            System.out.println(request.getOtpCode());
            System.out.println(request.getNewPassword());
            throw new InvalidPayloadException("Request payload is empty or incomplete");
        }

        if(!libUserRepo.existsByEmail(request.getEmail())){
            throw new UserNotFoundException("Email not found in database");
        }

        TempForgetPasswordData tempData = (TempForgetPasswordData) redisTemplate.opsForValue().get(PASSWORD_RESET_PREFIX+request.getEmail());

        if(tempData == null){
            throw new PasswordResetNotFoundException("Password reset session not found or expired. Please request a new reset code.");
        }

        if(request.getOtpCode().equals(tempData.getOtpCode())){
            LibUser user = libUserRepo.findByEmail(tempData.getEmail()).orElseThrow(()-> new InvalidOtpException("Invalid OTP code"));
            redisTemplate.delete(PASSWORD_RESET_PREFIX+request.getEmail());

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            libUserRepo.save(user);
        }
    }

}
