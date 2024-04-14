package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.LoginResponse;
import com.projects.aeroplannerrestapi.dto.LoginUserDto;
import com.projects.aeroplannerrestapi.dto.RegisterUserDto;
import com.projects.aeroplannerrestapi.dto.UserDto;
import com.projects.aeroplannerrestapi.service.AuthenticationService;
import com.projects.aeroplannerrestapi.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        return new ResponseEntity<>(authenticationService.register(registerUserDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginUserDto loginUserDto) {
        return ResponseEntity.ok(authenticationService.authenticate(loginUserDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout (HttpServletRequest request) {
        authenticationService.logout(request);
        return ResponseEntity.ok("Logged out successfully");
    }
}
