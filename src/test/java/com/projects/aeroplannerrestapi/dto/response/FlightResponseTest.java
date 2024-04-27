package com.projects.aeroplannerrestapi.dto.response;

import com.projects.aeroplannerrestapi.enums.FlightStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class FlightResponseTest {

    @Test
    void testAllArgsConstructor() {
        FlightResponse flightResponse = new FlightResponse(0L, "airline", "flight number", "departure time", "arrival time", BigDecimal.valueOf(1), "aircraft type", 2, 3, FlightStatusEnum.IN_FLIGHT);
        assertEquals(flightResponse);
    }

    @Test
    void testNoArgsConstructor() {
        FlightResponse flightResponse = new FlightResponse();
        Assertions.assertInstanceOf(FlightResponse.class, flightResponse);
    }

    @Test
    void testSetters() {
        FlightResponse flightResponse = new FlightResponse();
        flightResponse.setId(0L);
        flightResponse.setAirline("airline");
        flightResponse.setFlightNumber("flight number");
        flightResponse.setDepartureTime("departure time");
        flightResponse.setArrivalTime("arrival time");
        flightResponse.setPrice(BigDecimal.valueOf(1));
        flightResponse.setAircraftType("aircraft type");
        flightResponse.setSeatAvailability(2);
        flightResponse.setCurrentAvailableSeat(3);
        flightResponse.setStatus(FlightStatusEnum.IN_FLIGHT);
        assertEquals(flightResponse);
    }

    void assertEquals(FlightResponse flightResponse) {
        Assertions.assertEquals(0L, flightResponse.getId());
        Assertions.assertEquals("airline", flightResponse.getAirline());
        Assertions.assertEquals("flight number", flightResponse.getFlightNumber());
        Assertions.assertEquals("departure time", flightResponse.getDepartureTime());
        Assertions.assertEquals("arrival time", flightResponse.getArrivalTime());
        Assertions.assertEquals(BigDecimal.valueOf(1), flightResponse.getPrice());
        Assertions.assertEquals("aircraft type", flightResponse.getAircraftType());
        Assertions.assertEquals(2, flightResponse.getSeatAvailability());
        Assertions.assertEquals(3, flightResponse.getCurrentAvailableSeat());
        Assertions.assertEquals(FlightStatusEnum.IN_FLIGHT, flightResponse.getStatus());
    }

}
