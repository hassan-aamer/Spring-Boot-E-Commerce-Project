package com.eCommerce.eCommerce.service.payment;

import com.eCommerce.eCommerce.dto.PaymentDto;
import com.eCommerce.eCommerce.request.payment.CreatePaymentRequest;

public interface PaymentService {
    PaymentDto processPayment(CreatePaymentRequest request);
    PaymentDto getPaymentByOrderId(Long orderId);
    PaymentDto getPaymentById(Long paymentId);
}
