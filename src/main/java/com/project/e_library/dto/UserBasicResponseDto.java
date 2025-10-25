package com.project.e_library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserBasicResponseDto {
    private String userId;
    private String userName;
    private Date createdAt;
}
