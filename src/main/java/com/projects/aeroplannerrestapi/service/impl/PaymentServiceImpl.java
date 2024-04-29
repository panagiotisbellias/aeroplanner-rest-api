package com.projects.aeroplannerrestapi.service.impl;

import com.projects.aeroplannerrestapi.dto.request.PaymentRequest;
import com.projects.aeroplannerrestapi.dto.request.TicketRequest;
import com.projects.aeroplannerrestapi.dto.response.PaymentResponse;
import com.projects.aeroplannerrestapi.dto.response.TicketResponse;
import com.projects.aeroplannerrestapi.entity.Flight;
import com.projects.aeroplannerrestapi.entity.Payment;
import com.projects.aeroplannerrestapi.entity.Reservation;
import com.projects.aeroplannerrestapi.enums.PaymentStatusEnum;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.mapper.PaymentMapper;
import com.projects.aeroplannerrestapi.repository.FlightRepository;
import com.projects.aeroplannerrestapi.repository.PaymentRepository;
import com.projects.aeroplannerrestapi.repository.ReservationRepository;
import com.projects.aeroplannerrestapi.service.EmailService;
import com.projects.aeroplannerrestapi.service.PaymentService;
import com.projects.aeroplannerrestapi.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final EmailService emailService;
    private final SimpleMailMessage template;

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
        TicketResponse ticketResponse = ticketService.createTicket(ticketRequest);
        String to = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        String subject = template.getSubject();
        String text = String.format(template.getText(), to,
                ticketResponse.getPassengerId().toString(),
                ticketResponse.getFlightId().toString(),
                ticketResponse.getSeatNumber(),
                ticketResponse.getIssueDate().toString(),
                ticketResponse.getTicketStatusEnum().toString());
        emailService.emailUser(to, subject, text);
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
