package com.projects.aeroplannerrestapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    private Long id;

    @NotNull
    private String fullName;

    @Email
    @NotNull
    private String email;

    @NotNull
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
