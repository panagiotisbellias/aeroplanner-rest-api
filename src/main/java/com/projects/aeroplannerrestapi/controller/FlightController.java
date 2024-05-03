package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.request.FlightRequest;
import com.projects.aeroplannerrestapi.dto.response.FlightResponse;
import com.projects.aeroplannerrestapi.service.FlightService;
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
    public ResponseEntity<FlightResponse> createFlight(@RequestBody @Valid FlightRequest flightRequest) {
        return new ResponseEntity<>(flightService.createFlight(flightRequest), HttpStatus.CREATED);
    }

    @PreAuthorize(USER_OR_ADMIN_ROLE_AUTHORIZATION)
    @GetMapping
    public ResponseEntity<List<FlightResponse>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @PreAuthorize(USER_OR_ADMIN_ROLE_AUTHORIZATION)
    @GetMapping(ID)
    public ResponseEntity<FlightResponse> getFlight(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getFlight(id));
    }

    @PutMapping(ID)
    public ResponseEntity<FlightResponse> updateFlight(@PathVariable Long id, @RequestBody @Valid FlightRequest flightRequest) {
        return ResponseEntity.ok(flightService.updateFlight(id, flightRequest));
    }

    @DeleteMapping(ID)
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }
}
