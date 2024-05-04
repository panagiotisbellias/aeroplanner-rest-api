package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.request.FlightRequest;
import com.projects.aeroplannerrestapi.dto.response.FlightResponse;
import com.projects.aeroplannerrestapi.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.projects.aeroplannerrestapi.constants.PathConstants.API_V1_FLIGHTS;
import static com.projects.aeroplannerrestapi.constants.PathConstants.ID;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.ADMIN_ROLE_AUTHORIZATION;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.USER_OR_ADMIN_ROLE_AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@PreAuthorize(ADMIN_ROLE_AUTHORIZATION)
@RequestMapping(API_V1_FLIGHTS)
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    @Operation(summary = "Create flight")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "Flight Created"))
    public ResponseEntity<FlightResponse> createFlight(@RequestBody @Valid FlightRequest flightRequest) {
        return new ResponseEntity<>(flightService.createFlight(flightRequest), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize(USER_OR_ADMIN_ROLE_AUTHORIZATION)
    @Operation(summary = "Get all flights")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Found the flights"))
    public ResponseEntity<List<FlightResponse>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @GetMapping(ID)
    @PreAuthorize(USER_OR_ADMIN_ROLE_AUTHORIZATION)
    @Operation(summary = "Get flight")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Found the flight"))
    public ResponseEntity<FlightResponse> getFlight(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getFlight(id));
    }

    @PutMapping(ID)
    @Operation(summary = "Update flight")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Flight updated"))
    public ResponseEntity<FlightResponse> updateFlight(@PathVariable Long id, @RequestBody @Valid FlightRequest flightRequest) {
        return ResponseEntity.ok(flightService.updateFlight(id, flightRequest));
    }

    @DeleteMapping(ID)
    @Operation(summary = "Delete flight")
    @ApiResponses(@ApiResponse(responseCode = "204", description = "Flight deleted"))
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }
}
