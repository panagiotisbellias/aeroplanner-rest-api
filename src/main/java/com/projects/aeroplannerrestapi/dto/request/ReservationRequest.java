package com.projects.aeroplannerrestapi.dto.request;

import com.projects.aeroplannerrestapi.enums.ReservationStatusEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {
    private Long id;
    @NotNull
    private Long passengerId;
    @NotNull
    private Long flightId;
    @Positive
    private int seatNumber;
    @NotEmpty
    private String reservationDate;
    private ReservationStatusEnum reservationStatus;
}
