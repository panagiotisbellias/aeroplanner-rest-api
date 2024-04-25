package com.projects.aeroplannerrestapi.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private Long id;
    @NotNull
    private Long passengerId;
    @NotNull
    private Long flightId;
    @PositiveOrZero
    private int seatNumber;
    @NotBlank(message = "Card number is mandatory")
    @CreditCardNumber(message = "Invalid credit card number")
    private String cardNumber;
    @NotBlank(message = "Card holder name is mandatory")
    @Size(min = 2, max = 100, message = "Card holder name must be between 2 and 100 characters")
    private String cardHolderName;
    @NotBlank(message = "Expiry date is mandatory")
    @Size(min = 5, max = 5, message = "Expiry date should be in the format MM/YY")
    private String expiryDate;
    @NotBlank(message = "CVV is mandatory")
    @Size(min = 3, max = 4, message = "CVV should be 3 or 4 digits")
    private String cvv;
    @Positive(message = "Amount must be greater than 0")
    private BigDecimal amount;
}
