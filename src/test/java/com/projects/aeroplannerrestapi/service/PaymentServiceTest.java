package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.request.PaymentRequest;
import com.projects.aeroplannerrestapi.dto.response.PaymentResponse;
import com.projects.aeroplannerrestapi.entity.Flight;
import com.projects.aeroplannerrestapi.entity.Payment;
import com.projects.aeroplannerrestapi.entity.Reservation;
import com.projects.aeroplannerrestapi.enums.PaymentStatusEnum;
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
    Payment payment;

    @Test
    void testConstructor() {
        PaymentService paymentService = new PaymentServiceImpl(paymentRepository, reservationRepository, flightRepository, ticketService);
        Assertions.assertInstanceOf(PaymentService.class, paymentService);
    }

    @Test
    void testProcessPayment() {
        PaymentRequest paymentRequest = Mockito.mock(PaymentRequest.class);
        Flight flight = Mockito.mock(Flight.class);
        Reservation reservation = Mockito.mock(Reservation.class);

        Mockito.when(payment.getFlightId()).thenReturn(0L);
        Mockito.when(paymentRepository.save(ArgumentMatchers.any(Payment.class))).thenReturn(payment);
        Mockito.when(flightRepository.findById(0L)).thenReturn(Optional.of(flight));
        Mockito.when(reservationRepository.findByFlightId(0L)).thenReturn(Optional.of(reservation));

        PaymentResponse response = paymentService.processPayment(paymentRequest);
        Assertions.assertInstanceOf(String.class, response.getTransactionId());
        Assertions.assertNull(response.getAmount());
        Assertions.assertEquals(PaymentStatusEnum.PAID, response.getStatus());
        Assertions.assertEquals("Paid", response.getMessage());
    }

    @Test
    void testGetPaymentDetails() {
        Mockito.when(paymentRepository.findById(0L)).thenReturn(Optional.of(payment));
        Assertions.assertEquals(payment, paymentService.getPaymentDetails(0L));
    }

}
