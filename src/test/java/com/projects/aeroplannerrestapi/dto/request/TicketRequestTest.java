package com.projects.aeroplannerrestapi.dto.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TicketRequestTest {

    @Test
    void testAllArgsConstructor() {
        TicketRequest ticketRequest = new TicketRequest(0L, 1L);
        assertEquals(ticketRequest);
    }

    @Test
    void testNoArgsConstructor() {
        TicketRequest ticketRequest = new TicketRequest();
        Assertions.assertInstanceOf(TicketRequest.class, ticketRequest);
    }

    @Test
    void testSetters() {
        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setId(0L);
        ticketRequest.setReservationId(1L);
        assertEquals(ticketRequest);
    }

    void assertEquals(TicketRequest ticketRequest) {
        Assertions.assertEquals(0L, ticketRequest.getId());
        Assertions.assertEquals(1L, ticketRequest.getReservationId());
    }

}
