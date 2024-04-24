package com.projects.aeroplannerrestapi.mapper;

import com.projects.aeroplannerrestapi.dto.PaymentRequest;
import com.projects.aeroplannerrestapi.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    Payment paymentRequestToPayment(PaymentRequest paymentRequest);
}
