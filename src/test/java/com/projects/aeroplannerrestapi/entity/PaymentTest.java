package com.projects.aeroplannerrestapi.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class PaymentTest {

    @Test
    void testAllArgsConstructor() {
        Payment payment = new Payment(0L, 1L, 2L, 3, "card number", "card holder name", "expiry date", "cvv", BigDecimal.valueOf(4), "status", "transaction id");
        assertEquals(payment);
    }

    @Test
    void testNoArgsConstructor() {
        Payment payment = new Payment();
        Assertions.assertInstanceOf(Payment.class, payment);
    }

    @Test
    void testSetters() {
        Payment payment = new Payment();
        payment.setId(0L);
        payment.setPassengerId(1L);
        payment.setFlightId(2L);
        payment.setSeatNumber(3);
        payment.setCardNumber("card number");
        payment.setCardHolderName("card holder name");
        payment.setExpiryDate("expiry date");
        payment.setCvv("cvv");
        payment.setAmount(BigDecimal.valueOf(4));
        payment.setStatus("status");
        payment.setTransactionId("transaction id");
        assertEquals(payment);
    }

    void assertEquals(Payment payment) {
        Assertions.assertEquals(0L, payment.getId());
        Assertions.assertEquals(1L, payment.getPassengerId());
        Assertions.assertEquals(2L, payment.getFlightId());
        Assertions.assertEquals(3, payment.getSeatNumber());
        Assertions.assertEquals("card number", payment.getCardNumber());
        Assertions.assertEquals("card holder name", payment.getCardHolderName());
        Assertions.assertEquals("expiry date", payment.getExpiryDate());
        Assertions.assertEquals("cvv", payment.getCvv());
        Assertions.assertEquals(BigDecimal.valueOf(4), payment.getAmount());
        Assertions.assertEquals("status", payment.getStatus());
        Assertions.assertEquals("transaction id", payment.getTransactionId());
    }

}
