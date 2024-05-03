package com.projects.aeroplannerrestapi.entity;

import com.projects.aeroplannerrestapi.enums.FlightStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Duration;

import static com.projects.aeroplannerrestapi.util.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class FlightTest {

    @Mock
    Duration duration;

    @Test
    void testAllArgsConstructor() {
        Flight flight = new Flight(0L, AIRLINE, FLIGHT_NUMBER, DEPARTURE_TIME, ARRIVAL_TIME, duration, BigDecimal.valueOf(1), AIRCRAFT_TYPE, 2, 3, FlightStatusEnum.IN_FLIGHT);
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
        flight.setAirline(AIRLINE);
        flight.setFlightNumber(FLIGHT_NUMBER);
        flight.setDepartureTime(DEPARTURE_TIME);
        flight.setArrivalTime(ARRIVAL_TIME);
        flight.setDuration(duration);
        flight.setPrice(BigDecimal.valueOf(1));
        flight.setAircraftType(AIRCRAFT_TYPE);
        flight.setSeatAvailability(2);
        flight.setCurrentAvailableSeat(3);
        flight.setStatus(FlightStatusEnum.IN_FLIGHT);
        assertEquals(flight);
    }

    void assertEquals(Flight flight) {
        Assertions.assertEquals(0L, flight.getId());
        Assertions.assertEquals(AIRLINE, flight.getAirline());
        Assertions.assertEquals(FLIGHT_NUMBER, flight.getFlightNumber());
        Assertions.assertEquals(DEPARTURE_TIME, flight.getDepartureTime());
        Assertions.assertEquals(ARRIVAL_TIME, flight.getArrivalTime());
        Assertions.assertEquals(duration, flight.getDuration());
        Assertions.assertEquals(BigDecimal.valueOf(1), flight.getPrice());
        Assertions.assertEquals(AIRCRAFT_TYPE, flight.getAircraftType());
        Assertions.assertEquals(2, flight.getSeatAvailability());
        Assertions.assertEquals(3, flight.getCurrentAvailableSeat());
        Assertions.assertEquals(FlightStatusEnum.IN_FLIGHT, flight.getStatus());
    }

}
