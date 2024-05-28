package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.constants.ErrorMessage;
import com.projects.aeroplannerrestapi.dto.request.PaymentRequest;
import com.projects.aeroplannerrestapi.dto.response.PaymentResponse;
import com.projects.aeroplannerrestapi.entity.Flight;
import com.projects.aeroplannerrestapi.entity.Payment;
import com.projects.aeroplannerrestapi.entity.Reservation;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.repository.FlightRepository;
import com.projects.aeroplannerrestapi.repository.PaymentRepository;
import com.projects.aeroplannerrestapi.repository.ReservationRepository;
import com.projects.aeroplannerrestapi.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    FlightRepository flightRepository;

    @Mock
    TicketService ticketService;

    @Mock
    EmailService emailService;

    @Mock
    Payment payment;

    @Mock
    PaymentRequest paymentRequest;

    @Mock
    Flight flight;

    @Test
    void testConstructor() {
        PaymentService paymentService = new PaymentServiceImpl(paymentRepository, reservationRepository, flightRepository, ticketService, emailService);
        Assertions.assertInstanceOf(PaymentService.class, paymentService);
    }

    @Test
    void testProcessPayment() {
        Reservation reservation = Mockito.mock(Reservation.class);
        Mockito.when(payment.getFlightId()).thenReturn(0L);
        Mockito.when(payment.getPassengerId()).thenReturn(1L);
        Mockito.when(paymentRepository.save(ArgumentMatchers.any(Payment.class))).thenReturn(payment);
        Mockito.when(flightRepository.findById(0L)).thenReturn(Optional.of(flight));
        Mockito.when(reservationRepository.findByFlightIdAndPassengerId(0L, 1L)).thenReturn(Optional.of(reservation));

        PaymentResponse response = paymentService.processPayment(paymentRequest, "");
        Assertions.assertNull(response.getTransactionId());
        Assertions.assertNull(response.getAmount());
        Assertions.assertNull(response.getStatus());
        Assertions.assertEquals("PAID", response.getMessage());
    }

    @Test
    void testProcessPaymentNull() {
        NullPointerException nullPointerException = Assertions.assertThrows(NullPointerException.class, () -> paymentService.processPayment(null, ""));
        Assertions.assertEquals("Cannot invoke \"com.projects.aeroplannerrestapi.entity.Payment.setStatus(com.projects.aeroplannerrestapi.enums.PaymentStatusEnum)\" because \"payment\" is null", nullPointerException.getMessage());
    }

    @Test
    void testProcessPaymentReservationNotFound() {
        Mockito.when(payment.getFlightId()).thenReturn(1L);
        Mockito.when(payment.getPassengerId()).thenReturn(2L);
        Mockito.when(paymentRepository.save(ArgumentMatchers.any(Payment.class))).thenReturn(payment);
        Mockito.when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        ResourceNotFoundException resourceNotFoundException = Assertions.assertThrows(ResourceNotFoundException.class, () -> paymentService.processPayment(paymentRequest, ""));
        Assertions.assertEquals(ErrorMessage.RESERVATION.concat(" not found with ").concat(ErrorMessage.FLIGHT_ID_PASSENGER_ID).concat(" : 1 : 2"), resourceNotFoundException.getMessage());
    }

    @Test
    void testGetPaymentDetails() {
        Mockito.when(paymentRepository.findById(0L)).thenReturn(Optional.of(payment));
        Assertions.assertEquals(payment, paymentService.getPaymentDetails(0L, ""));
    }

}
