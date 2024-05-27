package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.response.TicketResponse;
import com.projects.aeroplannerrestapi.service.TicketService;
import com.projects.aeroplannerrestapi.util.AssertionsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

@ExtendWith(MockitoExtension.class)
class TicketControllerTest {

    @InjectMocks
    TicketController ticketController;

    @Mock
    TicketService ticketService;

    @Test
    void testConstructor() {
        TicketController ticketController = new TicketController(ticketService);
        Assertions.assertInstanceOf(TicketController.class, ticketController);
    }

    @Test
    void testGetAllTickets() {
        ResponseEntity<List<TicketResponse>> response = ticketController.getAllTickets();
        Assertions.assertTrue(Objects.requireNonNull(response.getBody()).isEmpty());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetTicket() {
        ResponseEntity<TicketResponse> response = ticketController.getTicket(0L);
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.OK, response);
    }

    @Test
    void testCancelTicket() {
        ResponseEntity<Void> response = ticketController.cancelTicket(0L);
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.NO_CONTENT, response);
    }

}
