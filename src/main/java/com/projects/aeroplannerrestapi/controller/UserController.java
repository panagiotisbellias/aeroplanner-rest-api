package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.response.PaginatedAndSortedUserResponse;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;
import com.projects.aeroplannerrestapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.projects.aeroplannerrestapi.constants.OpenApiConstants.*;
import static com.projects.aeroplannerrestapi.constants.PathConstants.API_V1_USERS;
import static com.projects.aeroplannerrestapi.constants.PathConstants.ME;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.IS_AUTHENTICATED;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.SUPER_ADMIN_OR_ADMIN_ROLE_AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_USERS)
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize(SUPER_ADMIN_OR_ADMIN_ROLE_AUTHORIZATION)
    @Operation(summary = GET_ALL_ADMINISTRATORS)
    @ApiResponses(@ApiResponse(responseCode = OK, description = FOUND_ALL_THE_ADMINISTRATORS))
    public ResponseEntity<PaginatedAndSortedUserResponse> getAllAdministrators(
            @RequestParam(name = "pageNum", defaultValue = "1", required = false) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return ResponseEntity.ok(userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir));
    }

    @GetMapping(ME)
    @PreAuthorize(IS_AUTHENTICATED)
    @Operation(summary = GET_AUTHENTICATED_USER)
    @ApiResponses(@ApiResponse(responseCode = OK, description = FOUND_THE_AUTHENTICATED_USER))
    public ResponseEntity<UserResponse> getAuthenticatedUser() {
        return ResponseEntity.ok(userService.getAuthenticatedUser());
    }
}
