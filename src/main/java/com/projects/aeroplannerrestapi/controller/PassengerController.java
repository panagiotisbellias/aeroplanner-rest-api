package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.response.PaginatedAndSortedPassengerResponse;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;
import com.projects.aeroplannerrestapi.service.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.projects.aeroplannerrestapi.constants.OpenApiConstants.*;
import static com.projects.aeroplannerrestapi.constants.PathConstants.API_V1_PASSENGERS;
import static com.projects.aeroplannerrestapi.constants.PathConstants.ID;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.ADMIN_ROLE_AUTHORIZATION;
import static com.projects.aeroplannerrestapi.constants.SortingAndPaginationConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_PASSENGERS)
@PreAuthorize(ADMIN_ROLE_AUTHORIZATION)
public class PassengerController {

    private final PassengerService adminService;

    @GetMapping
    @Operation(summary = GET_PASSENGERS)
    @ApiResponses(@ApiResponse(responseCode = OK, description = FOUND_THE_PASSENGERS))
    public ResponseEntity<PaginatedAndSortedPassengerResponse> getPassengers(
            @RequestParam(name = PAGE_NUM, defaultValue = DEFAULT_PAGE_NUM, required = false) int pageNumber,
            @RequestParam(name = PAGE_SIZE, defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = SORT_BY, defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = SORT_DIR, defaultValue = DEFAULT_SORT_DIR, required = false) String sortDir) {
        return ResponseEntity.ok(adminService.getPassengers(pageNumber, pageSize, sortBy, sortDir));
    }

    @GetMapping(ID)
    @Operation(summary = GET_PASSENGER)
    @ApiResponses(@ApiResponse(responseCode = OK, description = FOUND_THE_PASSENGER))
    public ResponseEntity<UserResponse> getPassenger(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getPassenger(id));
    }

    @DeleteMapping(ID)
    @Operation(summary = DELETE_PASSENGER)
    @ApiResponses(@ApiResponse(responseCode = NO_CONTENT, description = PASSENGER_DELETED))
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        adminService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }
}
