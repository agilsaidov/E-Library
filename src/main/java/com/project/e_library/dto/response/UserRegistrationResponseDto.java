package com.project.e_library.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserRegistrationResponseDto {
    private String userId;
    private String userName;
    private Date createdAt;
}
