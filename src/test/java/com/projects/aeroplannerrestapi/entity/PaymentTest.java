package com.projects.aeroplannerrestapi.entity;

import com.projects.aeroplannerrestapi.enums.PaymentStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.projects.aeroplannerrestapi.util.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class PaymentTest {

    @Test
    void testAllArgsConstructor() {
        Payment payment = new Payment(0L, 1L, 2L, 3, CARD_NUMBER, CARD_HOLDER_NAME, EXPIRY_DATE, CVV, BigDecimal.valueOf(4), PaymentStatusEnum.PENDING, TRANSACTION_ID);
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
        payment.setCardNumber(CARD_NUMBER);
        payment.setCardHolderName(CARD_HOLDER_NAME);
        payment.setExpiryDate(EXPIRY_DATE);
        payment.setCvv(CVV);
        payment.setStatus(PaymentStatusEnum.PENDING);
        payment.setAmount(BigDecimal.valueOf(4));
        payment.setTransactionId(TRANSACTION_ID);
        assertEquals(payment);
    }

    void assertEquals(Payment payment) {
        Assertions.assertEquals(0L, payment.getId());
        Assertions.assertEquals(1L, payment.getPassengerId());
        Assertions.assertEquals(2L, payment.getFlightId());
        Assertions.assertEquals(3, payment.getSeatNumber());
        Assertions.assertEquals(CARD_NUMBER, payment.getCardNumber());
        Assertions.assertEquals(CARD_HOLDER_NAME, payment.getCardHolderName());
        Assertions.assertEquals(EXPIRY_DATE, payment.getExpiryDate());
        Assertions.assertEquals(CVV, payment.getCvv());
        Assertions.assertEquals(BigDecimal.valueOf(4), payment.getAmount());
        Assertions.assertEquals(PaymentStatusEnum.PENDING, payment.getStatus());
        Assertions.assertEquals(TRANSACTION_ID, payment.getTransactionId());
    }

}
