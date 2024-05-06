package com.projects.aeroplannerrestapi.dto.response;

import com.projects.aeroplannerrestapi.enums.TicketStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.projects.aeroplannerrestapi.constants.EmailConstants.*;

@ExtendWith(MockitoExtension.class)
class TicketResponseTest {

    @Test
    void testAllArgsConstructor() {
        TicketResponse ticketResponse = new TicketResponse(0L, PASSENGER_ID, FLIGHT_ID, 1, ISSUE_DATE, TicketStatusEnum.BOARDED);
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
        ticketResponse.setPassengerId(PASSENGER_ID);
        ticketResponse.setFlightId(FLIGHT_ID);
        ticketResponse.setSeatNumber(1);
        ticketResponse.setIssueDate(ISSUE_DATE);
        ticketResponse.setTicketStatusEnum(TicketStatusEnum.BOARDED);
        assertEquals(ticketResponse);
    }

    void assertEquals(TicketResponse ticketResponse) {
        Assertions.assertEquals(0L, ticketResponse.getId());
        Assertions.assertEquals(PASSENGER_ID, ticketResponse.getPassengerId());
        Assertions.assertEquals(FLIGHT_ID, ticketResponse.getFlightId());
        Assertions.assertEquals(1, ticketResponse.getSeatNumber());
        Assertions.assertEquals(ISSUE_DATE, ticketResponse.getIssueDate());
        Assertions.assertEquals(TicketStatusEnum.BOARDED, ticketResponse.getTicketStatusEnum());
    }

}
