package com.eCommerce.eCommerce.service.order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.eCommerce.eCommerce.dto.CartItemDto;
import com.eCommerce.eCommerce.dto.OrderDto;
import com.eCommerce.eCommerce.exceptions.ResourceNotFoundException;
import com.eCommerce.eCommerce.model.CartItem;
import com.eCommerce.eCommerce.model.Order;
import com.eCommerce.eCommerce.model.OrderStatus;
import com.eCommerce.eCommerce.model.User;
import com.eCommerce.eCommerce.repository.card.CartItemRepository;
import com.eCommerce.eCommerce.repository.order.OrderRepository;
import com.eCommerce.eCommerce.repository.user.UserRepository;
import com.eCommerce.eCommerce.request.order.CreateOrderRequest;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final CartItemRepository cartItemRepository;
	private final UserRepository userRepository;

	public OrderServiceImpl(OrderRepository orderRepository, CartItemRepository cartItemRepository,
			UserRepository userRepository) {
		this.orderRepository = orderRepository;
		this.cartItemRepository = cartItemRepository;
		this.userRepository = userRepository;
	}

	@Override
	public OrderDto createOrder(CreateOrderRequest request) {
		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found for id: " + request.getUserId()));

		List<CartItem> cartItems = cartItemRepository.findAllById(request.getCartItemIds());

		if (cartItems == null || cartItems.isEmpty()) {
			throw new ResourceNotFoundException("CartItems not found for the provided IDs.");
		}

		Order order = new Order();
		order.setUser(user);
		order.setShippingAddress(request.getShippingAddress());
		order.setBillingAddress(request.getBillingAddress());
		order.setStatus(OrderStatus.PENDING);

		for (CartItem item : cartItems) {
			item.setOrder(order);
		}

		order.setCartItems(cartItems);

		Order savedOrder = orderRepository.save(order);

		return mapToDto(savedOrder);
	}

	@Override
	public void cancelOrder(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found for id: " + orderId));
		order.setStatus(OrderStatus.CANCELLED);
		orderRepository.save(order);
	}

	@Override
	public List<OrderDto> getOrdersByUserId(Long userId) {
		return orderRepository.findByUserId(userId).stream().map(this::mapToDto).collect(Collectors.toList());
	}

	@Override
	public OrderDto getOrderById(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found for id: " + orderId));

		order.getCartItems().size();

		return mapToDto(order);
	}

	private OrderDto mapToDto(Order order) {
		List<CartItemDto> cartItemDtos = (order.getCartItems() != null)
				? order.getCartItems().stream().map(item -> new CartItemDto(item.getId(), item.getProduct().getName(),
						item.getQuantity(), item.getProduct().getPrice())).collect(Collectors.toList())
				: new ArrayList<>();

		return new OrderDto(order.getId(), order.getUser().getId(), cartItemDtos, order.getShippingAddress(),
				order.getBillingAddress(), order.getStatus());
	}

	@Override
	public OrderDto updateOrderStatus(Long orderId, OrderStatus newStatus) {

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));

		order.setStatus(newStatus);

		Order updatedOrder = orderRepository.save(order);

		return mapToDto(updatedOrder);
	}
}
