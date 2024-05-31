package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.request.RegisterRequest;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;
import com.projects.aeroplannerrestapi.service.SuperAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.projects.aeroplannerrestapi.constants.OpenApiConstants.*;
import static com.projects.aeroplannerrestapi.constants.PathConstants.API_V1_SUPER_ADMINS;
import static com.projects.aeroplannerrestapi.constants.PathConstants.ID;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.SUPER_ADMIN_ROLE_AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_SUPER_ADMINS)
@PreAuthorize(SUPER_ADMIN_ROLE_AUTHORIZATION)
public class SuperAdminController {

    private static final Log LOG = LogFactory.getLog(SuperAdminController.class);

    private final SuperAdminService superAdminService;

    @PostMapping
    @Operation(summary = CREATE_ADMINISTRATOR)
    @ApiResponses(@ApiResponse(responseCode = CREATED, description = ADMINISTRATOR_CREATED))
    public ResponseEntity<UserResponse> createAdministrator(@RequestBody RegisterRequest registerRequest) {
        LOG.debug(String.format("== createAdministrator(%s)", registerRequest.getEmail()));
        return new ResponseEntity<>(superAdminService.createAdministrator(registerRequest), HttpStatus.CREATED);
    }

    @GetMapping(ID)
    @Operation(summary = GET_ADMINISTRATOR)
    @ApiResponses(@ApiResponse(responseCode = OK, description = FOUND_THE_ADMINISTRATOR))
    public ResponseEntity<UserResponse> getAdministrator(@PathVariable Long id) {
        LOG.debug(String.format("== getAdministrator(%d)", id));
        return ResponseEntity.ok(superAdminService.getAdministrator(id));
    }

    @PutMapping(ID)
    @Operation(summary = UPDATE_ADMINISTRATOR)
    @ApiResponses(@ApiResponse(responseCode = OK, description = ADMINISTRATOR_UPDATED))
    public ResponseEntity<UserResponse> updateAdministrator(@PathVariable Long id,
                                                       @RequestBody RegisterRequest registerRequest) {
        LOG.debug(String.format("== updateAdministrator(%d, %s)", id, registerRequest.getEmail()));
        return ResponseEntity.ok(superAdminService.updateAdministrator(id, registerRequest));
    }

    @DeleteMapping(ID)
    @Operation(summary = DELETE_ADMINISTRATOR)
    @ApiResponses(@ApiResponse(responseCode = NO_CONTENT, description = ADMINISTRATOR_DELETED))
    public ResponseEntity<Void> deleteAdministrator(@PathVariable Long id) {
        LOG.debug(String.format("== deleteAdministrator(%d)", id));
        superAdminService.deleteAdministrator(id);
        LOG.info(ADMINISTRATOR_DELETED);
        return ResponseEntity.noContent().build();
    }
}
