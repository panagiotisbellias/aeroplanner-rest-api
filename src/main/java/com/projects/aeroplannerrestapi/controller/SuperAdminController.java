package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.RegisterUserDto;
import com.projects.aeroplannerrestapi.dto.UserDto;
import com.projects.aeroplannerrestapi.service.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/super-admins")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    @PostMapping
    public ResponseEntity<UserDto> createAdministrator(@RequestBody RegisterUserDto registerUserDto) {
        return new ResponseEntity<>(superAdminService.createAdministrator(registerUserDto), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getAdministrator(@PathVariable Long id) {
        return ResponseEntity.ok(superAdminService.getAdministrator(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateAdministrator(@PathVariable Long id,
                                                       @RequestBody RegisterUserDto registerUserDto) {
        return ResponseEntity.ok(superAdminService.updateAdministrator(id, registerUserDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAdministrator(@PathVariable Long id) {
        superAdminService.deleteAdministrator(id);
        return ResponseEntity.noContent().build();
    }
}
