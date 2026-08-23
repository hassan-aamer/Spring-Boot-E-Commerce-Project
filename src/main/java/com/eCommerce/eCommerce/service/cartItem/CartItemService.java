package com.eCommerce.eCommerce.service.cardItem;

import java.util.List;

import com.eCommerce.eCommerce.dto.CartItemDto;
import com.eCommerce.eCommerce.request.cardItem.AddCartItemRequest;
import com.eCommerce.eCommerce.request.cardItem.UpdateCartItemRequest;

public interface CartItemService {

	CartItemDto createCartItem(AddCartItemRequest request);

	CartItemDto updateCartItem(UpdateCartItemRequest request);

	void deleteCartItem(Long cartItemId);

	CartItemDto getCartItemById(Long cartItemId);

	List<CartItemDto> getCartItemsByCartId(Long cartId);

}
