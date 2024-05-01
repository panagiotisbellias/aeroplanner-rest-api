package com.projects.aeroplannerrestapi.scheduler;

import com.projects.aeroplannerrestapi.entity.Flight;
import com.projects.aeroplannerrestapi.entity.Reservation;
import com.projects.aeroplannerrestapi.enums.ReservationStatusEnum;
import com.projects.aeroplannerrestapi.repository.FlightRepository;
import com.projects.aeroplannerrestapi.repository.ReservationRepository;
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
    Reservation reservation;

    @Mock
    Flight flight;

    @Test
    void testConstructor() {
        ReservationCheckTask reservationCheckTask = new ReservationCheckTask(reservationRepository, flightRepository);
        Assertions.assertInstanceOf(ReservationCheckTask.class, reservationCheckTask);
    }

    @Test
    void testCheckReservations() {
        Mockito.when(reservation.getReservationDate()).thenReturn(LocalDateTime.now().plusDays(1).toString());
        Mockito.when(reservationRepository.findByReservationStatus(ReservationStatusEnum.CONFIRMED)).thenReturn(List.of(reservation));
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);
        Mockito.when(flightRepository.findById(0L)).thenReturn(Optional.of(flight));

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
    void testCheckReservationsDateNow() {
        Mockito.when(reservationRepository.findByReservationStatus(ReservationStatusEnum.CONFIRMED)).thenReturn(List.of(reservation));
        Mockito.when(reservation.getReservationDate()).thenReturn(LocalDateTime.now().toString());

        reservationCheckTask.checkReservations();
        verifyCommonInvocations();
        verifyInvalidDateInvocations();
    }

    @Test
    void testCheckReservationsDateBefore() {
        Mockito.when(reservationRepository.findByReservationStatus(ReservationStatusEnum.CONFIRMED)).thenReturn(List.of(reservation));
        Mockito.when(reservation.getReservationDate()).thenReturn(LocalDateTime.now().plusDays(3).toString());

        reservationCheckTask.checkReservations();
        verifyInvalidDateInvocations();
    }

    void verifyInvalidDateInvocations() {
        verifyCommonInvocations();
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
