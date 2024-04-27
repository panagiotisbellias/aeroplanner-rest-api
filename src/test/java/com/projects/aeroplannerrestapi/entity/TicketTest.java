package com.projects.aeroplannerrestapi.entity;

import com.projects.aeroplannerrestapi.enums.TicketStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class TicketTest {

    @Test
    void testAllArgsConstructor() {
        Ticket ticket = new Ticket(0L, "passenger id", "flight id", 1, "issue date", TicketStatusEnum.BOARDED);
        assertEquals(ticket);
    }

    @Test
    void testNoArgsConstructor() {
        Ticket ticket = new Ticket();
        Assertions.assertInstanceOf(Ticket.class, ticket);
    }

    @Test
    void testSetters() {
        Ticket ticket = new Ticket();
        ticket.setId(0L);
        ticket.setPassengerId("passenger id");
        ticket.setFlightId("flight id");
        ticket.setSeatNumber(1);
        ticket.setIssueDate("issue date");
        ticket.setTicketStatusEnum(TicketStatusEnum.BOARDED);
        assertEquals(ticket);
    }

    void assertEquals(Ticket ticket) {
        Assertions.assertEquals(0L, ticket.getId());
        Assertions.assertEquals("passenger id", ticket.getPassengerId());
        Assertions.assertEquals("flight id", ticket.getFlightId());
        Assertions.assertEquals(1, ticket.getSeatNumber());
        Assertions.assertEquals("issue date", ticket.getIssueDate());
        Assertions.assertEquals(TicketStatusEnum.BOARDED, ticket.getTicketStatusEnum());
    }

}
