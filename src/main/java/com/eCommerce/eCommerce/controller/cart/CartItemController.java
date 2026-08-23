package com.eCommerce.eCommerce.controller.cart;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eCommerce.eCommerce.dto.CartItemDto;
import com.eCommerce.eCommerce.request.cartItem.AddCartItemRequest;
import com.eCommerce.eCommerce.request.cartItem.UpdateCartItemRequest;
import com.eCommerce.eCommerce.response.ApiResponse;
import com.eCommerce.eCommerce.service.cartItem.CartItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createCartItem(@Valid @RequestBody AddCartItemRequest request) {
        CartItemDto cartItemDto = cartItemService.createCartItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Cart item added successfully", cartItemDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCartItem(@PathVariable Long id, @Valid @RequestBody UpdateCartItemRequest request) {
        request.setCartItemId(id);
        CartItemDto response = cartItemService.updateCartItem(request);
        return ResponseEntity.ok(new ApiResponse("Cart item updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long id) {
        cartItemService.deleteCartItem(id);
        return ResponseEntity.ok(new ApiResponse("CartItem deleted successfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCartItemById(@PathVariable Long id) {
        CartItemDto response = cartItemService.getCartItemById(id);
        return ResponseEntity.ok(new ApiResponse("Cart item fetched successfully", response));
    }
}

