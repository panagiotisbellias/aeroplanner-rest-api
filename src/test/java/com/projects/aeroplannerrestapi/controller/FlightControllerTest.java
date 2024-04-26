package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.request.FlightRequest;
import com.projects.aeroplannerrestapi.dto.response.FlightResponse;
import com.projects.aeroplannerrestapi.service.FlightService;
import com.projects.aeroplannerrestapi.util.AssertionsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

@ExtendWith(MockitoExtension.class)
class FlightControllerTest {

    @InjectMocks
    FlightController flightController;

    @Mock
    FlightService flightService;

    @Mock
    FlightRequest flightRequest;

    @Test
    void testConstructor() {
        FlightController flightController = new FlightController(flightService);
        Assertions.assertInstanceOf(FlightController.class, flightController);
    }

    @Test
    void testCreateFlight() {
        ResponseEntity<FlightResponse> response = flightController.createFlight(flightRequest);
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.CREATED, response);
    }

    @Test
    void testGetAllFlights() {
        ResponseEntity<List<FlightResponse>> response = flightController.getAllFlights();
        Assertions.assertTrue(Objects.requireNonNull(response.getBody()).isEmpty());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetFlight() {
        ResponseEntity<FlightResponse> response = flightController.getFlight(0L);
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.OK, response);
    }

    @Test
    void testUpdateFlight() {
        ResponseEntity<FlightResponse> response = flightController.updateFlight(0L, flightRequest);
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.OK, response);
    }

    @Test
    void testDeleteFlight() {
        ResponseEntity<Void> response = flightController.deleteFlight(0L);
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.NO_CONTENT, response);
    }

}
