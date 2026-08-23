package com.eCommerce.eCommerce.service.card;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eCommerce.eCommerce.dto.CartDto;
import com.eCommerce.eCommerce.dto.CartItemDto;
import com.eCommerce.eCommerce.model.Cart;
import com.eCommerce.eCommerce.model.CartItem;
import com.eCommerce.eCommerce.model.Product;
import com.eCommerce.eCommerce.model.User;
import com.eCommerce.eCommerce.repository.card.CartItemRepository;
import com.eCommerce.eCommerce.repository.card.CartRepository;
import com.eCommerce.eCommerce.repository.product.ProductRepository;
import com.eCommerce.eCommerce.repository.user.UserRepository;
import com.eCommerce.eCommerce.request.card.CreateCartRequest;
import com.eCommerce.eCommerce.request.card.UpdateCartRequest;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public CartDto createCart(CreateCartRequest request) {
		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));

		List<CartItem> cartItems = new ArrayList<>();
		BigDecimal totalPrice = BigDecimal.ZERO;

		Cart cart = new Cart();
		cart.setUser(user);

		for (int i = 0; i < request.getProductIds().size(); i++) {
			Long productId = request.getProductIds().get(i);
			Product product = productRepository.findById(productId)
					.orElseThrow(() -> new RuntimeException("Product not found"));

			int quantity = request.getQuantities().get(i);

			if (quantity <= 0) {
				throw new IllegalArgumentException(
						"Quantity must be greater than 0. Invalid quantity for product ID: " + productId);
			}

			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setQuantity(quantity);
			cartItem.setCart(cart);

			BigDecimal productPrice = product.getPrice();
			totalPrice = totalPrice.add(productPrice.multiply(BigDecimal.valueOf(quantity)));

			cartItems.add(cartItem);
		}

		cart.setCartItems(cartItems);
		cart.setTotalPrice(totalPrice.doubleValue());

		Cart savedCart = cartRepository.save(cart);

		List<CartItemDto> cartItemDtos = new ArrayList<>();
		for (CartItem cartItem : savedCart.getCartItems()) {
			cartItemDtos.add(new CartItemDto(cartItem.getProduct().getId(), cartItem.getProduct().getName(),
					cartItem.getQuantity(), cartItem.getProduct().getPrice()));
		}

		return new CartDto(savedCart.getId(), savedCart.getUser().getId(), cartItemDtos, totalPrice);
	}

	@Override
	public CartDto updateCart(UpdateCartRequest request) {
		Cart cart = cartRepository.findById(request.getCartId())
				.orElseThrow(() -> new RuntimeException("Cart not found with id: " + request.getCartId()));

		List<Long> cartItemIdsToRemove = request.getCartItemIds();

		if (cartItemIdsToRemove != null && !cartItemIdsToRemove.isEmpty()) {
			List<CartItem> itemsToRemove = new ArrayList<>();
			for (Long cartItemId : cartItemIdsToRemove) {
				CartItem cartItem = cartItemRepository.findById(cartItemId)
						.orElseThrow(() -> new RuntimeException("CartItem not found with id: " + cartItemId));

				if (!cartItem.getCart().getId().equals(cart.getId())) {
					throw new RuntimeException("CartItem does not belong to the Cart");
				}

				itemsToRemove.add(cartItem);
			}
			cartItemRepository.deleteAll(itemsToRemove);
			cart.getCartItems().removeAll(itemsToRemove);
		}

		BigDecimal totalPrice = BigDecimal.ZERO;
		for (CartItem item : cart.getCartItems()) {
			totalPrice = totalPrice.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
		}
		cart.setTotalPrice(totalPrice.doubleValue());

		Cart savedCart = cartRepository.save(cart);

		List<CartItemDto> cartItemDtos = new ArrayList<>();
		for (CartItem cartItem : savedCart.getCartItems()) {
			cartItemDtos.add(new CartItemDto(cartItem.getProduct().getId(), cartItem.getProduct().getName(),
					cartItem.getQuantity(), cartItem.getProduct().getPrice()));
		}

		return new CartDto(savedCart.getId(), savedCart.getUser().getId(), cartItemDtos, totalPrice);
	}

	@Override
	public void deleteCart(Long cartId) {
		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));

		cartItemRepository.deleteAll(cart.getCartItems());

		cartRepository.delete(cart);
	}

	@Override
	public CartDto getCartByUserId(Long userId) {
		Cart cart = cartRepository.findByUserId(userId);
		if (cart == null) {
			throw new RuntimeException("Cart not found for userId: " + userId);
		}

		List<CartItemDto> cartItemDtos = new ArrayList<>();
		for (CartItem cartItem : cart.getCartItems()) {
			cartItemDtos.add(new CartItemDto(cartItem.getProduct().getId(), cartItem.getProduct().getName(),
					cartItem.getQuantity(), cartItem.getProduct().getPrice()));
		}

		return new CartDto(cart.getId(), cart.getUser().getId(), cartItemDtos,
				BigDecimal.valueOf(cart.getTotalPrice()));
	}
}
