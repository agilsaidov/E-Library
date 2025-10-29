package com.project.e_library.controller;

import com.project.e_library.dto.LoginRequestDto;
import com.project.e_library.dto.LoginResponseDto;
import com.project.e_library.model.LibUser;
import com.project.e_library.security.JwtService;
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

    private final LibUserService userService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LibUser user = userService.loginUser(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        String token = jwtService.generateToken(user);
        return new ResponseEntity<>(
                new LoginResponseDto(token,user.getUserId(),
                        user.getEmail(),
                        user.getName(),
                        user.getSurname(),
                        user.getBirthday(),
                        user.getGender(),
                        user.getPictureUrl()),
                HttpStatus.OK);
    }

}
