package com.eCommerce.eCommerce.controller.cardItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.eCommerce.dto.CartItemDto;
import com.eCommerce.eCommerce.request.cardItem.AddCartItemRequest;
import com.eCommerce.eCommerce.request.cardItem.UpdateCartItemRequest;
import com.eCommerce.eCommerce.service.cardItem.CartItemService;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

	@Autowired
	private CartItemService cartItemService;

	@PostMapping()
	public ResponseEntity<CartItemDto> createCartItem(@RequestBody AddCartItemRequest request) {
		System.out.println("Received request: " + request);

		if (request.getProductId() == null || request.getCartId() == null || request.getQuantity() <= 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		CartItemDto cartItemDto = cartItemService.createCartItem(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
	}

	@PutMapping
	public ResponseEntity<CartItemDto> updateCartItem(@RequestBody UpdateCartItemRequest request) {
		CartItemDto response = cartItemService.updateCartItem(request);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCartItem(@PathVariable Long id) {
		cartItemService.deleteCartItem(id);
		return ResponseEntity.ok("CartItem deleted successfully");
	}

	@GetMapping("/{id}")
	public ResponseEntity<CartItemDto> getCartItemById(@PathVariable Long id) {
		CartItemDto response = cartItemService.getCartItemById(id);
		return ResponseEntity.ok(response);
	}

}
