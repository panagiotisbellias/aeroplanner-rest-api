package com.projects.aeroplannerrestapi.entity;

import com.projects.aeroplannerrestapi.enums.TicketStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.RESERVATION_ID;
import static com.projects.aeroplannerrestapi.util.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class TicketTest {

    @Test
    void testAllArgsConstructor() {
        Ticket ticket = new Ticket(0L, PASSENGER_ID, FLIGHT_ID, RESERVATION_ID, 1, ISSUE_DATE, TicketStatusEnum.BOARDED);
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
        ticket.setPassengerId(PASSENGER_ID);
        ticket.setFlightId(FLIGHT_ID);
        ticket.setReservationId(RESERVATION_ID);
        ticket.setSeatNumber(1);
        ticket.setIssueDate(ISSUE_DATE);
        ticket.setTicketStatusEnum(TicketStatusEnum.BOARDED);
        assertEquals(ticket);
    }

    void assertEquals(Ticket ticket) {
        Assertions.assertEquals(0L, ticket.getId());
        Assertions.assertEquals(PASSENGER_ID, ticket.getPassengerId());
        Assertions.assertEquals(FLIGHT_ID, ticket.getFlightId());
        Assertions.assertEquals(RESERVATION_ID, ticket.getReservationId());
        Assertions.assertEquals(1, ticket.getSeatNumber());
        Assertions.assertEquals(ISSUE_DATE, ticket.getIssueDate());
        Assertions.assertEquals(TicketStatusEnum.BOARDED, ticket.getTicketStatusEnum());
    }

}
