package com.project.e_library.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.e_library.model.Gender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@AllArgsConstructor
@Setter @Getter
public class UserUpdateRequestDto {

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only letters")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Surname is required")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Surname must contain only letters")
    @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters")
    private String surname;

    @NotNull(message = "Birthdate is required")
    @Past(message = "Birthdate must be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    @NotNull(message = "Gender is required")
    private Gender gender;
}
