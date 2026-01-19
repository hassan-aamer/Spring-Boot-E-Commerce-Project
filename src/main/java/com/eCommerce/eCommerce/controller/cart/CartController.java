package com.eCommerce.eCommerce.controller.cart;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.eCommerce.dto.CartDto;
import com.eCommerce.eCommerce.dto.CartItemDto;
import com.eCommerce.eCommerce.request.card.CreateCartRequest;
import com.eCommerce.eCommerce.request.card.UpdateCartRequest;
import com.eCommerce.eCommerce.request.cardItem.AddCartItemRequest;
import com.eCommerce.eCommerce.request.cardItem.UpdateCartItemRequest;
import com.eCommerce.eCommerce.service.card.CartService;
import org.springframework.http.HttpStatus;
import com.eCommerce.eCommerce.service.cardItem.CartItemService;

@RestController
@RequestMapping("/api/carts")
public class CartController {

	private final CartService cartService;

	private final CartItemService cartItemService;

	public CartController(CartService cartService, CartItemService cartItemService) {
		this.cartService = cartService;
		this.cartItemService = cartItemService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CartDto createCart(@RequestBody CreateCartRequest request) {
		return cartService.createCart(request);
	}

	@GetMapping("/user/{userId}")
	public CartDto getCartByUserId(@PathVariable Long userId) {
		return cartService.getCartByUserId(userId);
	}

	@PutMapping("/{cartId}")
	public CartDto updateCart(@PathVariable Long cartId, @RequestBody UpdateCartRequest request) {
		request.setCartId(cartId);

		return cartService.updateCart(request);
	}

	@DeleteMapping("/{cartId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCart(@PathVariable Long cartId) {
		cartService.deleteCart(cartId);
	}

	@PostMapping("/{cartId}/items")
	@ResponseStatus(HttpStatus.CREATED)
	public CartItemDto addCartItem(@PathVariable Long cartId, @RequestBody AddCartItemRequest request) {
		request.setCartId(cartId);
		return cartItemService.createCartItem(request);
	}

	@GetMapping("/{cartId}/items")
	public List<CartItemDto> getCartItemsByCartId(@PathVariable Long cartId) {
		return cartItemService.getCartItemsByCartId(cartId);
	}

	@PutMapping("/items/{cartItemId}")
	public CartItemDto updateCartItem(@PathVariable Long cartItemId, @RequestBody UpdateCartItemRequest request) {
		request.setCartItemId(cartItemId);
		return cartItemService.updateCartItem(request);
	}

	@DeleteMapping("/items/{cartItemId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCartItem(@PathVariable Long cartItemId) {
		cartItemService.deleteCartItem(cartItemId);
	}

}
