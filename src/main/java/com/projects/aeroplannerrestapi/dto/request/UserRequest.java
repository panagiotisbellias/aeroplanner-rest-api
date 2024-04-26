package com.projects.aeroplannerrestapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public class UserRequest {
    @NotNull
    private Long id;
    @NotBlank
    private String fullName;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$",
            message = """
                    Password must contain at least one uppercase letter, 
                    one lowercase letter, one digit, one special character 
                    and be at least 8 characters long.
                    """)
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
