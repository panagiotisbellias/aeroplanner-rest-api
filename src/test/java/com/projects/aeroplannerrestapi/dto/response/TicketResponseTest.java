package com.projects.aeroplannerrestapi.dto.response;

import com.projects.aeroplannerrestapi.enums.TicketStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TicketResponseTest {

    @Test
    void testAllArgsConstructor() {
        TicketResponse ticketResponse = new TicketResponse(0L, "passenger id", "flight id", 1, "issue date", TicketStatusEnum.BOARDED);
        assertEquals(ticketResponse);
    }

    @Test
    void testNoArgsConstructor() {
        TicketResponse ticketResponse = new TicketResponse();
        Assertions.assertInstanceOf(TicketResponse.class, ticketResponse);
    }

    @Test
    void testSetters() {
        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setId(0L);
        ticketResponse.setPassengerId("passenger id");
        ticketResponse.setFlightId("flight id");
        ticketResponse.setSeatNumber(1);
        ticketResponse.setIssueDate("issue date");
        ticketResponse.setTicketStatusEnum(TicketStatusEnum.BOARDED);
        assertEquals(ticketResponse);
    }

    void assertEquals(TicketResponse ticketResponse) {
        Assertions.assertEquals(0L, ticketResponse.getId());
        Assertions.assertEquals("passenger id", ticketResponse.getPassengerId());
        Assertions.assertEquals("flight id", ticketResponse.getFlightId());
        Assertions.assertEquals(1, ticketResponse.getSeatNumber());
        Assertions.assertEquals("issue date", ticketResponse.getIssueDate());
        Assertions.assertEquals(TicketStatusEnum.BOARDED, ticketResponse.getTicketStatusEnum());
    }

}
