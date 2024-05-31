package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.request.FlightRequest;
import com.projects.aeroplannerrestapi.dto.response.FlightResponse;
import com.projects.aeroplannerrestapi.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.projects.aeroplannerrestapi.constants.OpenApiConstants.*;
import static com.projects.aeroplannerrestapi.constants.PathConstants.API_V1_FLIGHTS;
import static com.projects.aeroplannerrestapi.constants.PathConstants.ID;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.ADMIN_ROLE_AUTHORIZATION;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.USER_OR_ADMIN_ROLE_AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@PreAuthorize(ADMIN_ROLE_AUTHORIZATION)
@RequestMapping(API_V1_FLIGHTS)
public class FlightController {

    private static final Log LOG = LogFactory.getLog(FlightController.class);

    private final FlightService flightService;

    @PostMapping
    @Operation(summary = CREATE_FLIGHT)
    @ApiResponses(@ApiResponse(responseCode = CREATED, description = FLIGHT_CREATED))
    public ResponseEntity<FlightResponse> createFlight(@RequestBody @Valid FlightRequest flightRequest) {
        LOG.debug(String.format("== createFlight(%s)", flightRequest));
        return new ResponseEntity<>(flightService.createFlight(flightRequest), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize(USER_OR_ADMIN_ROLE_AUTHORIZATION)
    @Operation(summary = GET_ALL_FLIGHTS)
    @ApiResponses(@ApiResponse(responseCode = OK, description = FOUND_THE_FLIGHTS))
    public ResponseEntity<List<FlightResponse>> getAllFlights() {
        LOG.debug("== getAllFlights()");
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @GetMapping(ID)
    @PreAuthorize(USER_OR_ADMIN_ROLE_AUTHORIZATION)
    @Operation(summary = GET_FLIGHT)
    @ApiResponses(@ApiResponse(responseCode = OK, description = FOUND_THE_FLIGHT))
    public ResponseEntity<FlightResponse> getFlight(@PathVariable Long id) {
        LOG.debug(String.format("== getFlight(%d)", id));
        return ResponseEntity.ok(flightService.getFlight(id));
    }

    @PutMapping(ID)
    @Operation(summary = UPDATE_FLIGHT)
    @ApiResponses(@ApiResponse(responseCode = OK, description = FLIGHT_UPDATED))
    public ResponseEntity<FlightResponse> updateFlight(@PathVariable Long id, @RequestBody @Valid FlightRequest flightRequest) {
        LOG.debug(String.format("== updateFlight(%d, %s)", id, flightRequest));
        return ResponseEntity.ok(flightService.updateFlight(id, flightRequest));
    }

    @DeleteMapping(ID)
    @Operation(summary = DELETE_FLIGHT)
    @ApiResponses(@ApiResponse(responseCode = NO_CONTENT, description = FLIGHT_DELETED))
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        LOG.debug(String.format("== deleteFlight(%d)", id));
        flightService.deleteFlight(id);
        LOG.info(FLIGHT_DELETED);
        return ResponseEntity.noContent().build();
    }
}
