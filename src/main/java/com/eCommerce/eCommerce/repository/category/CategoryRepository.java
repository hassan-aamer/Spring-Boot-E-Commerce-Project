package com.eCommerce.eCommerce.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.eCommerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	boolean existsByName(String name);
}
