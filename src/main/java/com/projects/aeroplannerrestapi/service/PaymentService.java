package com.projects.aeroplannerrestapi.service;

import com.projects.aeroplannerrestapi.dto.request.PaymentRequest;
import com.projects.aeroplannerrestapi.dto.response.PaymentResponse;
import com.projects.aeroplannerrestapi.entity.Payment;

public interface PaymentService {

    PaymentResponse processPayment(PaymentRequest paymentRequest, String cacheKey);

    Payment getPaymentDetails(Long id, String cacheKey);
}
