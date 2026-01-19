package com.eCommerce.eCommerce.controller.order;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.eCommerce.dto.OrderDto;
import com.eCommerce.eCommerce.model.Order;
import com.eCommerce.eCommerce.model.OrderStatus;
import com.eCommerce.eCommerce.request.order.CreateOrderRequest;
import com.eCommerce.eCommerce.request.order.UpdateOrderRequest;
import com.eCommerce.eCommerce.service.order.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderDto createOrder(@RequestBody CreateOrderRequest request) {
		return orderService.createOrder(request);
	}

	@PutMapping("/{orderId}")
	public OrderDto updateOrder(@PathVariable Long orderId, @RequestBody UpdateOrderRequest request) {
		return orderService.updateOrderStatus(orderId, request.getStatus());
	}

	@PutMapping("/{orderId}/status")
	public OrderDto updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus newStatus) {
		return orderService.updateOrderStatus(orderId, newStatus);
	}

	@DeleteMapping("/{orderId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelOrder(@PathVariable Long orderId) {
		orderService.cancelOrder(orderId);
	}

	@GetMapping("/user/{userId}")
	public List<OrderDto> getOrdersByUserId(@PathVariable Long userId) {
		return orderService.getOrdersByUserId(userId);
	}

	@GetMapping("/{orderId}")
	public OrderDto getOrderById(@PathVariable Long orderId) {
		return orderService.getOrderById(orderId);
	}

}
