package com.project.e_library.controller;

import com.project.e_library.dto.UserRegistrationRequestDto;
import com.project.e_library.model.LibUser;
import com.project.e_library.service.LibUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RegistrationController {

    private final LibUserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationRequestDto userRegistrationRequestDto) {
        LibUser registrationUser = new LibUser();
        registrationUser.setEmail(userRegistrationRequestDto.getEmail());
        registrationUser.setPassword(userRegistrationRequestDto.getPassword());
        userService.registerUser(registrationUser);
        return ResponseEntity.ok("User registered successfully");
    }
}
