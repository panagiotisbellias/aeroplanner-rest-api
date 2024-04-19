package com.projects.aeroplannerrestapi.dto;

import com.projects.aeroplannerrestapi.enums.FlightStatusEnum;
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
    private String airline;
    private String flightNumber;
    private String departureTime;
    private String arrivalTime;
    private BigDecimal price;
    private String aircraftType;
    private int seatAvailability;
    private FlightStatusEnum status;
}
