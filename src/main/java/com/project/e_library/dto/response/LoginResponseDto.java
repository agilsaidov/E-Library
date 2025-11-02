package com.project.e_library.dto.response;

import com.project.e_library.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private String token;
    private String userId;
    private String email;
    private String name;
    private String surname;
    private Date birthday;
    private Gender gender;
    private String avatarUrl;
}
