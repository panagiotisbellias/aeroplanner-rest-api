package com.projects.aeroplannerrestapi.entity;

import com.projects.aeroplannerrestapi.enums.ReservationStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationTest {

    @Test
    void testAllArgsConstructor() {
        Reservation reservation = new Reservation(0L, 1L, 2L, 3, "reservation date", ReservationStatusEnum.CONFIRMED);
        assertEquals(reservation);
    }

    @Test
    void testNoArgsConstructor() {
        Reservation reservation = new Reservation();
        Assertions.assertInstanceOf(Reservation.class, reservation);
    }

    @Test
    void testSetters() {
        Reservation reservation = new Reservation();
        reservation.setId(0L);
        reservation.setPassengerId(1L);
        reservation.setFlightId(2L);
        reservation.setSeatNumber(3);
        reservation.setReservationDate("reservation date");
        reservation.setReservationStatus(ReservationStatusEnum.CONFIRMED);
        assertEquals(reservation);
    }

    void assertEquals(Reservation reservation) {
        Assertions.assertEquals(0L, reservation.getId());
        Assertions.assertEquals(1L, reservation.getPassengerId());
        Assertions.assertEquals(2L, reservation.getFlightId());
        Assertions.assertEquals(3, reservation.getSeatNumber());
        Assertions.assertEquals("reservation date", reservation.getReservationDate());
        Assertions.assertEquals(ReservationStatusEnum.CONFIRMED, reservation.getReservationStatus());
    }

}
