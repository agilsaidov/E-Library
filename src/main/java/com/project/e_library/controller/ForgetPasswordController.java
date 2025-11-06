package com.project.e_library.controller;

import com.project.e_library.dto.request.ForgetPasswordInitiateRequest;
import com.project.e_library.dto.request.ForgetPasswordVerifyRequest;
import com.project.e_library.service.ForgetPasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/password/reset")
@RequiredArgsConstructor
public class ForgetPasswordController {

    private final ForgetPasswordService forgetPasswordService;

    @PostMapping("/initiate")
    public ResponseEntity<?> resetPasswordInitiate(@Valid @RequestBody ForgetPasswordInitiateRequest request) {
        forgetPasswordService.initiate(request);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/verify")
    public ResponseEntity<?> resetPasswordVerify(@Valid @RequestBody ForgetPasswordVerifyRequest request) {
        forgetPasswordService.verify(request);
        return ResponseEntity.ok().build();
    }

}
