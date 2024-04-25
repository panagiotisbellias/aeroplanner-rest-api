package com.projects.aeroplannerrestapi.dto.response;

import com.projects.aeroplannerrestapi.enums.FlightStatusEnum;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightResponse {
    private Long id;
    private String airline;
    private String flightNumber;
    private String departureTime;
    private String arrivalTime;
    private BigDecimal price;
    private String aircraftType;
    private int seatAvailability;
    private int currentAvailableSeat;
    private FlightStatusEnum status;
}
