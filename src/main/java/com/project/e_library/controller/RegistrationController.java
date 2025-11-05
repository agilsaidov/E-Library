package com.project.e_library.controller;

import com.project.e_library.dto.request.RegistrationInitiateRequest;
import com.project.e_library.dto.request.RegistrationVerifyRequest;
import com.project.e_library.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/initiate")
    public ResponseEntity<?> initiateRegistration(@Valid @RequestBody RegistrationInitiateRequest dto) {
        System.out.println(dto);
        registrationService.initiateRegistration(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<String> validateRegistration(@Valid @RequestBody RegistrationVerifyRequest dto){
        registrationService.registerUser(dto);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

}
