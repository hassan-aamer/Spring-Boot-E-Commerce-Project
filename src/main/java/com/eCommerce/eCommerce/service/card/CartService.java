package com.eCommerce.eCommerce.service.card;

import com.eCommerce.eCommerce.dto.CartDto;
import com.eCommerce.eCommerce.request.card.CreateCartRequest;
import com.eCommerce.eCommerce.request.card.UpdateCartRequest;

public interface CartService {

	CartDto createCart(CreateCartRequest request);

	CartDto updateCart(UpdateCartRequest request);

	void deleteCart(Long cartId);

	CartDto getCartByUserId(Long userId);

}
