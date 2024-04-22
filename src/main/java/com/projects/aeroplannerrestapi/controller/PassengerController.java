package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.UserDto;
import com.projects.aeroplannerrestapi.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/passengers")
// TODO: Put the @PreAuthorize("hasRole('ADMIN')")
public class PassengerController {

    private final PassengerService adminService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getPassengers() {
        return ResponseEntity.ok(adminService.getPassengers());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getPassenger(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getPassenger(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        adminService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }
}
