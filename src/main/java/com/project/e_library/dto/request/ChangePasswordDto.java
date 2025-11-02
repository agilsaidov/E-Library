package com.project.e_library.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter @Setter
public class ChangePasswordDto {

    @NotBlank(message = "Old password can't be empty")
    private String oldPassword;

    @NotBlank(message = "New password can't be empty")
    @Size(min = 8,max = 50, message = "Password must be between 8 and 50 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, and one digit"
    )
    private String newPassword;

    @NotBlank(message = "Confirmation password can't be empty")
    private String confirmPassword;
}
