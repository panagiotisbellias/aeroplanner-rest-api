package com.projects.aeroplannerrestapi.controller;

import com.projects.aeroplannerrestapi.dto.request.PaymentRequest;
import com.projects.aeroplannerrestapi.dto.response.PaymentResponse;
import com.projects.aeroplannerrestapi.entity.Payment;
import com.projects.aeroplannerrestapi.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.projects.aeroplannerrestapi.constants.PathConstants.*;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.USER_OR_ADMIN_ROLE_AUTHORIZATION;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.USER_ROLE_AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@PreAuthorize(USER_OR_ADMIN_ROLE_AUTHORIZATION)
@RequestMapping(API_V1_PAYMENTS)
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping(PAYMENT)
    @PreAuthorize(USER_ROLE_AUTHORIZATION)
    @Operation(summary = "Make payment")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "Made payment"))
    public ResponseEntity<PaymentResponse> makePayment(@RequestBody @Valid PaymentRequest paymentRequest) {
        return new ResponseEntity<>(paymentService.processPayment(paymentRequest), HttpStatus.CREATED);
    }

    @GetMapping(ID)
    @Operation(summary = "Get payment details")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Found payment details"))
    public ResponseEntity<Payment> getPaymentDetails(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentDetails(id));
    }
}
