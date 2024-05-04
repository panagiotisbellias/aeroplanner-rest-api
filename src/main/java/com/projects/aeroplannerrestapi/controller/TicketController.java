package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.request.TicketRequest;
import com.projects.aeroplannerrestapi.dto.response.TicketResponse;
import com.projects.aeroplannerrestapi.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.projects.aeroplannerrestapi.constants.PathConstants.API_V1_TICKETS;
import static com.projects.aeroplannerrestapi.constants.PathConstants.ID;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.USER_OR_ADMIN_ROLE_AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_TICKETS)
@PreAuthorize(USER_OR_ADMIN_ROLE_AUTHORIZATION)
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    @Operation(summary = "Get all tickets")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Found all the tickets"))
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping(ID)
    @Operation(summary = "Get ticket")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Found the ticket"))
    public ResponseEntity<TicketResponse> getTicket(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicket(id));
    }

    @PutMapping(ID)
    @Operation(summary = "Update ticket")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Ticket updated"))
    public ResponseEntity<TicketResponse> updateTicket(@PathVariable Long id, @RequestBody @Valid TicketRequest ticketRequest) {
        return ResponseEntity.ok(ticketService.updateTicket(id, ticketRequest));
    }

    @DeleteMapping(ID)
    @Operation(summary = "Cancel ticket")
    @ApiResponses(@ApiResponse(responseCode = "204", description = "Ticket cancelled"))
    public ResponseEntity<Void> cancelTicket(@PathVariable Long id) {
        ticketService.cancelTicket(id);
        return ResponseEntity.noContent().build();
    }
}
