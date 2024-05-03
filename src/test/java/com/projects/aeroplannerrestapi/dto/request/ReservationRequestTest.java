package com.projects.aeroplannerrestapi.dto.request;

import com.projects.aeroplannerrestapi.enums.ReservationStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.projects.aeroplannerrestapi.util.TestConstants.RESERVATION_DATE;

@ExtendWith(MockitoExtension.class)
class ReservationRequestTest {

    @Test
    void testAllArgsConstructor() {
        ReservationRequest reservationRequest = new ReservationRequest(0L, 1L, 2L, 3, RESERVATION_DATE, ReservationStatusEnum.CONFIRMED);
        assertEquals(reservationRequest);
    }

    @Test
    void testNoArgsConstructor() {
        ReservationRequest reservationRequest = new ReservationRequest();
        Assertions.assertInstanceOf(ReservationRequest.class, reservationRequest);
    }

    @Test
    void testSetters() {
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setId(0L);
        reservationRequest.setPassengerId(1L);
        reservationRequest.setFlightId(2L);
        reservationRequest.setSeatNumber(3);
        reservationRequest.setReservationDate(RESERVATION_DATE);
        reservationRequest.setReservationStatus(ReservationStatusEnum.CONFIRMED);
        assertEquals(reservationRequest);
    }

    void assertEquals(ReservationRequest reservationRequest) {
        Assertions.assertEquals(0L, reservationRequest.getId());
        Assertions.assertEquals(1L, reservationRequest.getPassengerId());
        Assertions.assertEquals(2L, reservationRequest.getFlightId());
        Assertions.assertEquals(3, reservationRequest.getSeatNumber());
        Assertions.assertEquals(RESERVATION_DATE, reservationRequest.getReservationDate());
        Assertions.assertEquals(ReservationStatusEnum.CONFIRMED, reservationRequest.getReservationStatus());
    }

}
