package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.response.PaginatedAndSortedPassengerResponse;
import com.projects.aeroplannerrestapi.dto.response.UserResponse;
import com.projects.aeroplannerrestapi.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.projects.aeroplannerrestapi.constants.PathConstants.API_V1_PASSENGERS;
import static com.projects.aeroplannerrestapi.constants.PathConstants.ID;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.ADMIN_ROLE_AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_PASSENGERS)
@PreAuthorize(ADMIN_ROLE_AUTHORIZATION)
public class PassengerController {

    private final PassengerService adminService;

    @GetMapping
    public ResponseEntity<PaginatedAndSortedPassengerResponse> getPassengers(
            @RequestParam(name = "pageNum", defaultValue = "1", required = false) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return ResponseEntity.ok(adminService.getPassengers(pageNumber, pageSize, sortBy, sortDir));
    }

    @GetMapping(ID)
    public ResponseEntity<UserResponse> getPassenger(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getPassenger(id));
    }

    @DeleteMapping(ID)
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        adminService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }
}
