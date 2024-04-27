package com.projects.aeroplannerrestapi.dto.request;

import com.projects.aeroplannerrestapi.enums.ReservationStatusEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
