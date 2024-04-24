package com.projects.aeroplannerrestapi.dto;

import com.projects.aeroplannerrestapi.enums.ReservationStatusEnum;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    private Long id;
    @NotNull
    private Long passengerId;
    @NotNull
    private Long flightId;
    @PositiveOrZero
    private int seatNumber;
    @NotEmpty
    private String reservationDate;
    private ReservationStatusEnum reservationStatus;
}
