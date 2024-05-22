package com.projects.aeroplannerrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.aeroplannerrestapi.dto.request.FlightRequest;
import com.projects.aeroplannerrestapi.enums.FlightStatusEnum;
import com.projects.aeroplannerrestapi.util.AbstractContainerBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FlightControllerIT extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenFlightRequest_whenCreateFlight_thenReturnFlightResponse() throws Exception {
        // given
        FlightRequest flightRequest = new FlightRequest();
        flightRequest.setAirline("Airline");
        flightRequest.setFlightNumber("Flight Number");
        flightRequest.setDepartureTime("2023-04-19T15:30:00");
        flightRequest.setArrivalTime("2024-04-19T15:30:00");
        flightRequest.setPrice(BigDecimal.valueOf(1));
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
}
