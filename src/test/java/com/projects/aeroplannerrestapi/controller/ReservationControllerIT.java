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

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static com.projects.aeroplannerrestapi.constants.PathConstants.API_V1_RESERVATIONS;
import static com.projects.aeroplannerrestapi.constants.PathConstants.ID;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.USER;
import static com.projects.aeroplannerrestapi.constants.SortingAndPaginationConstants.*;
import static com.projects.aeroplannerrestapi.util.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WithMockUser(roles = USER)
@ActiveProfiles(INTEGRATION)
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
        Flight flight = Flight.builder()
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

        Flight savedFlight = flightRepository.save(flight);

        ReservationRequest reservationRequest = ReservationRequest.builder()
                .passengerId(Long.parseLong(ONE))
                .flightId(savedFlight.getId())
                .seatNumber(Integer.parseInt(ONE))
                .reservationDate(VALID_RESERVATION_DATE)
                .reservationStatus(ReservationStatusEnum.CONFIRMED)
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(post(API_V1_RESERVATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservationRequest)));

        // then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath(PASSENGER_ID_PATH).value(reservationRequest.getPassengerId()))
                .andExpect(jsonPath(FLIGHT_ID_PATH).value(reservationRequest.getFlightId()))
                .andExpect(jsonPath(SEAT_NUMBER_PATH).value(reservationRequest.getSeatNumber()))
                .andExpect(jsonPath(RESERVATION_DATE_PATH).value(reservationRequest.getReservationDate()))
                .andExpect(jsonPath(RESERVATION_STATUS_PATH).value(reservationRequest.getReservationStatus().name()));
    }

    @Test
    public void givenListOfReservations_whenGetAllReservations_thenReturnListOfReservations() throws Exception {
        // given
        Reservation reservation1 = Reservation.builder()
                .passengerId(VALID_PASSENGER_ID)
                .flightId(VALID_FLIGHT_ID)
                .seatNumber(Integer.parseInt(ONE))
                .reservationDate(VALID_RESERVATION_DATE)
                .reservationStatus(ReservationStatusEnum.CONFIRMED)
                .build();

        Reservation reservation2 = Reservation.builder()
                .passengerId(Long.parseLong(TWO))
                .flightId(Long.parseLong(TWO))
                .seatNumber(Integer.parseInt(TWO))
                .reservationDate(VALID_RESERVATION_DATE)
                .reservationStatus(ReservationStatusEnum.CONFIRMED)
                .build();

        List<Reservation> reservations = reservationRepository.saveAll(List.of(reservation1, reservation2));

        // when
        ResultActions resultActions = mockMvc.perform(get(API_V1_RESERVATIONS)
                .param(PAGE_NUM, DEFAULT_PAGE_NUM)
                .param(PAGE_SIZE, DEFAULT_PAGE_SIZE)
                .param(SORT_BY, DEFAULT_SORT_BY)
                .param(SORT_DIR, DEFAULT_SORT_DIR));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0][0].passengerId").value(reservations.get(0).getPassengerId()))
                .andExpect(jsonPath("$.content[0][0].flightId").value(reservations.get(0).getFlightId()))
                .andExpect(jsonPath("$.content[0][0].seatNumber").value(reservations.get(0).getSeatNumber()))
                .andExpect(jsonPath("$.content[0][0].reservationDate").value(reservations.get(0).getReservationDate()))
                .andExpect(jsonPath("$.content[0][0].reservationStatus").value(reservations.get(0).getReservationStatus().name()))
                .andExpect(jsonPath("$.content[0][1].passengerId").value(reservations.get(1).getPassengerId()))
                .andExpect(jsonPath("$.content[0][1].flightId").value(reservations.get(1).getFlightId()))
                .andExpect(jsonPath("$.content[0][1].seatNumber").value(reservations.get(1).getSeatNumber()))
                .andExpect(jsonPath("$.content[0][1].reservationDate").value(reservations.get(1).getReservationDate()))
                .andExpect(jsonPath("$.content[0][1].reservationStatus").value(reservations.get(1).getReservationStatus().name()));
    }

    @Test
    public void givenReservationId_whenGetReservation_thenReturnReservationResponse() throws Exception {
        // given
        Reservation reservation = Reservation.builder()
                .passengerId(VALID_PASSENGER_ID)
                .flightId(VALID_FLIGHT_ID)
                .seatNumber(Integer.parseInt(ONE))
                .reservationDate(VALID_RESERVATION_DATE)
                .reservationStatus(ReservationStatusEnum.CONFIRMED)
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);

        // when
        ResultActions resultActions = mockMvc.perform(get(API_V1_RESERVATIONS.concat(ID), savedReservation.getId()));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(PASSENGER_ID_PATH).value(savedReservation.getPassengerId()))
                .andExpect(jsonPath(FLIGHT_ID_PATH).value(savedReservation.getFlightId()))
                .andExpect(jsonPath(SEAT_NUMBER_PATH).value(savedReservation.getSeatNumber()))
                .andExpect(jsonPath(RESERVATION_DATE_PATH).value(savedReservation.getReservationDate()))
                .andExpect(jsonPath(RESERVATION_STATUS_PATH).value(savedReservation.getReservationStatus().name()));
    }

    @Test
    public void givenReservationIdAndReservationRequest_whenUpdateReservation_thenReturnUpdatedReservationObject() throws Exception {
        // given
        Flight flight = Flight.builder()
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

        Flight savedFlight = flightRepository.save(flight);

        Reservation reservation = Reservation.builder()
                .passengerId(Long.parseLong(ONE))
                .flightId(savedFlight.getId())
                .seatNumber(Integer.parseInt(ONE))
                .reservationDate(VALID_RESERVATION_DATE)
                .reservationStatus(ReservationStatusEnum.CONFIRMED)
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);

        ReservationRequest updatedReservationRequest = ReservationRequest.builder()
                .passengerId(Long.parseLong(TWO))
                .flightId(savedFlight.getId())
                .seatNumber(Integer.parseInt(TWO))
                .reservationDate(VALID_RESERVATION_DATE)
                .reservationStatus(ReservationStatusEnum.CONFIRMED)
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(put(API_V1_RESERVATIONS.concat(ID), savedReservation.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedReservationRequest)));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(PASSENGER_ID_PATH).value(updatedReservationRequest.getPassengerId()))
                .andExpect(jsonPath(FLIGHT_ID_PATH).value(updatedReservationRequest.getFlightId()))
                .andExpect(jsonPath(SEAT_NUMBER_PATH).value(updatedReservationRequest.getSeatNumber()))
                .andExpect(jsonPath(RESERVATION_DATE_PATH).value(updatedReservationRequest.getReservationDate()))
                .andExpect(jsonPath(RESERVATION_STATUS_PATH).value(updatedReservationRequest.getReservationStatus().name()));
    }

    @Test
    public void givenReservationId_whenCancelReservation_thenReturnNothing() throws Exception {
        // given
        Flight flight = Flight.builder()
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

        Flight savedFlight = flightRepository.save(flight);

        Reservation reservation = Reservation.builder()
                .passengerId(Long.parseLong(ONE))
                .flightId(savedFlight.getId())
                .seatNumber(Integer.parseInt(ONE))
                .reservationDate(VALID_RESERVATION_DATE)
                .reservationStatus(ReservationStatusEnum.CONFIRMED)
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);

        // when
        ResultActions resultActions = mockMvc.perform(delete(API_V1_RESERVATIONS.concat(ID), savedReservation.getId()));

        // then
        resultActions.andDo(print())
                .andExpect(status().isNoContent());
    }
}
