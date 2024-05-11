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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.projects.aeroplannerrestapi.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private static final Log LOG = LogFactory.getLog(PaymentServiceImpl.class);

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    private final TicketService ticketService;
    private final EmailService emailService;

    @Override
    @Transactional
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        LOG.debug(String.format("processPayment(%s)", paymentRequest));
        Payment payment = PaymentMapper.INSTANCE.paymentRequestToPayment(paymentRequest);
        payment.setStatus(PaymentStatusEnum.PAID);
        payment.setTransactionId(UUID.randomUUID().toString());
        Payment savedPayment = paymentRepository.save(payment);
        LOG.info("Payment done");
        Long flightId = savedPayment.getFlightId();
        Long passengerId = savedPayment.getPassengerId();
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException(FLIGHT, ID, flightId.toString()));
        Reservation reservation = reservationRepository.findByFlightIdAndPassengerId(flightId, passengerId)
                .orElseThrow(() -> new ResourceNotFoundException(RESERVATION,
                        FLIGHT_ID_PASSENGER_ID,
                        String.format("%s : %s", flightId, passengerId)));
        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setReservationId(reservation.getId());
        TicketResponse ticketResponse = ticketService.createTicket(ticketRequest);
        emailService.sendEmail(ticketResponse);
        return PaymentResponse.builder()
                .transactionId(savedPayment.getTransactionId())
                .amount(flight.getPrice())
                .status(savedPayment.getStatus())
                .message(PaymentStatusEnum.PAID.name())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Payment getPaymentDetails(Long id) {
        LOG.debug(String.format("getPaymentDetails(%s)", id));
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PAYMENT, ID, id.toString()));
    }
}
