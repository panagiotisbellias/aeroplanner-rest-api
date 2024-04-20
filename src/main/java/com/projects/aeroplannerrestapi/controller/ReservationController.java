package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.ReservationDto;
import com.projects.aeroplannerrestapi.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@RequestBody @Valid ReservationDto reservationDto) {
        return new ResponseEntity<>(reservationService.createReservation(reservationDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("{id}")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservation(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<ReservationDto> updateReservation(@PathVariable Long id,
                                                            @RequestBody @Valid ReservationDto reservationDto) {
        return ResponseEntity.ok(reservationService.updateReservation(id, reservationDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
