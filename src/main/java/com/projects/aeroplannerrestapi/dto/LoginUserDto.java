package com.projects.aeroplannerrestapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;
}
