package com.projects.aeroplannerrestapi.dto;

import com.projects.aeroplannerrestapi.enums.FlightStatusEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightDto {
    private Long id;
    @NotEmpty
    private String airline;
    @NotEmpty
    private String flightNumber;
    @NotEmpty
    private String departureTime;
    @NotEmpty
    private String arrivalTime;
    @DecimalMin(value = "0")
    private BigDecimal price;
    @NotEmpty
    private String aircraftType;
    @PositiveOrZero
    private int seatAvailability;
    @NotEmpty
    private FlightStatusEnum status;
}
