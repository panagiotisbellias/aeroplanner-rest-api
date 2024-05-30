package com.projects.aeroplannerrestapi.dto.request;

import com.projects.aeroplannerrestapi.enums.FlightStatusEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightRequest {
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
    private FlightStatusEnum status;
}
