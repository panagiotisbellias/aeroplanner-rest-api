package com.projects.aeroplannerrestapi.dto.response;

import com.projects.aeroplannerrestapi.enums.PaymentStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.projects.aeroplannerrestapi.util.TestConstants.MESSAGE;
import static com.projects.aeroplannerrestapi.util.TestConstants.TRANSACTION_ID;

@ExtendWith(MockitoExtension.class)
class PaymentResponseTest {

    @Test
    void testAllArgsConstructor() {
        PaymentResponse paymentResponse = new PaymentResponse(TRANSACTION_ID, PaymentStatusEnum.PAID, MESSAGE, BigDecimal.valueOf(0));
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
        paymentResponse.setTransactionId(TRANSACTION_ID);
        paymentResponse.setStatus(PaymentStatusEnum.PAID);
        paymentResponse.setMessage(MESSAGE);
        paymentResponse.setAmount(BigDecimal.valueOf(0));
        assertEquals(paymentResponse);
    }

    void assertEquals(PaymentResponse paymentResponse) {
        Assertions.assertEquals(TRANSACTION_ID, paymentResponse.getTransactionId());
        Assertions.assertEquals(PaymentStatusEnum.PAID, paymentResponse.getStatus());
        Assertions.assertEquals(MESSAGE, paymentResponse.getMessage());
        Assertions.assertEquals(BigDecimal.valueOf(0), paymentResponse.getAmount());
    }

}
