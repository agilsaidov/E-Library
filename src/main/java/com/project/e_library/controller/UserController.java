package com.project.e_library.controller;

import com.project.e_library.model.User;
import com.project.e_library.service.UserService;
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
    public User getCurrentUser(Authentication authentication) {
        String authId = authentication.getName();
        return userService.getUserByAuthId(authId);
    }
}
