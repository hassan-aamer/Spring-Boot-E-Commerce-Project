package com.eCommerce.eCommerce.request.card;

import java.util.ArrayList;
import java.util.List;

public class CreateCartRequest {

	private Long userId;
	private List<Long> productIds;
	private List<Integer> quantities;

	public CreateCartRequest() {
		this.productIds = new ArrayList<>();
		this.quantities = new ArrayList<>();
	}

	public CreateCartRequest(Long userId, List<Long> productIds, List<Integer> quantities) {
		this.userId = userId;
		this.productIds = productIds != null ? productIds : new ArrayList<>();
		this.quantities = quantities != null ? quantities : new ArrayList<>();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Long> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}

	public List<Integer> getQuantities() {
		return quantities;
	}

	public void setQuantities(List<Integer> quantities) {
		this.quantities = quantities;
	}

}
