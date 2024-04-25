package com.projects.aeroplannerrestapi.dto.response;

import com.projects.aeroplannerrestapi.enums.ReservationStatusEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationResponse {
    private Long id;
    private Long passengerId;
    private Long flightId;
    private int seatNumber;
    private String reservationDate;
    private ReservationStatusEnum reservationStatus;
}
