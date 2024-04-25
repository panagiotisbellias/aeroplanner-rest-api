package com.projects.aeroplannerrestapi.dto.response;

import com.projects.aeroplannerrestapi.enums.PaymentStatusEnum;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private String transactionId;
    private PaymentStatusEnum status;
    private String message;
    private BigDecimal amount;
}
