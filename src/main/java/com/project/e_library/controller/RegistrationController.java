package com.project.e_library.controller;

import com.project.e_library.dto.UserRegistrationRequestDto;
import com.project.e_library.model.User;
import com.project.e_library.service.UserService;
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

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationRequestDto userRegistrationRequestDto) {
        User registrationUser = new User();
        registrationUser.setEmail(userRegistrationRequestDto.getEmail());
        registrationUser.setPassword(userRegistrationRequestDto.getPassword());
        userService.registerUser(registrationUser);
        return ResponseEntity.ok("User registered successfully");
    }
}
