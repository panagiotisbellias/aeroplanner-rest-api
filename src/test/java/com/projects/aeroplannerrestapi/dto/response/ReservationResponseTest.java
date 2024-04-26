package com.projects.aeroplannerrestapi.dto.response;

import com.projects.aeroplannerrestapi.enums.ReservationStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationResponseTest {

    @Test
    void testAllArgsConstructor() {
        ReservationResponse reservationResponse = new ReservationResponse(0L, 1L, 2L, 3, "reservation date", ReservationStatusEnum.CONFIRMED);
        assertEquals(reservationResponse);
    }

    @Test
    void testNoArgsConstructor() {
        ReservationResponse reservationResponse = new ReservationResponse();
        Assertions.assertInstanceOf(ReservationResponse.class, reservationResponse);
    }

    @Test
    void testSetters() {
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setId(0L);
        reservationResponse.setPassengerId(1L);
        reservationResponse.setFlightId(2L);
        reservationResponse.setSeatNumber(3);
        reservationResponse.setReservationDate("reservation date");
        reservationResponse.setReservationStatus(ReservationStatusEnum.CONFIRMED);
        assertEquals(reservationResponse);
    }

    void assertEquals(ReservationResponse reservationResponse) {
        Assertions.assertEquals(0L, reservationResponse.getId());
        Assertions.assertEquals(1L, reservationResponse.getPassengerId());
        Assertions.assertEquals(2L, reservationResponse.getFlightId());
        Assertions.assertEquals(3, reservationResponse.getSeatNumber());
        Assertions.assertEquals("reservation date", reservationResponse.getReservationDate());
        Assertions.assertEquals(ReservationStatusEnum.CONFIRMED, reservationResponse.getReservationStatus());
    }

}
