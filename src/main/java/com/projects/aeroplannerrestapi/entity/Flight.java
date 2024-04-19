package com.projects.aeroplannerrestapi.entity;

import com.projects.aeroplannerrestapi.enums.FlightStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flights_generator")
    @SequenceGenerator(name = "flights_generator", sequenceName = "flights_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String airline;

    @Column(nullable = false, unique = true)
    private String flightNumber;

    @Column(nullable = false)
    private String departureTime;

    @Column(nullable = false)
    private String arrivalTime;

    @Column(nullable = false)
    private Duration duration;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String aircraftType;

    @Column(nullable = false)
    private int seatAvailability;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FlightStatusEnum status;
}
