package com.projects.aeroplannerrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.aeroplannerrestapi.dto.request.PaymentRequest;
import com.projects.aeroplannerrestapi.entity.Flight;
import com.projects.aeroplannerrestapi.entity.Reservation;
import com.projects.aeroplannerrestapi.entity.Role;
import com.projects.aeroplannerrestapi.entity.User;
import com.projects.aeroplannerrestapi.enums.*;
import com.projects.aeroplannerrestapi.repository.FlightRepository;
import com.projects.aeroplannerrestapi.repository.ReservationRepository;
import com.projects.aeroplannerrestapi.repository.RoleRepository;
import com.projects.aeroplannerrestapi.repository.UserRepository;
import com.projects.aeroplannerrestapi.util.AbstractContainerBaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentControllerIT extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        reservationRepository.deleteAll();
        flightRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenPaymentRequest_whenMakePayment_thenReturnPaymentResponse() throws Exception {
        // given
        User user = new User();
        user.setFullName("Full Name");
        user.setEmail("sample@email.com");
        user.setPassword(passwordEncoder.encode("password"));
        Role role = new Role();
        role.setName(RoleEnum.USER);
        role.setDescription("Default user role");
        Role savedRole = roleRepository.save(role);
        user.setRoles(Set.of(savedRole));
        User savedUser = userRepository.save(user);

        Flight flight = new Flight();
        flight.setAirline("Airline");
        flight.setFlightNumber("Flight Number");
        flight.setDepartureTime("2023-04-19T15:30:00");
        flight.setArrivalTime("2024-04-19T15:30:00");
        flight.setDuration(Duration.between(LocalDateTime.parse("2023-04-19T15:30:00"),
                LocalDateTime.parse("2024-04-19T15:30:00")));
        flight.setPrice(BigDecimal.valueOf(100.00));
        flight.setAircraftType("Aircraft Type");
        flight.setSeatAvailability(0);
        flight.setCurrentAvailableSeat(0);
        flight.setStatus(FlightStatusEnum.UNKNOWN);
        Flight savedFlight = flightRepository.save(flight);

        Reservation reservation = new Reservation();
        reservation.setPassengerId(savedUser.getId());
        reservation.setFlightId(savedFlight.getId());
        reservation.setSeatNumber(1);
        reservation.setReservationDate("2024-05-19T15:30:00");
        reservation.setReservationStatus(ReservationStatusEnum.CONFIRMED);
        Reservation savedReservation = reservationRepository.save(reservation);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPassengerId(savedUser.getId());
        paymentRequest.setFlightId(savedFlight.getId());
        paymentRequest.setSeatNumber(savedReservation.getSeatNumber());
        paymentRequest.setCardNumber("4532280979380570");
        paymentRequest.setCardHolderName("Card Holder Name");
        paymentRequest.setExpiryDate("11/30");
        paymentRequest.setCvv("4431");
        paymentRequest.setAmount(savedFlight.getPrice());

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/payments/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentRequest)));

        // then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(paymentRequest.getAmount()))
                .andExpect(jsonPath("$.status").value(PaymentStatusEnum.PAID.name()))
                .andExpect(jsonPath("$.message").value(PaymentStatusEnum.PAID.name()));
    }
}
