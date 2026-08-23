package com.eCommerce.eCommerce.controller.payment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.eCommerce.dto.PaymentDto;
import com.eCommerce.eCommerce.request.payment.CreatePaymentRequest;
import com.eCommerce.eCommerce.response.ApiResponse;
import com.eCommerce.eCommerce.service.payment.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> processPayment(@Valid @RequestBody CreatePaymentRequest request) {
        PaymentDto paymentDto = paymentService.processPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Payment processed successfully", paymentDto));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse> getPaymentById(@PathVariable Long paymentId) {
        PaymentDto paymentDto = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(new ApiResponse("Payment fetched successfully", paymentDto));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> getPaymentByOrderId(@PathVariable Long orderId) {
        PaymentDto paymentDto = paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(new ApiResponse("Payment fetched successfully", paymentDto));
    }
}
