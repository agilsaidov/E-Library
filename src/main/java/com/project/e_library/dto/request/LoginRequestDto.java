package com.project.e_library.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Setter @Getter
public class LoginRequestDto {
    private String email;
    private String password;
}
