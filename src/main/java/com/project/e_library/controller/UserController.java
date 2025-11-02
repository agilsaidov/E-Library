package com.project.e_library.controller;

import com.project.e_library.dto.request.UserUpdateRequestDto;
import com.project.e_library.model.LibUser;
import com.project.e_library.security.JwtService;
import com.project.e_library.service.LibUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final LibUserService userService;
    private final JwtService jwtService;

    @PutMapping("/update{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateRequestDto dto,
                                        @RequestHeader("Authorization") String authHeader,
                                        /* Method is using ID in Token.This Path Variable is just for clearance*/
                                        @PathVariable String id) {

            String userId = jwtService.getIdFromToken(authHeader.substring(7));

            userService.updateUser(userId, dto);
            return ResponseEntity.ok("Data Updated");
    }

}
