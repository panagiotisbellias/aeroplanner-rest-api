package com.projects.aeroplannerrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.aeroplannerrestapi.dto.request.ReservationRequest;
import com.projects.aeroplannerrestapi.entity.Flight;
import com.projects.aeroplannerrestapi.entity.Reservation;
import com.projects.aeroplannerrestapi.enums.FlightStatusEnum;
import com.projects.aeroplannerrestapi.enums.ReservationStatusEnum;
import com.projects.aeroplannerrestapi.repository.FlightRepository;
import com.projects.aeroplannerrestapi.repository.ReservationRepository;
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

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WithMockUser(roles = "USER")
@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerIT extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setup() {
        flightRepository.deleteAll();
        reservationRepository.deleteAll();
    }

    @Test
    public void givenReservationRequest_whenCreateReservation_thenReturnReservationResponse() throws Exception {
        // given
        Flight flight = new Flight();
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
        Flight savedFlight = flightRepository.save(flight);

        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setPassengerId(1L);
        reservationRequest.setFlightId(savedFlight.getId());
        reservationRequest.setSeatNumber(1);
        reservationRequest.setReservationDate("2024-05-19T15:30:00");
        reservationRequest.setReservationStatus(ReservationStatusEnum.CONFIRMED);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservationRequest)));

        // then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.passengerId").value(reservationRequest.getPassengerId()))
                .andExpect(jsonPath("$.flightId").value(reservationRequest.getFlightId()))
                .andExpect(jsonPath("$.seatNumber").value(reservationRequest.getSeatNumber()))
                .andExpect(jsonPath("$.reservationDate").value(reservationRequest.getReservationDate()))
                .andExpect(jsonPath("$.reservationStatus").value(reservationRequest.getReservationStatus().name()));
    }

    @Test
    public void givenListOfReservations_whenGetAllReservations_thenReturnListOfReservations() throws Exception {
        // given
        Reservation reservation1 = new Reservation();
        reservation1.setPassengerId(1L);
        reservation1.setFlightId(1L);
        reservation1.setSeatNumber(1);
        reservation1.setReservationDate("2024-05-19T15:30:00");
        reservation1.setReservationStatus(ReservationStatusEnum.CONFIRMED);

        Reservation reservation2 = new Reservation();
        reservation2.setPassengerId(2L);
        reservation2.setFlightId(2L);
        reservation2.setSeatNumber(2);
        reservation2.setReservationDate("2024-05-19T15:30:00");
        reservation2.setReservationStatus(ReservationStatusEnum.CONFIRMED);

        List<Reservation> reservations = reservationRepository.saveAll(List.of(reservation1, reservation2));

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/reservations")
                .param("pageNum", "1")
                .param("pageSize", "10")
                .param("sortBy", "id")
                .param("sortDir", "asc"));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0][0].passengerId").value(reservation1.getPassengerId()))
                .andExpect(jsonPath("$.content[0][0].flightId").value(reservation1.getFlightId()))
                .andExpect(jsonPath("$.content[0][0].seatNumber").value(reservation1.getSeatNumber()))
                .andExpect(jsonPath("$.content[0][0].reservationDate").value(reservation1.getReservationDate()))
                .andExpect(jsonPath("$.content[0][0].reservationStatus").value(reservation1.getReservationStatus().name()))
                .andExpect(jsonPath("$.content[0][1].passengerId").value(reservation2.getPassengerId()))
                .andExpect(jsonPath("$.content[0][1].flightId").value(reservation2.getFlightId()))
                .andExpect(jsonPath("$.content[0][1].seatNumber").value(reservation2.getSeatNumber()))
                .andExpect(jsonPath("$.content[0][1].reservationDate").value(reservation2.getReservationDate()))
                .andExpect(jsonPath("$.content[0][1].reservationStatus").value(reservation2.getReservationStatus().name()));
    }
}
