package com.projects.aeroplannerrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.aeroplannerrestapi.dto.request.FlightRequest;
import com.projects.aeroplannerrestapi.entity.Flight;
import com.projects.aeroplannerrestapi.enums.FlightStatusEnum;
import com.projects.aeroplannerrestapi.repository.FlightRepository;
import com.projects.aeroplannerrestapi.util.AbstractContainerBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WithMockUser(roles = "ADMIN")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FlightControllerIT extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FlightRepository flightRepository;

    private Flight flight;

    @BeforeEach
    public void setup() {
        flight = new Flight();
        flight.setAirline("Airline");
        flight.setFlightNumber("Flight Number");
        flight.setDepartureTime("2023-04-19T15:30:00");
        flight.setArrivalTime("2024-04-19T15:30:00");
        flight.setDuration(Duration.between(LocalDateTime.parse("2023-04-19T15:30:00"),
                LocalDateTime.parse("2024-04-19T15:30:00")));
        flight.setPrice(BigDecimal.valueOf(0.0));
        flight.setAircraftType("Aircraft Type");
        flight.setSeatAvailability(0);
        flight.setCurrentAvailableSeat(0);
        flight.setStatus(FlightStatusEnum.UNKNOWN);

        flightRepository.deleteAll();
    }

    @Test

    public void givenFlightRequest_whenCreateFlight_thenReturnFlightResponse() throws Exception {
        // given
        FlightRequest flightRequest = new FlightRequest();
        flightRequest.setAirline("Airline");
        flightRequest.setFlightNumber("Flight Number");
        flightRequest.setDepartureTime("2023-04-19T15:30:00");
        flightRequest.setArrivalTime("2024-04-19T15:30:00");
        flightRequest.setPrice(BigDecimal.valueOf(1.0));
        flightRequest.setAircraftType("Aircraft Type");
        flightRequest.setSeatAvailability(0);
        flightRequest.setStatus(FlightStatusEnum.UNKNOWN);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/flights")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flightRequest)));

        // then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.airline").value(flightRequest.getAirline()))
                .andExpect(jsonPath("$.flightNumber").value(flightRequest.getFlightNumber()))
                .andExpect(jsonPath("$.departureTime").value(flightRequest.getDepartureTime()))
                .andExpect(jsonPath("$.arrivalTime").value(flightRequest.getArrivalTime()))
                .andExpect(jsonPath("$.price").value(flightRequest.getPrice()))
                .andExpect(jsonPath("$.aircraftType").value(flightRequest.getAircraftType()))
                .andExpect(jsonPath("$.seatAvailability").value(flightRequest.getSeatAvailability()))
                .andExpect(jsonPath("$.currentAvailableSeat").value(flightRequest.getSeatAvailability()))
                .andExpect(jsonPath("$.status").value(flightRequest.getStatus().name()));
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void givenListOfFlights_whenGetAllFlights_thenReturnListOfFlights() throws Exception {
        // given
        Flight flight1 = new Flight();
        flight1.setAirline("Airline 1");
        flight1.setFlightNumber("Flight Number 1");
        flight1.setDepartureTime("2023-04-19T15:30:00");
        flight1.setArrivalTime("2024-04-19T15:30:00");
        flight1.setDuration(Duration.between(LocalDateTime.parse("2023-04-19T15:30:00"),
                LocalDateTime.parse("2024-04-19T15:30:00")));
        flight1.setPrice(BigDecimal.valueOf(0.0));
        flight1.setAircraftType("Aircraft Type 1");
        flight1.setSeatAvailability(0);
        flight1.setCurrentAvailableSeat(0);
        flight1.setStatus(FlightStatusEnum.UNKNOWN);

        Flight flight2 = new Flight();
        flight2.setAirline("Airline 2");
        flight2.setFlightNumber("Flight Number 2");
        flight2.setDepartureTime("2022-04-19T15:50:00");
        flight2.setArrivalTime("2023-04-19T15:50:00");
        flight2.setDuration(Duration.between(LocalDateTime.parse("2022-04-19T15:50:00"),
                LocalDateTime.parse("2023-04-19T15:50:00")));
        flight2.setPrice(BigDecimal.valueOf(1.0));
        flight2.setAircraftType("Aircraft Type 2");
        flight2.setSeatAvailability(1);
        flight2.setCurrentAvailableSeat(1);
        flight2.setStatus(FlightStatusEnum.IN_FLIGHT);

        flightRepository.saveAll(List.of(flight1, flight2));

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/flights"));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(flightRepository.findAll().size()));
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void givenFlightId_whenGetFlight_thenReturnFlightResponse() throws Exception {
        // given
        Flight savedFlight = flightRepository.save(flight);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/flights/{id}", savedFlight.getId()));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airline").value(flight.getAirline()))
                .andExpect(jsonPath("$.flightNumber").value(flight.getFlightNumber()))
                .andExpect(jsonPath("$.departureTime").value(flight.getDepartureTime()))
                .andExpect(jsonPath("$.arrivalTime").value(flight.getArrivalTime()))
                .andExpect(jsonPath("$.price").value(flight.getPrice()))
                .andExpect(jsonPath("$.aircraftType").value(flight.getAircraftType()))
                .andExpect(jsonPath("$.seatAvailability").value(flight.getSeatAvailability()))
                .andExpect(jsonPath("$.currentAvailableSeat").value(flight.getSeatAvailability()))
                .andExpect(jsonPath("$.status").value(flight.getStatus().name()));
    }

    @Test
    public void givenFlightIdAndFlightRequest_whenUpdateFlight_thenReturnFlightResponse() throws Exception {
        // given
        Flight savedFlight = flightRepository.save(flight);

        FlightRequest flightRequest = new FlightRequest();
        flightRequest.setAirline("Updated Airline");
        flightRequest.setFlightNumber("Updated Flight Number");
        flightRequest.setDepartureTime("2022-04-19T15:30:00");
        flightRequest.setArrivalTime("2023-04-19T15:30:00");
        flightRequest.setPrice(BigDecimal.valueOf(2.0));
        flightRequest.setAircraftType("Updated Aircraft Type");
        flightRequest.setSeatAvailability(1);
        flightRequest.setStatus(FlightStatusEnum.IN_FLIGHT);

        // when
        ResultActions resultActions = mockMvc.perform(put("/api/v1/flights/{id}", savedFlight.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(flightRequest)));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airline").value(flightRequest.getAirline()))
                .andExpect(jsonPath("$.flightNumber").value(flightRequest.getFlightNumber()))
                .andExpect(jsonPath("$.departureTime").value(flightRequest.getDepartureTime()))
                .andExpect(jsonPath("$.arrivalTime").value(flightRequest.getArrivalTime()))
                .andExpect(jsonPath("$.price").value(flightRequest.getPrice()))
                .andExpect(jsonPath("$.aircraftType").value(flightRequest.getAircraftType()))
                .andExpect(jsonPath("$.seatAvailability").value(flightRequest.getSeatAvailability()))
                .andExpect(jsonPath("$.currentAvailableSeat").value(flight.getCurrentAvailableSeat()))
                .andExpect(jsonPath("$.status").value(flightRequest.getStatus().name()));
    }
}
