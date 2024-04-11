package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.RegisterUserDto;
import com.projects.aeroplannerrestapi.dto.UserDto;
import com.projects.aeroplannerrestapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins")
public class AdminController {

    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<UserDto> createAdministrator(@RequestBody RegisterUserDto registerUserDto) {
        return new ResponseEntity<>(userService.createAdministrator(registerUserDto), HttpStatus.CREATED);
    }
}
