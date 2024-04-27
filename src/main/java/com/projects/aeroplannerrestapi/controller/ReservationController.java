package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.request.ReservationRequest;
import com.projects.aeroplannerrestapi.dto.response.ReservationResponse;
import com.projects.aeroplannerrestapi.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody @Valid ReservationRequest reservationDto) {
        return new ResponseEntity<>(reservationService.createReservation(reservationDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("{id}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservation(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<ReservationResponse> updateReservation(@PathVariable Long id,
                                                            @RequestBody @Valid ReservationRequest reservationRequest) {
        return ResponseEntity.ok(reservationService.updateReservation(id, reservationRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }
}
