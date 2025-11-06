package com.project.e_library.controller;

import com.project.e_library.dto.request.ChangePasswordDto;
import com.project.e_library.dto.request.UserUpdateRequestDto;
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

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateRequestDto dto,
                                        @RequestHeader("Authorization") String authHeader) {

            String userId = jwtService.getIdFromToken(authHeader.substring(7));

            userService.updateUser(userId, dto);
            return ResponseEntity.ok("Data Updated");
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto,
                                            @RequestHeader("Authorization") String authHeader) {

        String userId = jwtService.getIdFromToken(authHeader.substring(7));

        userService.changePassword(userId, changePasswordDto);
        return ResponseEntity.ok("Password changed");
    }

}
