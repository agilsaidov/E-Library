package com.project.e_library.controller;

import com.project.e_library.exception.UserNotFoundException;
import com.project.e_library.model.User;
import com.project.e_library.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/profile")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        String authId = authentication.getName();
        User user = userService.getUserByAuthId(authId);

        if (user == null) {
            throw new UserNotFoundException("User not found with Auth ID: " + authId);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
