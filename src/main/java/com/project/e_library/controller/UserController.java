package com.project.e_library.controller;

import com.project.e_library.exception.UserNotFoundException;
import com.project.e_library.model.LibUser;
import com.project.e_library.service.LibUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private LibUserService userService;

    @GetMapping("/user")
    public LibUser getUserDetailsAfterLogin(Authentication authentication) {
        Optional<LibUser> user = userService.findUserByEmail(authentication.getName());
        return user.orElseThrow(() -> new UserNotFoundException(authentication.getName()));
    }
}
