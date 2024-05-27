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

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("integration")
@WithMockUser(roles = {"USER", "ADMIN"})
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
        Ticket ticket1 = new Ticket();
        ticket1.setPassengerId("Passenger ID 1");
        ticket1.setFlightId("Flight ID 1");
        ticket1.setReservationId("Reservation ID 1");
        ticket1.setSeatNumber(1);
        ticket1.setIssueDate("Issue Date 1");
        ticket1.setTicketStatusEnum(TicketStatusEnum.ISSUED);

        Ticket ticket2 = new Ticket();
        ticket2.setPassengerId("Passenger ID 2");
        ticket2.setFlightId("Flight ID 2");
        ticket2.setReservationId("Reservation ID 2");
        ticket2.setSeatNumber(2);
        ticket2.setIssueDate("Issue Date 2");
        ticket2.setTicketStatusEnum(TicketStatusEnum.ISSUED);

        List<Ticket> savedTickets = ticketRepository.saveAll(List.of(ticket1, ticket2));

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/tickets"));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(savedTickets.size()));
    }

    @Test
    public void givenTicketId_whenGetTicket_thenReturnTicketResponse() throws Exception {
        // given
        Ticket ticket = new Ticket();
        ticket.setPassengerId("Passenger ID");
        ticket.setFlightId("Flight ID");
        ticket.setReservationId("Reservation ID");
        ticket.setSeatNumber(1);
        ticket.setIssueDate("Issue Date");
        ticket.setTicketStatusEnum(TicketStatusEnum.ISSUED);

        Ticket savedTicket = ticketRepository.save(ticket);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/tickets/{id}", savedTicket.getId()));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ticket.getId()))
                .andExpect(jsonPath("$.passengerId").value(ticket.getPassengerId()))
                .andExpect(jsonPath("$.flightId").value(ticket.getFlightId()))
                .andExpect(jsonPath("$.seatNumber").value(ticket.getSeatNumber()))
                .andExpect(jsonPath("$.issueDate").value(ticket.getIssueDate()))
                .andExpect(jsonPath("$.ticketStatusEnum").value(ticket.getTicketStatusEnum().name()));
    }

    @Test
    public void givenTicketId_whenCancelTicket_thenReturnNothing() throws Exception {
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

        Reservation reservation = new Reservation();
        reservation.setPassengerId(1L);
        reservation.setFlightId(savedFlight.getId());
        reservation.setSeatNumber(1);
        reservation.setReservationDate("2024-05-19T15:30:00");
        reservation.setReservationStatus(ReservationStatusEnum.CONFIRMED);

        Reservation savedReservation = reservationRepository.save(reservation);

        Ticket ticket = new Ticket();
        ticket.setPassengerId("1");
        ticket.setFlightId(savedFlight.getId().toString());
        ticket.setReservationId(savedReservation.getId().toString());
        ticket.setSeatNumber(1);
        ticket.setIssueDate(LocalDateTime.now().toString());
        ticket.setTicketStatusEnum(TicketStatusEnum.ISSUED);

        Ticket savedTicket = ticketRepository.save(ticket);

        // when
        ResultActions resultActions = mockMvc.perform(delete("/api/v1/tickets/{id}", savedTicket.getId()));

        // then
        resultActions.andDo(print())
                .andExpect(status().isNoContent());
    }
}
