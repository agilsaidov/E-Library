package com.project.e_library.controller;

import com.project.e_library.model.User;
import com.project.e_library.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public User getUser(@RequestParam("id") long user_id) {
        return userService.getUserById(user_id);
    }
}
