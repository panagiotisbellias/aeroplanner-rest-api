package com.projects.aeroplannerrestapi.dto.request;

import com.projects.aeroplannerrestapi.enums.FlightStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class FlightRequestTest {

    @Test
    void testAllArgsConstructor() {
        FlightRequest flightRequest = new FlightRequest(0L, "airline", "flight number", "departure time", "arrival time", BigDecimal.valueOf(1), "aircraft type", 2, 0, FlightStatusEnum.IN_FLIGHT);
        assertEquals(flightRequest);
    }

    @Test
    void testNoArgsConstructor() {
        FlightRequest flightRequest = new FlightRequest();
        Assertions.assertInstanceOf(FlightRequest.class, flightRequest);
    }

    @Test
    void testSetters() {
        FlightRequest flightRequest = new FlightRequest();
        flightRequest.setId(0L);
        flightRequest.setAirline("airline");
        flightRequest.setFlightNumber("flight number");
        flightRequest.setDepartureTime("departure time");
        flightRequest.setArrivalTime("arrival time");
        flightRequest.setPrice(BigDecimal.valueOf(1));
        flightRequest.setAircraftType("aircraft type");
        flightRequest.setSeatAvailability(2);
        flightRequest.setCurrentAvailableSeat(0);
        flightRequest.setStatus(FlightStatusEnum.IN_FLIGHT);
        assertEquals(flightRequest);
    }

    void assertEquals(FlightRequest flightRequest) {
        Assertions.assertEquals(0L, flightRequest.getId());
        Assertions.assertEquals("airline", flightRequest.getAirline());
        Assertions.assertEquals("flight number", flightRequest.getFlightNumber());
        Assertions.assertEquals("departure time", flightRequest.getDepartureTime());
        Assertions.assertEquals("arrival time", flightRequest.getArrivalTime());
        Assertions.assertEquals(BigDecimal.valueOf(1), flightRequest.getPrice());
        Assertions.assertEquals("aircraft type", flightRequest.getAircraftType());
        Assertions.assertEquals(2, flightRequest.getSeatAvailability());
        Assertions.assertEquals(0, flightRequest.getCurrentAvailableSeat());
        Assertions.assertEquals(FlightStatusEnum.IN_FLIGHT, flightRequest.getStatus());
    }

}
