package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.request.RegisterRequest;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;
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
    public ResponseEntity<UserResponse> createAdministrator(@RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(superAdminService.createAdministrator(registerRequest), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getAdministrator(@PathVariable Long id) {
        return ResponseEntity.ok(superAdminService.getAdministrator(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<UserResponse> updateAdministrator(@PathVariable Long id,
                                                       @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(superAdminService.updateAdministrator(id, registerRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAdministrator(@PathVariable Long id) {
        superAdminService.deleteAdministrator(id);
        return ResponseEntity.noContent().build();
    }
}
