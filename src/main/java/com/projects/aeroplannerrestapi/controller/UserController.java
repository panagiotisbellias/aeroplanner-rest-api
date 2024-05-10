package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.response.PaginatedAndSortedUserResponse;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;
import com.projects.aeroplannerrestapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import static com.projects.aeroplannerrestapi.constants.SortingAndPaginationConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_USERS)
public class UserController {

    private static final Log LOG = LogFactory.getLog(UserController.class);

    private final UserService userService;

    @GetMapping
    @PreAuthorize(SUPER_ADMIN_OR_ADMIN_ROLE_AUTHORIZATION)
    @Operation(summary = GET_ALL_ADMINISTRATORS)
    @ApiResponses(@ApiResponse(responseCode = OK, description = FOUND_ALL_THE_ADMINISTRATORS))
    public ResponseEntity<PaginatedAndSortedUserResponse> getAllAdministrators(
            @RequestParam(name = PAGE_NUM, defaultValue = DEFAULT_PAGE_NUM, required = false) int pageNumber,
            @RequestParam(name = PAGE_SIZE, defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = SORT_BY, defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = SORT_DIR, defaultValue = DEFAULT_SORT_DIR, required = false) String sortDir) {
        LOG.debug(String.format("getAllAdministrators(%d, %d, %s, %s)", pageNumber, pageSize, sortBy, sortDir));
        return ResponseEntity.ok(userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir));
    }

    @GetMapping(ME)
    @PreAuthorize(IS_AUTHENTICATED)
    @Operation(summary = GET_AUTHENTICATED_USER)
    @ApiResponses(@ApiResponse(responseCode = OK, description = FOUND_THE_AUTHENTICATED_USER))
    public ResponseEntity<UserResponse> getAuthenticatedUser() {
        LOG.debug("getAuthenticatedUser()");
        return ResponseEntity.ok(userService.getAuthenticatedUser());
    }
}
