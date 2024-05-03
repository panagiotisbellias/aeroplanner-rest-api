package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.request.RegisterRequest;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;
import com.projects.aeroplannerrestapi.service.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.projects.aeroplannerrestapi.constants.PathConstants.API_V1_SUPER_ADMINS;
import static com.projects.aeroplannerrestapi.constants.PathConstants.ID;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.SUPER_ADMIN_ROLE_AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_SUPER_ADMINS)
@PreAuthorize(SUPER_ADMIN_ROLE_AUTHORIZATION)
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    @PostMapping
    public ResponseEntity<UserResponse> createAdministrator(@RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(superAdminService.createAdministrator(registerRequest), HttpStatus.CREATED);
    }

    @GetMapping(ID)
    public ResponseEntity<UserResponse> getAdministrator(@PathVariable Long id) {
        return ResponseEntity.ok(superAdminService.getAdministrator(id));
    }

    @PutMapping(ID)
    public ResponseEntity<UserResponse> updateAdministrator(@PathVariable Long id,
                                                       @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(superAdminService.updateAdministrator(id, registerRequest));
    }

    @DeleteMapping(ID)
    public ResponseEntity<Void> deleteAdministrator(@PathVariable Long id) {
        superAdminService.deleteAdministrator(id);
        return ResponseEntity.noContent().build();
    }
}
