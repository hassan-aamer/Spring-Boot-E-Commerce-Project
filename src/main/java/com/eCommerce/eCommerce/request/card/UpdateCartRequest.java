package com.eCommerce.eCommerce.request.card;

import java.util.List;

public class UpdateCartRequest {

	private Long cartId;

	private List<Long> cartItemIds;

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public List<Long> getCartItemIds() {
		return cartItemIds;
	}

	public void setCartItemIds(List<Long> cartItemIds) {
		this.cartItemIds = cartItemIds;
	}
}
