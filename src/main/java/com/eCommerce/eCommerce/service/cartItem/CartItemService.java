package com.eCommerce.eCommerce.service.cartItem;

import java.util.List;

import com.eCommerce.eCommerce.dto.CartItemDto;
import com.eCommerce.eCommerce.request.cartItem.AddCartItemRequest;
import com.eCommerce.eCommerce.request.cartItem.UpdateCartItemRequest;

public interface CartItemService {

	CartItemDto createCartItem(AddCartItemRequest request);

	CartItemDto updateCartItem(UpdateCartItemRequest request);

	void deleteCartItem(Long cartItemId);

	CartItemDto getCartItemById(Long cartItemId);

	List<CartItemDto> getCartItemsByCartId(Long cartId);

}
