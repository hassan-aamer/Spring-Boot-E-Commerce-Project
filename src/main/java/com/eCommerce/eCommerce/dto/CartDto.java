package com.eCommerce.eCommerce.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartDto {

	private Long id;

	private Long userId;

	private List<CartItemDto> cartItems;

	private BigDecimal totalPrice;

	public CartDto(Long id, Long userId, List<CartItemDto> cartItems, BigDecimal totalPrice) {
		this.id = id;
		this.userId = userId;
		this.cartItems = cartItems;
		this.totalPrice = totalPrice;
	}

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

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

}
