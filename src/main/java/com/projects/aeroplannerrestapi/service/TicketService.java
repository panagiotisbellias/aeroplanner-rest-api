package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.request.TicketRequest;
import com.projects.aeroplannerrestapi.dto.response.TicketResponse;

import java.util.List;

public interface TicketService {

    TicketResponse createTicket(TicketRequest ticketRequest);

    List<TicketResponse> getAllTickets();

    TicketResponse getTicket(Long id);

    void cancelTicket(Long id);
}
