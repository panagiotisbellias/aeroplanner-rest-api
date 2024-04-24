package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.PaymentRequest;
import com.projects.aeroplannerrestapi.dto.PaymentResponse;
import com.projects.aeroplannerrestapi.entity.Payment;

public interface PaymentService {

    PaymentResponse processPayment(PaymentRequest paymentRequest);

    Payment getPaymentDetails(Long id);
}
