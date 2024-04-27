package com.projects.aeroplannerrestapi.entity;

import com.projects.aeroplannerrestapi.enums.TicketStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tickets_generator")
    @SequenceGenerator(name = "tickets_generator", sequenceName = "tickets_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "passenger_id", nullable = false, unique = true)
    private String passengerId;

    @Column(name = "flight_id", nullable = false, unique = true)
    private String flightId;

    @Column(name = "seat_number", nullable = false)
    private int seatNumber;

    @Column(name = "issue_date")
    private String issueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_status", nullable = false)
    private TicketStatusEnum ticketStatusEnum;
}
