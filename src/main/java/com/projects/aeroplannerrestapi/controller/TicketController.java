package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.request.TicketRequest;
import com.projects.aeroplannerrestapi.dto.response.TicketResponse;
import com.projects.aeroplannerrestapi.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.projects.aeroplannerrestapi.constants.OpenApiConstants.*;
import static com.projects.aeroplannerrestapi.constants.PathConstants.API_V1_TICKETS;
import static com.projects.aeroplannerrestapi.constants.PathConstants.ID;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.USER_OR_ADMIN_ROLE_AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_TICKETS)
@PreAuthorize(USER_OR_ADMIN_ROLE_AUTHORIZATION)
public class TicketController {

    private static final Log LOG = LogFactory.getLog(TicketController.class);

    private final TicketService ticketService;

    @GetMapping
    @Operation(summary = GET_ALL_TICKETS)
    @ApiResponses(@ApiResponse(responseCode = OK, description = FOUND_ALL_THE_TICKETS))
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        LOG.debug("getAllTickets()");
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping(ID)
    @Operation(summary = GET_TICKET)
    @ApiResponses(@ApiResponse(responseCode = OK, description = FOUND_THE_TICKET))
    public ResponseEntity<TicketResponse> getTicket(@PathVariable Long id) {
        LOG.debug(String.format("getTicket(%d)", id));
        return ResponseEntity.ok(ticketService.getTicket(id));
    }

    @PutMapping(ID)
    @Operation(summary = UPDATE_TICKET)
    @ApiResponses(@ApiResponse(responseCode = OK, description = TICKET_UPDATED))
    public ResponseEntity<TicketResponse> updateTicket(@PathVariable Long id, @RequestBody @Valid TicketRequest ticketRequest) {
        LOG.debug(String.format("updateTicket(%d, %s)", id, ticketRequest.getId()));
        return ResponseEntity.ok(ticketService.updateTicket(id, ticketRequest));
    }

    @DeleteMapping(ID)
    @Operation(summary = CANCEL_TICKET)
    @ApiResponses(@ApiResponse(responseCode = NO_CONTENT, description = TICKET_CANCELLED))
    public ResponseEntity<Void> cancelTicket(@PathVariable Long id) {
        LOG.debug(String.format("cancelTicket(%s)", id));
        ticketService.cancelTicket(id);
        LOG.info(TICKET_CANCELLED);
        return ResponseEntity.noContent().build();
    }
}
