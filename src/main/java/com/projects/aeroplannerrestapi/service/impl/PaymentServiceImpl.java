package com.projects.aeroplannerrestapi.service.impl;

import com.projects.aeroplannerrestapi.dto.request.PaymentRequest;
import com.projects.aeroplannerrestapi.dto.request.TicketRequest;
import com.projects.aeroplannerrestapi.dto.response.PaymentResponse;
import com.projects.aeroplannerrestapi.entity.Flight;
import com.projects.aeroplannerrestapi.entity.Payment;
import com.projects.aeroplannerrestapi.entity.Reservation;
import com.projects.aeroplannerrestapi.enums.PaymentStatusEnum;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.mapper.PaymentMapper;
import com.projects.aeroplannerrestapi.repository.FlightRepository;
import com.projects.aeroplannerrestapi.repository.PaymentRepository;
import com.projects.aeroplannerrestapi.repository.ReservationRepository;
import com.projects.aeroplannerrestapi.service.PaymentService;
import com.projects.aeroplannerrestapi.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    private final TicketService ticketService;

    @Override
    @Transactional
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        Payment payment = PaymentMapper.INSTANCE.paymentRequestToPayment(paymentRequest);
        payment.setStatus(PaymentStatusEnum.PAID);
        payment.setTransactionId(UUID.randomUUID().toString());
        Payment savedPayment = paymentRepository.save(payment);
        Long flightId = savedPayment.getFlightId();
        Long passengerId = savedPayment.getPassengerId();
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "id", flightId.toString()));
        Reservation reservation = reservationRepository.findByFlightIdAndPassengerId(flightId, passengerId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation",
                        "flight id and passenger id",
                        String.format("%s : %s", flightId, passengerId)));
        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setReservationId(reservation.getId());
        ticketService.createTicket(ticketRequest);
        return PaymentResponse.builder()
                .transactionId(savedPayment.getTransactionId())
                .amount(flight.getPrice())
                .status(savedPayment.getStatus())
                .message("Paid")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Payment getPaymentDetails(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id.toString()));
    }
}
