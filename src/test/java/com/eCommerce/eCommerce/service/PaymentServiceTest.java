package com.eCommerce.eCommerce.service;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.eCommerce.eCommerce.dto.PaymentDto;
import com.eCommerce.eCommerce.model.Order;
import com.eCommerce.eCommerce.model.Payment;
import com.eCommerce.eCommerce.model.PaymentStatus;
import com.eCommerce.eCommerce.repository.order.OrderRepository;
import com.eCommerce.eCommerce.repository.payment.PaymentRepository;
import com.eCommerce.eCommerce.request.payment.CreatePaymentRequest;
import com.eCommerce.eCommerce.service.payment.PaymentServiceImpl;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void processPayment_Success() {
        Order order = new Order();
        order.setId(1L);

        CreatePaymentRequest request = new CreatePaymentRequest(1L, BigDecimal.valueOf(250.00), "CREDIT_CARD");

        Payment payment = new Payment(order, BigDecimal.valueOf(250.00), "CREDIT_CARD", PaymentStatus.COMPLETED);
        payment.setId(100L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentDto result = paymentService.processPayment(request);

        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals(BigDecimal.valueOf(250.00), result.getAmount());
        assertEquals(PaymentStatus.COMPLETED, result.getPaymentStatus());
        verify(paymentRepository).save(any(Payment.class));
    }
}
