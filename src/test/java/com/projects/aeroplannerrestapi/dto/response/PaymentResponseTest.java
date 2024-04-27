package com.projects.aeroplannerrestapi.dto.response;

import com.projects.aeroplannerrestapi.enums.PaymentStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class PaymentResponseTest {

    @Test
    void testAllArgsConstructor() {
        PaymentResponse paymentResponse = new PaymentResponse("transaction id", PaymentStatusEnum.PAID, "message", BigDecimal.valueOf(0));
        assertEquals(paymentResponse);
    }

    @Test
    void testNoArgsConstructor() {
        PaymentResponse paymentResponse = new PaymentResponse();
        Assertions.assertInstanceOf(PaymentResponse.class, paymentResponse);
    }

    @Test
    void testSetters() {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setTransactionId("transaction id");
        paymentResponse.setStatus(PaymentStatusEnum.PAID);
        paymentResponse.setMessage("message");
        paymentResponse.setAmount(BigDecimal.valueOf(0));
        assertEquals(paymentResponse);
    }

    void assertEquals(PaymentResponse paymentResponse) {
        Assertions.assertEquals("transaction id", paymentResponse.getTransactionId());
        Assertions.assertEquals(PaymentStatusEnum.PAID, paymentResponse.getStatus());
        Assertions.assertEquals("message", paymentResponse.getMessage());
        Assertions.assertEquals(BigDecimal.valueOf(0), paymentResponse.getAmount());
    }

}
