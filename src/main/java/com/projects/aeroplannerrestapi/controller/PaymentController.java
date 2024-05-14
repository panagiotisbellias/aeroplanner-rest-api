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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.projects.aeroplannerrestapi.constants.OpenApiConstants.*;
import static com.projects.aeroplannerrestapi.constants.PathConstants.*;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.USER_OR_ADMIN_ROLE_AUTHORIZATION;
import static com.projects.aeroplannerrestapi.constants.SecurityRoleConstants.USER_ROLE_AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@PreAuthorize(USER_OR_ADMIN_ROLE_AUTHORIZATION)
@RequestMapping(API_V1_PAYMENTS)
public class PaymentController {

    private static final Log LOG = LogFactory.getLog(PaymentController.class);

    private final PaymentService paymentService;

    @PostMapping(PAYMENT)
    @PreAuthorize(USER_ROLE_AUTHORIZATION)
    @Operation(summary = MAKE_PAYMENT)
    @ApiResponses(@ApiResponse(responseCode = CREATED, description = MADE_PAYMENT))
    public ResponseEntity<PaymentResponse> makePayment(@RequestBody @Valid PaymentRequest paymentRequest) {
        LOG.debug(String.format("makePayment(%s)", paymentRequest.getId()));
        return new ResponseEntity<>(paymentService.processPayment(paymentRequest), HttpStatus.CREATED);
    }

    @GetMapping(ID)
    @Operation(summary = GET_PAYMENT_DETAILS)
    @ApiResponses(@ApiResponse(responseCode = OK, description = FOUND_PAYMENT_DETAILS))
    public ResponseEntity<Payment> getPaymentDetails(@PathVariable Long id) {
        LOG.debug(String.format("getPaymentDetails(%s)", id));
        return ResponseEntity.ok(paymentService.getPaymentDetails(id));
    }
}
