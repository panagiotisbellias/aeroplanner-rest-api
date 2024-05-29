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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static com.projects.aeroplannerrestapi.constants.PathConstants.API_V1_FLIGHTS;
import static com.projects.aeroplannerrestapi.constants.PathConstants.ID;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.ADMIN;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.USER;
import static com.projects.aeroplannerrestapi.util.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ActiveProfiles(INTEGRATION)
@WithMockUser(roles = ADMIN)
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
        flight = Flight.builder()
                .airline(AIRLINE)
                .flightNumber(FLIGHT_NUMBER)
                .departureTime(VALID_DEPARTURE_TIME)
                .arrivalTime(VALID_ARRIVAL_TIME)
                .duration(Duration.between(LocalDateTime.parse(VALID_DEPARTURE_TIME), LocalDateTime.parse(VALID_ARRIVAL_TIME)))
                .price(VALID_PRICE)
                .aircraftType(AIRCRAFT_TYPE)
                .seatAvailability(ZERO)
                .currentAvailableSeat(ZERO)
                .status(FlightStatusEnum.UNKNOWN)
                .build();

        flightRepository.deleteAll();
    }

    @Test

    public void givenFlightRequest_whenCreateFlight_thenReturnFlightResponse() throws Exception {
        // given
        FlightRequest flightRequest = FlightRequest.builder()
                .airline(AIRLINE)
                .flightNumber(FLIGHT_NUMBER)
                .departureTime(VALID_DEPARTURE_TIME)
                .arrivalTime(VALID_ARRIVAL_TIME)
                .price(VALID_PRICE)
                .aircraftType(AIRCRAFT_TYPE)
                .seatAvailability(ZERO)
                .status(FlightStatusEnum.UNKNOWN)
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(post(API_V1_FLIGHTS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flightRequest)));

        // then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath(AIRLINE_PATH).value(flightRequest.getAirline()))
                .andExpect(jsonPath(FLIGHT_NUMBER_PATH).value(flightRequest.getFlightNumber()))
                .andExpect(jsonPath(DEPARTURE_TIME_PATH).value(flightRequest.getDepartureTime()))
                .andExpect(jsonPath(ARRIVAL_TIME_PATH).value(flightRequest.getArrivalTime()))
                .andExpect(jsonPath(PRICE_PATH).value(flightRequest.getPrice()))
                .andExpect(jsonPath(AIRCRAFT_TYPE_PATH).value(flightRequest.getAircraftType()))
                .andExpect(jsonPath(SEAT_AVAILABILITY_PATH).value(flightRequest.getSeatAvailability()))
                .andExpect(jsonPath(CURRENT_SEAT_AVAILABILITY_PATH).value(flightRequest.getSeatAvailability()))
                .andExpect(jsonPath(STATUS_PATH).value(flightRequest.getStatus().name()));
    }

    @Test
    @WithMockUser(roles = {USER, ADMIN})
    public void givenListOfFlights_whenGetAllFlights_thenReturnListOfFlights() throws Exception {
        // given
        Flight flight1 = Flight.builder()
                .airline(AIRLINE.concat(ONE))
                .flightNumber(FLIGHT_NUMBER.concat(ONE))
                .departureTime(VALID_DEPARTURE_TIME)
                .arrivalTime(VALID_ARRIVAL_TIME)
                .duration(Duration.between(LocalDateTime.parse(VALID_DEPARTURE_TIME), LocalDateTime.parse(VALID_ARRIVAL_TIME)))
                .price(VALID_PRICE)
                .aircraftType(AIRCRAFT_TYPE.concat(ONE))
                .seatAvailability(ZERO)
                .currentAvailableSeat(ZERO)
                .status(FlightStatusEnum.UNKNOWN)
                .build();

        Flight flight2 = Flight.builder()
                .airline(AIRLINE.concat(TWO))
                .flightNumber(FLIGHT_NUMBER.concat(TWO))
                .departureTime(VALID_DEPARTURE_TIME)
                .arrivalTime(VALID_ARRIVAL_TIME)
                .duration(Duration.between(LocalDateTime.parse(VALID_DEPARTURE_TIME), LocalDateTime.parse(VALID_ARRIVAL_TIME)))
                .price(VALID_PRICE)
                .aircraftType(AIRCRAFT_TYPE.concat(TWO))
                .seatAvailability(ZERO)
                .currentAvailableSeat(ZERO)
                .status(FlightStatusEnum.UNKNOWN)
                .build();

        flightRepository.saveAll(List.of(flight1, flight2));

        // when
        ResultActions resultActions = mockMvc.perform(get(API_V1_FLIGHTS));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(SIZE_PATH).value(flightRepository.findAll().size()));
    }

    @Test
    @WithMockUser(roles = {USER, ADMIN})
    public void givenFlightId_whenGetFlight_thenReturnFlightResponse() throws Exception {
        // given
        Flight savedFlight = flightRepository.save(flight);

        // when
        ResultActions resultActions = mockMvc.perform(get(API_V1_FLIGHTS.concat(ID), savedFlight.getId()));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(AIRLINE_PATH).value(flight.getAirline()))
                .andExpect(jsonPath(FLIGHT_NUMBER_PATH).value(flight.getFlightNumber()))
                .andExpect(jsonPath(DEPARTURE_TIME_PATH).value(flight.getDepartureTime()))
                .andExpect(jsonPath(ARRIVAL_TIME_PATH).value(flight.getArrivalTime()))
                .andExpect(jsonPath(PRICE_PATH).value(flight.getPrice()))
                .andExpect(jsonPath(AIRCRAFT_TYPE_PATH).value(flight.getAircraftType()))
                .andExpect(jsonPath(SEAT_AVAILABILITY_PATH).value(flight.getSeatAvailability()))
                .andExpect(jsonPath(CURRENT_SEAT_AVAILABILITY_PATH).value(flight.getSeatAvailability()))
                .andExpect(jsonPath(STATUS_PATH).value(flight.getStatus().name()));
    }

    @Test
    public void givenFlightIdAndFlightRequest_whenUpdateFlight_thenReturnFlightResponse() throws Exception {
        // given
        Flight savedFlight = flightRepository.save(flight);

        FlightRequest flightRequest = FlightRequest.builder()
                .airline(UPDATED.concat(AIRLINE))
                .flightNumber(UPDATED.concat(FLIGHT_NUMBER))
                .departureTime(VALID_DEPARTURE_TIME)
                .arrivalTime(VALID_ARRIVAL_TIME)
                .price(VALID_PRICE)
                .aircraftType(UPDATED.concat(AIRCRAFT_TYPE))
                .seatAvailability(Integer.parseInt(ONE))
                .status(FlightStatusEnum.IN_FLIGHT)
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(put(API_V1_FLIGHTS.concat(ID), savedFlight.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(flightRequest)));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(AIRLINE_PATH).value(flightRequest.getAirline()))
                .andExpect(jsonPath(FLIGHT_NUMBER_PATH).value(flightRequest.getFlightNumber()))
                .andExpect(jsonPath(DEPARTURE_TIME_PATH).value(flightRequest.getDepartureTime()))
                .andExpect(jsonPath(ARRIVAL_TIME_PATH).value(flightRequest.getArrivalTime()))
                .andExpect(jsonPath(PRICE_PATH).value(flightRequest.getPrice()))
                .andExpect(jsonPath(AIRCRAFT_TYPE_PATH).value(flightRequest.getAircraftType()))
                .andExpect(jsonPath(SEAT_AVAILABILITY_PATH).value(flightRequest.getSeatAvailability()))
                .andExpect(jsonPath(CURRENT_SEAT_AVAILABILITY_PATH).value(flight.getCurrentAvailableSeat()))
                .andExpect(jsonPath(STATUS_PATH).value(flightRequest.getStatus().name()));
    }

    @Test
    public void givenFlightId_whenDeleteFlight_thenReturnNothing() throws Exception {
        // given
        Flight savedFlight = flightRepository.save(flight);

        // when
        ResultActions resultActions = mockMvc.perform(delete(API_V1_FLIGHTS.concat(ID), savedFlight.getId()));

        // then
        resultActions.andDo(print())
                .andExpect(status().isNoContent());
    }
}
