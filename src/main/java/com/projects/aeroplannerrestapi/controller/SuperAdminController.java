package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.request.RegisterRequest;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;
import com.projects.aeroplannerrestapi.service.SuperAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Create administrator")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "Administrator created"))
    public ResponseEntity<UserResponse> createAdministrator(@RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(superAdminService.createAdministrator(registerRequest), HttpStatus.CREATED);
    }

    @GetMapping(ID)
    @Operation(summary = "Get administrator")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Found the administrator"))
    public ResponseEntity<UserResponse> getAdministrator(@PathVariable Long id) {
        return ResponseEntity.ok(superAdminService.getAdministrator(id));
    }

    @PutMapping(ID)
    @Operation(summary = "Updated administrator")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Administrator updated"))
    public ResponseEntity<UserResponse> updateAdministrator(@PathVariable Long id,
                                                       @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(superAdminService.updateAdministrator(id, registerRequest));
    }

    @DeleteMapping(ID)
    @Operation(summary = "Delete administrator")
    @ApiResponses(@ApiResponse(responseCode = "204", description = "Administrator deleted"))
    public ResponseEntity<Void> deleteAdministrator(@PathVariable Long id) {
        superAdminService.deleteAdministrator(id);
        return ResponseEntity.noContent().build();
    }
}
