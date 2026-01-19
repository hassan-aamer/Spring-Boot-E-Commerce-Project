package com.eCommerce.eCommerce.dto;

import java.util.List;

import com.eCommerce.eCommerce.model.OrderStatus;

public class OrderDto {

	private Long id;

	private Long userId;

	private List<CartItemDto> cartItems;

	private String shippingAddress;

	private String billingAddress;

	private OrderStatus status;

	public OrderDto() {
	}

	public OrderDto(Long id, Long userId, List<CartItemDto> cartItems, String shippingAddress, String billingAddress,
			OrderStatus status) {
		this.id = id;
		this.userId = userId;
		this.cartItems = cartItems;
		this.shippingAddress = shippingAddress;
		this.billingAddress = billingAddress;
		this.status = status;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<CartItemDto> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItemDto> cartItems) {
		this.cartItems = cartItems;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
}
