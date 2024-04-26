package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.request.PaymentRequest;
import com.projects.aeroplannerrestapi.dto.response.PaymentResponse;
import com.projects.aeroplannerrestapi.entity.Payment;
import com.projects.aeroplannerrestapi.service.PaymentService;
import com.projects.aeroplannerrestapi.util.AssertionsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    @InjectMocks
    PaymentController paymentController;

    @Mock
    PaymentService paymentService;

    @Test
    void testConstructor() {
        PaymentController paymentController = new PaymentController(paymentService);
        Assertions.assertInstanceOf(PaymentController.class, paymentController);
    }

    @Test
    void testMakePayment() {
        PaymentRequest paymentRequest = Mockito.mock(PaymentRequest.class);
        ResponseEntity<PaymentResponse> response = paymentController.makePayment(paymentRequest);
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.CREATED, response);
    }

    @Test
    void testGetPaymentDetails() {
        ResponseEntity<Payment> response = paymentController.getPaymentDetails(0L);
        AssertionsUtil.assertNullBodyStatusCode(HttpStatus.OK, response);
    }

}
