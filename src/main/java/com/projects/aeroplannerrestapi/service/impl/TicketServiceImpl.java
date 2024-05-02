package com.projects.aeroplannerrestapi.service.impl;

import com.projects.aeroplannerrestapi.dto.request.TicketRequest;
import com.projects.aeroplannerrestapi.dto.response.TicketResponse;
import com.projects.aeroplannerrestapi.entity.Reservation;
import com.projects.aeroplannerrestapi.entity.Ticket;
import com.projects.aeroplannerrestapi.enums.ReservationStatusEnum;
import com.projects.aeroplannerrestapi.enums.TicketStatusEnum;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.mapper.TicketMapper;
import com.projects.aeroplannerrestapi.repository.ReservationRepository;
import com.projects.aeroplannerrestapi.repository.TicketRepository;
import com.projects.aeroplannerrestapi.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.projects.aeroplannerrestapi.contstants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public TicketResponse createTicket(TicketRequest ticketRequest) {
        Long reservationId = ticketRequest.getReservationId();
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException(RESERVATION, ID, reservationId.toString()));
        Ticket ticket = new Ticket();
        ticket.setReservationId(reservationId.toString());
        ticket.setPassengerId(reservation.getPassengerId().toString());
        ticket.setFlightId(reservation.getFlightId().toString());
        ticket.setSeatNumber(reservation.getSeatNumber());
        ticket.setTicketStatusEnum(TicketStatusEnum.ISSUED);
        ticket.setIssueDate(LocalDateTime.now().toString());
        return TicketMapper.INSTANCE.ticketToTicketResponse(ticketRepository.save(ticket));
    }

    @Override
    public List<TicketResponse> getAllTickets() {
        return ticketRepository.findAll().stream()
                .map(TicketMapper.INSTANCE::ticketToTicketResponse)
                .toList();
    }

    @Override
    public TicketResponse getTicket(Long id) {
        return ticketRepository.findById(id)
                .map(TicketMapper.INSTANCE::ticketToTicketResponse)
                .orElseThrow(() -> new ResourceNotFoundException(TICKET, ID, id.toString()));
    }

    @Override
    public TicketResponse updateTicket(Long id, TicketRequest ticketRequest) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TICKET, ID, id.toString()));
        ticket.setTicketStatusEnum(TicketStatusEnum.ISSUED);
        ticket.setIssueDate(LocalDateTime.now().toString());
        return TicketMapper.INSTANCE.ticketToTicketResponse(ticketRepository.save(ticket));
    }

    @Override
    public void cancelTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TICKET, ID, id.toString()));
        ticket.setTicketStatusEnum(TicketStatusEnum.CANCELLED);
        ticketRepository.save(ticket);
        Long flightId = Long.parseLong(ticket.getFlightId());
        Long passengerId = Long.parseLong(ticket.getPassengerId());
        Reservation reservation = reservationRepository.findByFlightIdAndPassengerId(flightId, passengerId)
                .orElseThrow(() -> new ResourceNotFoundException(RESERVATION,
                        FLIGHT_ID_PASSENGER_ID,
                        String.format("%s : %s", flightId, passengerId)));
        reservation.setReservationStatus(ReservationStatusEnum.CANCELLED);
        reservationRepository.save(reservation);
    }
}
