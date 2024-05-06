package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.request.ReservationRequest;
import com.projects.aeroplannerrestapi.dto.response.PaginatedAndSortedReservationResponse;
import com.projects.aeroplannerrestapi.entity.Flight;
import com.projects.aeroplannerrestapi.entity.Reservation;
import com.projects.aeroplannerrestapi.enums.ReservationStatusEnum;
import com.projects.aeroplannerrestapi.repository.FlightRepository;
import com.projects.aeroplannerrestapi.repository.ReservationRepository;
import com.projects.aeroplannerrestapi.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static com.projects.aeroplannerrestapi.constants.SortingAndPaginationConstants.DEFAULT_SORT_DIR;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    ReservationServiceImpl reservationService;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    FlightRepository flightRepository;

    @Mock
    Page<Reservation> page;

    @Mock
    ReservationRequest reservationRequest;

    @Mock
    Reservation reservation;

    @Mock
    Flight flight;

    @Test
    void testConstructor() {
        ReservationService reservationService = new ReservationServiceImpl(reservationRepository, flightRepository);
        Assertions.assertInstanceOf(ReservationService.class, reservationService);
    }

    @Test
    void testCreateReservation() {
        Mockito.when(reservationRequest.getFlightId()).thenReturn(0L);
        Mockito.when(flightRepository.findById(0L)).thenReturn(Optional.of(flight));
        Assertions.assertNull(reservationService.createReservation(reservationRequest));
    }

    @Test
    void testCreateReservationNullRequest() {
        NullPointerException nullPointerException = Assertions.assertThrows(NullPointerException.class, () -> reservationService.createReservation(null));
        Assertions.assertEquals("Cannot invoke \"com.projects.aeroplannerrestapi.dto.request.ReservationRequest.getFlightId()\" because \"reservationRequest\" is null", nullPointerException.getMessage());
    }

    @Test
    void testGetAllReservationsAsc() {
        Mockito.when(reservationRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(page);
        PaginatedAndSortedReservationResponse response = reservationService.getAllReservations(1, 2, "sort by", DEFAULT_SORT_DIR);

        Assertions.assertEquals(1, response.getContent().size());
        Assertions.assertEquals(0, response.getTotalPages());
        Assertions.assertEquals(0L, response.getTotalElements());
        Assertions.assertEquals(0, response.getPageNumber());
        Assertions.assertEquals(0, response.getPageSize());
        Assertions.assertFalse(response.isLast());
    }

    @Test
    void testGetAllReservationsDesc() {
        Mockito.when(reservationRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(page);
        PaginatedAndSortedReservationResponse response = reservationService.getAllReservations(1, 2, "sort by", "desc");

        Assertions.assertEquals(1, response.getContent().size());
        Assertions.assertEquals(0, response.getTotalPages());
        Assertions.assertEquals(0L, response.getTotalElements());
        Assertions.assertEquals(0, response.getPageNumber());
        Assertions.assertEquals(0, response.getPageSize());
        Assertions.assertFalse(response.isLast());
    }

    @Test
    void testGetReservation() {
        Mockito.when(reservationRepository.findById(0L)).thenReturn(Optional.of(reservation));
        Assertions.assertNotNull(reservationService.getReservation(0L));
    }

    @Test
    void testUpdateReservation() {
        Mockito.when(reservationRequest.getFlightId()).thenReturn(0L);
        Mockito.when(reservationRepository.findById(0L)).thenReturn(Optional.of(reservation));
        Mockito.when(flightRepository.findById(0L)).thenReturn(Optional.of(flight));
        Assertions.assertNull(reservationService.updateReservation(0L, reservationRequest));
    }

    @Test
    void testCancelReservation() {
        Mockito.when(reservation.getFlightId()).thenReturn(0L);
        Mockito.when(reservationRepository.findById(0L)).thenReturn(Optional.of(reservation));
        Mockito.when(flightRepository.findById(0L)).thenReturn(Optional.of(flight));
        reservationService.cancelReservation(0L);

        Mockito.verify(reservationRepository).findById(0L);
        Mockito.verify(reservation).getFlightId();
        Mockito.verify(flightRepository).findById(0L);
        Mockito.verify(flight).setCurrentAvailableSeat(0);
        Mockito.verify(flight).getCurrentAvailableSeat();
        Mockito.verify(reservation).getSeatNumber();
        Mockito.verify(flightRepository).save(flight);
        Mockito.verify(reservation).setReservationStatus(ReservationStatusEnum.CANCELLED);
        Mockito.verify(reservationRepository).save(reservation);
    }

}
