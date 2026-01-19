package com.eCommerce.eCommerce.service.cardItem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.eCommerce.dto.CartItemDto;
import com.eCommerce.eCommerce.model.Cart;
import com.eCommerce.eCommerce.model.CartItem;
import com.eCommerce.eCommerce.model.Product;
import com.eCommerce.eCommerce.repository.card.CartItemRepository;
import com.eCommerce.eCommerce.repository.card.CartRepository;
import com.eCommerce.eCommerce.repository.product.ProductRepository;
import com.eCommerce.eCommerce.request.cardItem.AddCartItemRequest;
import com.eCommerce.eCommerce.request.cardItem.UpdateCartItemRequest;

@Service
public class CartItemServiceImpl implements CartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartRepository cartRepository;

	@Override
	public CartItemDto createCartItem(AddCartItemRequest request) {
		Product product = productRepository.findById(request.getProductId())
				.orElseThrow(() -> new RuntimeException("Product not found"));

		Cart cart = cartRepository.findById(request.getCartId())
				.orElseThrow(() -> new RuntimeException("Cart not found"));

		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantity(request.getQuantity());
		cartItem.setCart(cart);

		CartItem savedCartItem = cartItemRepository.save(cartItem);

		return new CartItemDto(savedCartItem.getId(), savedCartItem.getProduct().getName(), savedCartItem.getQuantity(),
				savedCartItem.getProduct().getPrice());
	}

	@Override
	public CartItemDto updateCartItem(UpdateCartItemRequest request) {
		CartItem cartItem = cartItemRepository.findById(request.getCartItemId())
				.orElseThrow(() -> new RuntimeException("CartItem not found"));

		cartItem.setQuantity(request.getQuantity());

		CartItem updatedCartItem = cartItemRepository.save(cartItem);

		return new CartItemDto(updatedCartItem.getId(), updatedCartItem.getProduct().getName(),
				updatedCartItem.getQuantity(), updatedCartItem.getProduct().getPrice());
	}

	@Override
	public void deleteCartItem(Long cartItemId) {
		CartItem cartItem = cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new RuntimeException("CartItem not found"));

		cartItemRepository.delete(cartItem);
	}

	@Override
	public CartItemDto getCartItemById(Long cartItemId) {
		CartItem cartItem = cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new RuntimeException("CartItem not found"));

		return new CartItemDto(cartItem.getId(), cartItem.getProduct().getName(), cartItem.getQuantity(),
				cartItem.getProduct().getPrice());
	}

	@Override
	public List<CartItemDto> getCartItemsByCartId(Long cartId) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

		List<CartItem> cartItems = cartItemRepository.findByCart(cart);

		List<CartItemDto> cartItemDtos = new ArrayList<>();
		for (CartItem cartItem : cartItems) {
			cartItemDtos.add(new CartItemDto(cartItem.getId(), cartItem.getProduct().getName(), cartItem.getQuantity(),
					cartItem.getProduct().getPrice()));
		}

		return cartItemDtos;
	}
}
