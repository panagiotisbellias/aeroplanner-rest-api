package com.projects.aeroplannerrestapi.entity;

import com.projects.aeroplannerrestapi.enums.FlightStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flights")
public class Flight extends BaseEntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flights_generator")
    @SequenceGenerator(name = "flights_generator", sequenceName = "flights_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String airline;

    @Column(name = "flight_number", nullable = false, unique = true)
    private String flightNumber;

    @Column(name = "departure_time", nullable= false)
    private String departureTime;

    @Column(name = "arrival_time", nullable = false)
    private String arrivalTime;

    @Column(nullable = false)
    private Duration duration;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "aircraft_type", nullable = false)
    private String aircraftType;

    @Column(name = "seat_availability", nullable = false)
    private int seatAvailability;

    @Column(name = "current_available_seat")
    private int currentAvailableSeat;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlightStatusEnum status;
}
