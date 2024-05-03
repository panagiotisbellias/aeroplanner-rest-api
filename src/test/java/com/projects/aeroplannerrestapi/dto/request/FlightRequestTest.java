package com.projects.aeroplannerrestapi.dto.request;

import com.projects.aeroplannerrestapi.enums.FlightStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.projects.aeroplannerrestapi.util.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class FlightRequestTest {

    @Test
    void testAllArgsConstructor() {
        FlightRequest flightRequest = new FlightRequest(0L, AIRLINE, FLIGHT_NUMBER, DEPARTURE_TIME, ARRIVAL_TIME, BigDecimal.valueOf(1), AIRCRAFT_TYPE, 2, FlightStatusEnum.IN_FLIGHT);
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
        flightRequest.setAirline(AIRLINE);
        flightRequest.setFlightNumber(FLIGHT_NUMBER);
        flightRequest.setDepartureTime(DEPARTURE_TIME);
        flightRequest.setArrivalTime(ARRIVAL_TIME);
        flightRequest.setPrice(BigDecimal.valueOf(1));
        flightRequest.setAircraftType(AIRCRAFT_TYPE);
        flightRequest.setSeatAvailability(2);
        flightRequest.setStatus(FlightStatusEnum.IN_FLIGHT);
        assertEquals(flightRequest);
    }

    void assertEquals(FlightRequest flightRequest) {
        Assertions.assertEquals(0L, flightRequest.getId());
        Assertions.assertEquals(AIRLINE, flightRequest.getAirline());
        Assertions.assertEquals(FLIGHT_NUMBER, flightRequest.getFlightNumber());
        Assertions.assertEquals(DEPARTURE_TIME, flightRequest.getDepartureTime());
        Assertions.assertEquals(ARRIVAL_TIME, flightRequest.getArrivalTime());
        Assertions.assertEquals(BigDecimal.valueOf(1), flightRequest.getPrice());
        Assertions.assertEquals(AIRCRAFT_TYPE, flightRequest.getAircraftType());
        Assertions.assertEquals(2, flightRequest.getSeatAvailability());
        Assertions.assertEquals(FlightStatusEnum.IN_FLIGHT, flightRequest.getStatus());
    }

}
