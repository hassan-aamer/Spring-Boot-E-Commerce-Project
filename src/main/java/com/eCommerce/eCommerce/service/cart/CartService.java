package com.eCommerce.eCommerce.service.cart;

import com.eCommerce.eCommerce.dto.CartDto;
import com.eCommerce.eCommerce.request.cart.CreateCartRequest;
import com.eCommerce.eCommerce.request.cart.UpdateCartRequest;

public interface CartService {

	CartDto createCart(CreateCartRequest request);

	CartDto updateCart(UpdateCartRequest request);

	void deleteCart(Long cartId);

	CartDto getCartByUserId(Long userId);

}
