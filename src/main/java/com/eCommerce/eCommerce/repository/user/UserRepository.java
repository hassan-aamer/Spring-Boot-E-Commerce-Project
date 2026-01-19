package com.eCommerce.eCommerce.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.eCommerce.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);
}
