package com.projects.aeroplannerrestapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

//    TODO Test that validation annotations work. Trying empty request returns 201 instead of 400
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$",
            message = "Password must contain at least one uppercase letter, " +
                    "one lowercase letter, one digit, one special character " +
                    "and be at least 8 characters long.")
    private String password;
    @NotBlank
    private String fullName;
}
