package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.UserDto;
import com.projects.aeroplannerrestapi.dto.UserResponse;
import com.projects.aeroplannerrestapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<UserResponse> getAllAdministrators(
            @RequestParam(name = "pageNum", defaultValue = "1", required = false) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return ResponseEntity.ok(userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> getAuthenticatedUser() {
        return ResponseEntity.ok(userService.getAuthenticatedUser());
    }
}
