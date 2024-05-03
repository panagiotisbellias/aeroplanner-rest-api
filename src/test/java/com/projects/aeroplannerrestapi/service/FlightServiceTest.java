package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.request.FlightRequest;
import com.projects.aeroplannerrestapi.dto.response.FlightResponse;
import com.projects.aeroplannerrestapi.entity.Flight;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.repository.FlightRepository;
import com.projects.aeroplannerrestapi.service.impl.FlightServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FlightServiceTest {

    @InjectMocks
    FlightServiceImpl flightService;

    @Mock
    FlightRepository flightRepository;

    @Mock
    Flight flight;

    @Mock
    FlightRequest flightRequest;

    @BeforeEach
    public void setUp() {
        Mockito.when(flightRequest.getDepartureTime()).thenReturn("2024-04-27T17:30:22");
        Mockito.when(flightRequest.getArrivalTime()).thenReturn("2024-04-27T17:31:09");
    }

    @Test
    void testConstructor() {
        FlightService flightService = new FlightServiceImpl(flightRepository);
        Assertions.assertInstanceOf(FlightService.class, flightService);
    }

    @Test
    void testCreateFlight() {
        Assertions.assertNull(flightService.createFlight(flightRequest));
    }

    @Test
    void testCreateFlightNull() {
        NullPointerException nullPointerException = Assertions.assertThrows(NullPointerException.class, () -> flightService.createFlight(null));
        Assertions.assertEquals("Cannot invoke \"com.projects.aeroplannerrestapi.dto.request.FlightRequest.getSeatAvailability()\" because \"flightRequest\" is null", nullPointerException.getMessage());
    }

    @Test
    void testGetAllFlights() {
        Assertions.assertTrue(flightService.getAllFlights().isEmpty());
    }

    @Test
    void testGetFlight() {
        Mockito.when(flightRepository.findById(0L)).thenReturn(Optional.of(flight));
        FlightResponse response = flightService.getFlight(0L);

        Assertions.assertEquals(0L, response.getId());
        Assertions.assertEquals(flight.getAirline(), response.getAirline());
        Assertions.assertEquals(flight.getFlightNumber(), response.getFlightNumber());
        Assertions.assertEquals(flight.getDepartureTime(), response.getDepartureTime());
        Assertions.assertEquals(flight.getArrivalTime(), response.getArrivalTime());
        Assertions.assertEquals(flight.getPrice(), response.getPrice());
        Assertions.assertEquals(flight.getAircraftType(), response.getAircraftType());
        Assertions.assertEquals(flight.getSeatAvailability(), response.getSeatAvailability());
        Assertions.assertEquals(flight.getCurrentAvailableSeat(), response.getCurrentAvailableSeat());
        Assertions.assertEquals(flight.getStatus(), response.getStatus());
    }

    @Test
    void testGetFlightNotFound() {
        ResourceNotFoundException resourceNotFoundException = Assertions.assertThrows(ResourceNotFoundException.class, () -> flightService.getFlight(0L));
        Assertions.assertEquals("Flight not found with Id : 0", resourceNotFoundException.getMessage());
    }

    @Test
    void testUpdateFlight() {
        Mockito.when(flightRepository.findById(0L)).thenReturn(Optional.of(flight));
        Assertions.assertNull(flightService.updateFlight(0L, flightRequest));
    }

    @Test
    void testUpdateFlightNotFound() {
        ResourceNotFoundException resourceNotFoundException = Assertions.assertThrows(ResourceNotFoundException.class, () -> flightService.updateFlight(0L, flightRequest));
        Assertions.assertEquals("Flight not found with Id : 0", resourceNotFoundException.getMessage());
    }

    @Test
    void testDeleteFlight() {
        Mockito.when(flightRepository.findById(0L)).thenReturn(Optional.of(flight));
        flightService.deleteFlight(0L);

        Mockito.verify(flightRepository).findById(0L);
        Mockito.verify(flightRepository).delete(flight);
    }

    @Test
    void testDeleteFlightNotFound() {
        ResourceNotFoundException resourceNotFoundException = Assertions.assertThrows(ResourceNotFoundException.class, () -> flightService.deleteFlight(0L));
        Assertions.assertEquals("Flight not found with Id : 0", resourceNotFoundException.getMessage());
    }

}
