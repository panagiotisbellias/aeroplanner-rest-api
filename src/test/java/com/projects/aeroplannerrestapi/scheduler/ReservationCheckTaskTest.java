package com.projects.aeroplannerrestapi.scheduler;

import com.projects.aeroplannerrestapi.entity.Flight;
import com.projects.aeroplannerrestapi.entity.Reservation;
import com.projects.aeroplannerrestapi.entity.Ticket;
import com.projects.aeroplannerrestapi.enums.ReservationStatusEnum;
import com.projects.aeroplannerrestapi.enums.TicketStatusEnum;
import com.projects.aeroplannerrestapi.repository.FlightRepository;
import com.projects.aeroplannerrestapi.repository.ReservationRepository;
import com.projects.aeroplannerrestapi.repository.TicketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ReservationCheckTaskTest {

    @InjectMocks
    ReservationCheckTask reservationCheckTask;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    FlightRepository flightRepository;

    @Mock
    TicketRepository ticketRepository;

    @Mock
    Reservation reservation;

    @Mock
    Flight flight;

    @Mock
    Ticket ticket;

    @Test
    void testConstructor() {
        ReservationCheckTask reservationCheckTask = new ReservationCheckTask(reservationRepository, flightRepository, ticketRepository);
        Assertions.assertInstanceOf(ReservationCheckTask.class, reservationCheckTask);
    }

    @Test
    void testCheckReservations() {
        Mockito.when(ticket.getTicketStatusEnum()).thenReturn(TicketStatusEnum.BOARDED);
        Mockito.when(reservation.getReservationDate()).thenReturn(LocalDateTime.now().plusDays(1).toString());
        Mockito.when(reservationRepository.findByReservationStatus(ReservationStatusEnum.CONFIRMED)).thenReturn(List.of(reservation));
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);
        Mockito.when(flightRepository.findById(0L)).thenReturn(Optional.of(flight));
        Mockito.when(ticketRepository.findByReservationId("0")).thenReturn(Optional.of(ticket));

        reservationCheckTask.checkReservations();
        verifyCommonInvocations();

        Mockito.verify(reservation).getFlightId();
        Mockito.verify(reservation).setReservationStatus(ReservationStatusEnum.CANCELLED);
        Mockito.verify(reservationRepository).save(reservation);
        Mockito.verify(flightRepository).findById(0L);
        Mockito.verify(flight).setCurrentAvailableSeat(0);
        Mockito.verify(flight).getCurrentAvailableSeat();
        Mockito.verify(reservation).getSeatNumber();
        Mockito.verify(flightRepository).save(flight);
    }

    @Test
    void testCheckReservationsTicketIssued() {
        Mockito.when(ticket.getTicketStatusEnum()).thenReturn(TicketStatusEnum.ISSUED);
        Mockito.when(reservationRepository.findByReservationStatus(ReservationStatusEnum.CONFIRMED)).thenReturn(List.of(reservation));
        Mockito.when(ticketRepository.findByReservationId("0")).thenReturn(Optional.of(ticket));

        reservationCheckTask.checkReservations();
        Mockito.verify(reservationRepository).findByReservationStatus(ReservationStatusEnum.CONFIRMED);
        verifyInvalidDateInvocations();
    }

    @Test
    void testCheckReservationsDateNow() {
        Mockito.when(ticket.getTicketStatusEnum()).thenReturn(TicketStatusEnum.BOARDED);
        Mockito.when(reservationRepository.findByReservationStatus(ReservationStatusEnum.CONFIRMED)).thenReturn(List.of(reservation));
        Mockito.when(reservation.getReservationDate()).thenReturn(LocalDateTime.now().toString());
        Mockito.when(ticketRepository.findByReservationId("0")).thenReturn(Optional.of(ticket));

        reservationCheckTask.checkReservations();
        verifyCommonInvocations();
        verifyInvalidDateInvocations();
    }

    @Test
    void testCheckReservationsDateBefore() {
        Mockito.when(ticket.getTicketStatusEnum()).thenReturn(TicketStatusEnum.BOARDED);
        Mockito.when(reservationRepository.findByReservationStatus(ReservationStatusEnum.CONFIRMED)).thenReturn(List.of(reservation));
        Mockito.when(reservation.getReservationDate()).thenReturn(LocalDateTime.now().plusDays(3).toString());
        Mockito.when(ticketRepository.findByReservationId("0")).thenReturn(Optional.of(ticket));

        reservationCheckTask.checkReservations();
        verifyCommonInvocations();
        verifyInvalidDateInvocations();
    }

    void verifyInvalidDateInvocations() {
        Mockito.verify(reservation).getId();
        Mockito.verifyNoMoreInteractions(reservation);
        Mockito.verifyNoMoreInteractions(reservationRepository);
        Mockito.verifyNoInteractions(flightRepository);
        Mockito.verifyNoInteractions(flight);
    }

    void verifyCommonInvocations() {
        Mockito.verify(reservationRepository).findByReservationStatus(ReservationStatusEnum.CONFIRMED);
        Mockito.verify(reservation).getReservationDate();
    }

}
