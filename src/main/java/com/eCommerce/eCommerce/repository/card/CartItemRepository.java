package com.eCommerce.eCommerce.repository.card;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.eCommerce.model.Cart;
import com.eCommerce.eCommerce.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	List<CartItem> findByCart(Cart cart);

	List<CartItem> findAllById(Iterable<Long> cartItemIds);

}
