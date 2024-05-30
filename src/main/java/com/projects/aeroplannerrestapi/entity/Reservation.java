package com.projects.aeroplannerrestapi.entity;

import com.projects.aeroplannerrestapi.enums.ReservationStatusEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "passenger_id", nullable = false)
    private Long passengerId;

    @Column(name = "flight_id", nullable = false)
    private Long flightId;

    @Column(name = "seat_number", nullable = false)
    private int seatNumber;

    @Column(name = "reservation_date", nullable = false)
    private String reservationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status", nullable = false)
    private ReservationStatusEnum reservationStatus;
}
