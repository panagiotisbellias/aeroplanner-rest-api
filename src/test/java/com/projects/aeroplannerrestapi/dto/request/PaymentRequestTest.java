package com.projects.aeroplannerrestapi.dto.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class PaymentRequestTest {

    @Test
    void testAllArgsConstructor() {
        PaymentRequest paymentRequest = new PaymentRequest(0L, 1L, 2L, 3, "card number", "card holder name", "expiry date", "cvv", BigDecimal.valueOf(1));
        assertEquals(paymentRequest);
    }

    @Test
    void testNoArgsConstructor() {
        PaymentRequest paymentRequest = new PaymentRequest();
        Assertions.assertInstanceOf(PaymentRequest.class, paymentRequest);
    }

    @Test
    void testSetters() {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setId(0L);
        paymentRequest.setPassengerId(1L);
        paymentRequest.setFlightId(2L);
        paymentRequest.setSeatNumber(3);
        paymentRequest.setCardNumber("card number");
        paymentRequest.setCardHolderName("card holder name");
        paymentRequest.setExpiryDate("expiry date");
        paymentRequest.setCvv("cvv");
        paymentRequest.setAmount(BigDecimal.valueOf(1));
        assertEquals(paymentRequest);
    }

    void assertEquals(PaymentRequest paymentRequest) {
        Assertions.assertEquals(0L, paymentRequest.getId());
        Assertions.assertEquals(1L, paymentRequest.getPassengerId());
        Assertions.assertEquals(2L, paymentRequest.getFlightId());
        Assertions.assertEquals(3, paymentRequest.getSeatNumber());
        Assertions.assertEquals("card number", paymentRequest.getCardNumber());
        Assertions.assertEquals("card holder name", paymentRequest.getCardHolderName());
        Assertions.assertEquals("expiry date", paymentRequest.getExpiryDate());
        Assertions.assertEquals("cvv", paymentRequest.getCvv());
        Assertions.assertEquals(BigDecimal.valueOf(1), paymentRequest.getAmount());
    }

}
