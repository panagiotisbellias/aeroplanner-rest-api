package com.projects.aeroplannerrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.aeroplannerrestapi.entity.Flight;
import com.projects.aeroplannerrestapi.entity.Reservation;
import com.projects.aeroplannerrestapi.entity.Ticket;
import com.projects.aeroplannerrestapi.enums.FlightStatusEnum;
import com.projects.aeroplannerrestapi.enums.ReservationStatusEnum;
import com.projects.aeroplannerrestapi.enums.TicketStatusEnum;
import com.projects.aeroplannerrestapi.repository.FlightRepository;
import com.projects.aeroplannerrestapi.repository.ReservationRepository;
import com.projects.aeroplannerrestapi.repository.TicketRepository;
import com.projects.aeroplannerrestapi.util.AbstractContainerBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static com.projects.aeroplannerrestapi.constants.PathConstants.API_V1_TICKETS;
import static com.projects.aeroplannerrestapi.constants.PathConstants.ID;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.ADMIN;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.USER;
import static com.projects.aeroplannerrestapi.util.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles(INTEGRATION)
@WithMockUser(roles = {USER, ADMIN})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TicketControllerIT extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setup() {
        ticketRepository.deleteAll();
        flightRepository.deleteAll();
        reservationRepository.deleteAll();
    }

    @Test
    public void givenListOfTicket_whenGetAllTickets_thenReturnListOfTicket() throws Exception {
        // given
        Ticket ticket1 = Ticket.builder()
                .passengerId(PASSENGER_ID.concat(ONE))
                .flightId(FLIGHT_ID.concat(ONE))
                .reservationId(RESERVATION_ID.concat(ONE))
                .seatNumber(Integer.parseInt(ONE))
                .issueDate(ISSUE_DATE.concat(ONE))
                .ticketStatusEnum(TicketStatusEnum.ISSUED)
                .build();

        Ticket ticket2 = Ticket.builder()
                .passengerId(PASSENGER_ID.concat(TWO))
                .flightId(FLIGHT_ID.concat(TWO))
                .reservationId(RESERVATION_ID.concat(TWO))
                .seatNumber(Integer.parseInt(TWO))
                .issueDate(ISSUE_DATE.concat(TWO))
                .ticketStatusEnum(TicketStatusEnum.ISSUED)
                .build();

        List<Ticket> savedTickets = ticketRepository.saveAll(List.of(ticket1, ticket2));

        // when
        ResultActions resultActions = mockMvc.perform(get(API_V1_TICKETS));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(SIZE_PATH).value(savedTickets.size()));
    }

    @Test
    public void givenTicketId_whenGetTicket_thenReturnTicketResponse() throws Exception {
        // given
        Ticket ticket = Ticket.builder()
                .passengerId(PASSENGER_ID)
                .flightId(FLIGHT_ID)
                .reservationId(RESERVATION_ID)
                .seatNumber(Integer.parseInt(ONE))
                .issueDate(ISSUE_DATE.concat(ONE))
                .ticketStatusEnum(TicketStatusEnum.ISSUED)
                .build();

        Ticket savedTicket = ticketRepository.save(ticket);

        // when
        ResultActions resultActions = mockMvc.perform(get(API_V1_TICKETS.concat(ID), savedTicket.getId()));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID_PATH).value(ticket.getId()))
                .andExpect(jsonPath(PASSENGER_ID_PATH).value(ticket.getPassengerId()))
                .andExpect(jsonPath(FLIGHT_ID_PATH).value(ticket.getFlightId()))
                .andExpect(jsonPath(SEAT_NUMBER_PATH).value(ticket.getSeatNumber()))
                .andExpect(jsonPath(ISSUE_DATE_PATH).value(ticket.getIssueDate()))
                .andExpect(jsonPath(TICKET_STATUS_ENUM_PATH).value(ticket.getTicketStatusEnum().name()));
    }

    @Test
    public void givenTicketId_whenCancelTicket_thenReturnNothing() throws Exception {
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
                .passengerId(VALID_PASSENGER_ID)
                .flightId(savedFlight.getId())
                .seatNumber(Integer.parseInt(ONE))
                .reservationDate(VALID_RESERVATION_DATE)
                .reservationStatus(ReservationStatusEnum.CONFIRMED)
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);

        Ticket ticket = Ticket.builder()
                .passengerId(ONE)
                .flightId(savedFlight.getId().toString())
                .reservationId(savedReservation.getId().toString())
                .seatNumber(savedReservation.getSeatNumber())
                .issueDate(LocalDateTime.now().toString())
                .ticketStatusEnum(TicketStatusEnum.ISSUED)
                .build();

        Ticket savedTicket = ticketRepository.save(ticket);

        // when
        ResultActions resultActions = mockMvc.perform(delete(API_V1_TICKETS.concat(ID), savedTicket.getId()));

        // then
        resultActions.andDo(print())
                .andExpect(status().isNoContent());
    }
}
