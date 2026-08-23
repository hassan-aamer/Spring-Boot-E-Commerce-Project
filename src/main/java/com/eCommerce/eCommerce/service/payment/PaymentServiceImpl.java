package com.eCommerce.eCommerce.service.payment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eCommerce.eCommerce.dto.PaymentDto;
import com.eCommerce.eCommerce.exceptions.ResourceNotFoundException;
import com.eCommerce.eCommerce.model.Order;
import com.eCommerce.eCommerce.model.OrderStatus;
import com.eCommerce.eCommerce.model.Payment;
import com.eCommerce.eCommerce.model.PaymentStatus;
import com.eCommerce.eCommerce.repository.order.OrderRepository;
import com.eCommerce.eCommerce.repository.payment.PaymentRepository;
import com.eCommerce.eCommerce.request.payment.CreatePaymentRequest;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public PaymentDto processPayment(CreatePaymentRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for id: " + request.getOrderId()));

        Payment payment = new Payment(order, request.getAmount(), request.getPaymentMethod(), PaymentStatus.COMPLETED);
        Payment savedPayment = paymentRepository.save(payment);

        order.setStatus(OrderStatus.PROCESSING);
        orderRepository.save(order);

        return mapToDto(savedPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentDto getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for order id: " + orderId));
        return mapToDto(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentDto getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for id: " + paymentId));
        return mapToDto(payment);
    }

    private PaymentDto mapToDto(Payment payment) {
        return new PaymentDto(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getPaymentDate()
        );
    }
}
