package com.eCommerce.eCommerce.service.order;

import java.util.List;

import com.eCommerce.eCommerce.dto.OrderDto;
import com.eCommerce.eCommerce.model.OrderStatus;
import com.eCommerce.eCommerce.request.order.CreateOrderRequest;

public interface OrderService {

	OrderDto createOrder(CreateOrderRequest request);

	OrderDto updateOrderStatus(Long orderId, OrderStatus newStatus);

	void cancelOrder(Long orderId);

	List<OrderDto> getOrdersByUserId(Long userId);

	OrderDto getOrderById(Long orderId);
}
