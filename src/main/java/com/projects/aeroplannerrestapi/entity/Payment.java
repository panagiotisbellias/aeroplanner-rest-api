package com.projects.aeroplannerrestapi.entity;

import com.projects.aeroplannerrestapi.enums.PaymentStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment extends BaseEntityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "passenger_id")
    private Long passengerId;

    @Column(name = "flight_id")
    private Long flightId;

    @Column(name = "seat_number")
    private int seatNumber;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_holder_name")
    private String cardHolderName;

    @Column(name = "expiry_date")
    private String expiryDate;

    @Column(name = "cvv")
    private String cvv;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum status;

    @Column(name = "transaction_id")
    private String transactionId;
}
