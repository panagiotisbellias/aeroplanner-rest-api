package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.request.ReservationRequest;
import com.projects.aeroplannerrestapi.dto.response.PaginatedAndSortedReservationResponse;
import com.projects.aeroplannerrestapi.dto.response.ReservationResponse;
import com.projects.aeroplannerrestapi.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.projects.aeroplannerrestapi.constants.OpenApiConstants.*;
import static com.projects.aeroplannerrestapi.constants.PathConstants.API_V1_RESERVATIONS;
import static com.projects.aeroplannerrestapi.constants.PathConstants.ID;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.USER_ROLE_AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@PreAuthorize(USER_ROLE_AUTHORIZATION)
@RequestMapping(API_V1_RESERVATIONS)
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @Operation(summary = CREATE_RESERVATION)
    @ApiResponses(@ApiResponse(responseCode = CREATED, description = RESERVATION_CREATED))
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody @Valid ReservationRequest reservationDto) {
        return new ResponseEntity<>(reservationService.createReservation(reservationDto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = GET_ALL_RESERVATIONS)
    @ApiResponses(@ApiResponse(responseCode = OK, description = FOUND_ALL_THE_RESERVATIONS))
    public ResponseEntity<PaginatedAndSortedReservationResponse> getAllReservations(
            @RequestParam(name = "pageNum", defaultValue = "1", required = false) int pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return ResponseEntity.ok(reservationService.getAllReservations(pageNum, pageSize, sortBy, sortDir));
    }

    @GetMapping(ID)
    @Operation(summary = GET_RESERVATION)
    @ApiResponses(@ApiResponse(responseCode = OK, description = FOUND_THE_RESERVATION))
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservation(id));
    }

    @PutMapping(ID)
    @Operation(summary = UPDATE_RESERVATION)
    @ApiResponses(@ApiResponse(responseCode = OK, description = RESERVATION_UPDATED))
    public ResponseEntity<ReservationResponse> updateReservation(@PathVariable Long id,
                                                            @RequestBody @Valid ReservationRequest reservationRequest) {
        return ResponseEntity.ok(reservationService.updateReservation(id, reservationRequest));
    }

    @DeleteMapping(ID)
    @Operation(summary = CANCEL_RESERVATION)
    @ApiResponses(@ApiResponse(responseCode = NO_CONTENT, description = RESERVATION_CANCELLED))
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }
}
