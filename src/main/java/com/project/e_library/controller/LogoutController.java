package com.project.e_library.controller;

import com.project.e_library.service.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LogoutController {

    private final LogoutService logoutService;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        logoutService.logout(token);
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }
}
