package com.projects.aeroplannerrestapi.dto.response;

import com.projects.aeroplannerrestapi.enums.FlightStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.projects.aeroplannerrestapi.util.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class FlightResponseTest {

    @Test
    void testAllArgsConstructor() {
        FlightResponse flightResponse = new FlightResponse(0L, AIRLINE, FLIGHT_NUMBER, DEPARTURE_TIME, ARRIVAL_TIME, BigDecimal.valueOf(1), AIRCRAFT_TYPE, 2, 3, FlightStatusEnum.IN_FLIGHT);
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
        flightResponse.setAirline(AIRLINE);
        flightResponse.setFlightNumber(FLIGHT_NUMBER);
        flightResponse.setDepartureTime(DEPARTURE_TIME);
        flightResponse.setArrivalTime(ARRIVAL_TIME);
        flightResponse.setPrice(BigDecimal.valueOf(1));
        flightResponse.setAircraftType(AIRCRAFT_TYPE);
        flightResponse.setSeatAvailability(2);
        flightResponse.setCurrentAvailableSeat(3);
        flightResponse.setStatus(FlightStatusEnum.IN_FLIGHT);
        assertEquals(flightResponse);
    }

    void assertEquals(FlightResponse flightResponse) {
        Assertions.assertEquals(0L, flightResponse.getId());
        Assertions.assertEquals(AIRLINE, flightResponse.getAirline());
        Assertions.assertEquals(FLIGHT_NUMBER, flightResponse.getFlightNumber());
        Assertions.assertEquals(DEPARTURE_TIME, flightResponse.getDepartureTime());
        Assertions.assertEquals(ARRIVAL_TIME, flightResponse.getArrivalTime());
        Assertions.assertEquals(BigDecimal.valueOf(1), flightResponse.getPrice());
        Assertions.assertEquals(AIRCRAFT_TYPE, flightResponse.getAircraftType());
        Assertions.assertEquals(2, flightResponse.getSeatAvailability());
        Assertions.assertEquals(3, flightResponse.getCurrentAvailableSeat());
        Assertions.assertEquals(FlightStatusEnum.IN_FLIGHT, flightResponse.getStatus());
    }

}
