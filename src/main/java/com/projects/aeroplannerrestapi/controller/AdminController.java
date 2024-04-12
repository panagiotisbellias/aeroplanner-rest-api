package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.RegisterUserDto;
import com.projects.aeroplannerrestapi.dto.UserDto;
import com.projects.aeroplannerrestapi.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<UserDto> createAdministrator(@RequestBody RegisterUserDto registerUserDto) {
        return new ResponseEntity<>(adminService.createAdministrator(registerUserDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllAdministrators() {
        return ResponseEntity.ok(adminService.getAllAdministrators());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getAdministrator(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getAdministrator(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateAdministrator(@PathVariable Long id, @RequestBody RegisterUserDto registerUserDto) {
        return ResponseEntity.ok(adminService.updateAdministrator(id, registerUserDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAdministrator(@PathVariable Long id) {
        adminService.deleteAdministrator(id);
        return ResponseEntity.noContent().build();
    }
}
