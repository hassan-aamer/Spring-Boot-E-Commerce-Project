package com.eCommerce.eCommerce.repository.card;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.eCommerce.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Cart findByUserId(Long userId);

}
