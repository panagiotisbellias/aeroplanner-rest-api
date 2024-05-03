package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.request.TicketRequest;
import com.projects.aeroplannerrestapi.entity.Reservation;
import com.projects.aeroplannerrestapi.entity.Ticket;
import com.projects.aeroplannerrestapi.enums.ReservationStatusEnum;
import com.projects.aeroplannerrestapi.enums.TicketStatusEnum;
import com.projects.aeroplannerrestapi.exception.ResourceNotFoundException;
import com.projects.aeroplannerrestapi.repository.ReservationRepository;
import com.projects.aeroplannerrestapi.repository.TicketRepository;
import com.projects.aeroplannerrestapi.service.impl.TicketServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @InjectMocks
    TicketServiceImpl ticketService;

    @Mock
    TicketRepository ticketRepository;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    TicketRequest ticketRequest;

    @Mock
    Ticket ticket;

    @Mock
    Reservation reservation;

    @Test
    void testConstructor() {
        TicketService ticketService = new TicketServiceImpl(ticketRepository, reservationRepository);
        Assertions.assertInstanceOf(TicketService.class, ticketService);
    }

    @Test
    void testCreateTicket() {
        Mockito.when(ticketRequest.getReservationId()).thenReturn(0L);
        Mockito.when(reservationRepository.findById(0L)).thenReturn(Optional.of(reservation));
        Assertions.assertNull(ticketService.createTicket(ticketRequest));
    }

    @Test
    void testGetAllTickets() {
        Assertions.assertTrue(ticketService.getAllTickets().isEmpty());
    }

    @Test
    void testGetTicket() {
        Mockito.when(ticketRepository.findById(0L)).thenReturn(Optional.of(ticket));
        Assertions.assertNotNull(ticketService.getTicket(0L));
    }

    @Test
    void testUpdateTicket() {
        Mockito.when(ticketRepository.findById(0L)).thenReturn(Optional.of(ticket));
        Assertions.assertNull(ticketService.updateTicket(0L, ticketRequest));
    }

    @Test
    void testCancelTicket() {
        Mockito.when(ticket.getFlightId()).thenReturn("0");
        Mockito.when(ticket.getPassengerId()).thenReturn("1");
        Mockito.when(ticketRepository.findById(0L)).thenReturn(Optional.of(ticket));
        Mockito.when(reservationRepository.findByFlightIdAndPassengerId(0L, 1L)).thenReturn(Optional.of(reservation));
        ticketService.cancelTicket(0L);

        Mockito.verify(ticketRepository).findById(0L);
        Mockito.verify(ticket).setTicketStatusEnum(TicketStatusEnum.CANCELLED);
        Mockito.verify(ticketRepository).save(ticket);
        Mockito.verify(ticket).getFlightId();
        Mockito.verify(ticket).getPassengerId();
        Mockito.verify(reservationRepository).findByFlightIdAndPassengerId(0L, 1L);
        Mockito.verify(reservation).setReservationStatus(ReservationStatusEnum.CANCELLED);
        Mockito.verify(reservationRepository).save(reservation);
    }

    @Test
    void testCancelTicketReservationNotFound() {
        Mockito.when(ticket.getFlightId()).thenReturn("0");
        Mockito.when(ticket.getPassengerId()).thenReturn("1");
        Mockito.when(ticketRepository.findById(0L)).thenReturn(Optional.of(ticket));

        ResourceNotFoundException resourceNotFoundException = Assertions.assertThrows(ResourceNotFoundException.class, () -> ticketService.cancelTicket(0L));
        Assertions.assertEquals("Reservation not found with Fight Id and Passenger Id : 0 : 1", resourceNotFoundException.getMessage());
    }

}
