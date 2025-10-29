package com.project.e_library.controller;

import com.project.e_library.dto.LoginRequestDto;
import com.project.e_library.model.LibUser;
import com.project.e_library.service.LibUserDetailsService;
import com.project.e_library.service.LibUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final LibUserDetailsService userDetailsService;
    private final LibUserService userService;

    @PostMapping("/login")
    public ResponseEntity<LibUser> login(@RequestBody LoginRequestDto loginRequestDto) {
        return new ResponseEntity<>(userService.loginUser(loginRequestDto.getEmail(), loginRequestDto.getPassword()),HttpStatus.OK);
    }


}
