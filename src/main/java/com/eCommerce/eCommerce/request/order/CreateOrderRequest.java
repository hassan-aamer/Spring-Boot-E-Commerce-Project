package com.eCommerce.eCommerce.request.order;

import java.util.List;

public class CreateOrderRequest {

	private Long userId;

	private List<Long> cartItemIds;

	private String shippingAddress;

	private String billingAddress;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Long> getCartItemIds() {
		return cartItemIds;
	}

	public void setCartItemIds(List<Long> cartItemIds) {
		this.cartItemIds = cartItemIds;
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

}
