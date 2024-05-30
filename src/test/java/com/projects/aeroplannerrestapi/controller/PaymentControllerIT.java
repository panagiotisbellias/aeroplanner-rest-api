package com.projects.aeroplannerrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.aeroplannerrestapi.dto.request.PaymentRequest;
import com.projects.aeroplannerrestapi.entity.*;
import com.projects.aeroplannerrestapi.enums.*;
import com.projects.aeroplannerrestapi.repository.*;
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

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

import static com.projects.aeroplannerrestapi.constants.PathConstants.*;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.ADMIN;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.USER;
import static com.projects.aeroplannerrestapi.util.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles(INTEGRATION)
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
    private PaymentRepository paymentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        reservationRepository.deleteAll();
        flightRepository.deleteAll();
        paymentRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = USER)
    public void givenPaymentRequest_whenMakePayment_thenReturnPaymentResponse() throws Exception {
        // given
        Role role = Role.builder()
                .name(RoleEnum.USER)
                .description(DEFAULT_USER_ROLE)
                .build();

        Role savedRole = roleRepository.save(role);

        User user = User.builder()
                .fullName(FULL_NAME)
                .email(VALID_EMAIL_ADDRESS)
                .password(passwordEncoder.encode(VALID_PASSWORD))
                .roles(Set.of(savedRole))
                .build();

        User savedUser = userRepository.save(user);

        Flight flight = Flight.builder()
                .airline(AIRLINE)
                .flightNumber(FLIGHT_NUMBER)
                .departureTime(VALID_DEPARTURE_TIME)
                .arrivalTime(VALID_ARRIVAL_TIME)
                .duration(Duration.between(LocalDateTime.parse(VALID_DEPARTURE_TIME), LocalDateTime.parse(VALID_ARRIVAL_TIME)))
                .price(VALID_AMOUNT)
                .aircraftType(AIRCRAFT_TYPE)
                .seatAvailability(ZERO)
                .currentAvailableSeat(ZERO)
                .status(FlightStatusEnum.UNKNOWN)
                .build();

        Flight savedFlight = flightRepository.save(flight);

        Reservation reservation = Reservation.builder()
                .passengerId(savedUser.getId())
                .flightId(savedFlight.getId())
                .seatNumber(Integer.parseInt(ONE))
                .reservationDate(VALID_RESERVATION_DATE)
                .reservationStatus(ReservationStatusEnum.CONFIRMED)
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .passengerId(savedUser.getId())
                .flightId(savedFlight.getId())
                .seatNumber(savedReservation.getSeatNumber())
                .cardNumber(VALID_CARD_NUMBER)
                .cardHolderName(CARD_HOLDER_NAME)
                .expiryDate(VALID_EXPIRY_DATE)
                .cvv(VALID_CVV)
                .amount(savedFlight.getPrice())
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(post(API_V1_PAYMENTS.concat(PAYMENT))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paymentRequest)));

        // then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath(AMOUNT_PATH).value(paymentRequest.getAmount().intValue()))
                .andExpect(jsonPath(STATUS_PATH).value(PaymentStatusEnum.PAID.name()))
                .andExpect(jsonPath(MESSAGE_PATH).value(PaymentStatusEnum.PAID.name()));
    }

    @Test
    @WithMockUser(roles = {USER, ADMIN})
    public void givenPaymentId_whenGetPaymentDetails_thenReturnPaymentObject() throws Exception {
        // given
        Payment payment = Payment.builder()
                .passengerId(VALID_PASSENGER_ID)
                .flightId(VALID_FLIGHT_ID)
                .status(PaymentStatusEnum.PAID)
                .transactionId(TRANSACTION_ID)
                .cvv(VALID_CVV)
                .amount(VALID_AMOUNT)
                .cardNumber(VALID_CARD_NUMBER)
                .cardHolderName(CARD_HOLDER_NAME)
                .expiryDate(VALID_EXPIRY_DATE)
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        // when
        ResultActions resultActions = mockMvc.perform(get(API_V1_PAYMENTS.concat(ID), savedPayment.getId()));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID_PATH).value(savedPayment.getId()))
                .andExpect(jsonPath(PASSENGER_ID_PATH).value(savedPayment.getPassengerId()))
                .andExpect(jsonPath(FLIGHT_ID_PATH).value(savedPayment.getFlightId()))
                .andExpect(jsonPath(STATUS_PATH).value(savedPayment.getStatus().name()))
                .andExpect(jsonPath(TRANSACTION_ID_PATH).value(savedPayment.getTransactionId()))
                .andExpect(jsonPath(CVV_PATH).value(savedPayment.getCvv()))
                .andExpect(jsonPath(AMOUNT_PATH).value(savedPayment.getAmount().intValue()))
                .andExpect(jsonPath(CARD_NUMBER_PATH).value(savedPayment.getCardNumber()))
                .andExpect(jsonPath(CARD_HOLDER_NAME_PATH).value(savedPayment.getCardHolderName()))
                .andExpect(jsonPath(EXPIRY_DATE_PATH).value(savedPayment.getExpiryDate()));
    }
}
