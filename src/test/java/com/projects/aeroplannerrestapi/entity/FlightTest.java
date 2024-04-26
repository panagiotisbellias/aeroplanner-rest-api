package com.projects.aeroplannerrestapi.entity;

import com.projects.aeroplannerrestapi.enums.FlightStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Duration;

@ExtendWith(MockitoExtension.class)
class FlightTest {

    @Mock
    Duration duration;

    @Test
    void testAllArgsConstructor() {
        Flight flight = new Flight(0L, "airline", "flight number", "departure time", "arrival time", duration, BigDecimal.valueOf(1), "aircraft type", 2, 3, FlightStatusEnum.IN_FLIGHT);
        assertEquals(flight);
    }

    @Test
    void testNoArgsConstructor() {
        Flight flight = new Flight();
        Assertions.assertInstanceOf(Flight.class, flight);
    }

    @Test
    void testSetters() {
        Flight flight = new Flight();
        flight.setId(0L);
        flight.setAirline("airline");
        flight.setFlightNumber("flight number");
        flight.setDepartureTime("departure time");
        flight.setArrivalTime("arrival time");
        flight.setDuration(duration);
        flight.setPrice(BigDecimal.valueOf(1));
        flight.setAircraftType("aircraft type");
        flight.setSeatAvailability(2);
        flight.setCurrentAvailableSeat(3);
        flight.setStatus(FlightStatusEnum.IN_FLIGHT);
        assertEquals(flight);
    }

    void assertEquals(Flight flight) {
        Assertions.assertEquals(0L, flight.getId());
        Assertions.assertEquals("airline", flight.getAirline());
        Assertions.assertEquals("flight number", flight.getFlightNumber());
        Assertions.assertEquals("departure time", flight.getDepartureTime());
        Assertions.assertEquals("arrival time", flight.getArrivalTime());
        Assertions.assertEquals(duration, flight.getDuration());
        Assertions.assertEquals(BigDecimal.valueOf(1), flight.getPrice());
        Assertions.assertEquals("aircraft type", flight.getAircraftType());
        Assertions.assertEquals(2, flight.getSeatAvailability());
        Assertions.assertEquals(3, flight.getCurrentAvailableSeat());
        Assertions.assertEquals(FlightStatusEnum.IN_FLIGHT, flight.getStatus());
    }

}
