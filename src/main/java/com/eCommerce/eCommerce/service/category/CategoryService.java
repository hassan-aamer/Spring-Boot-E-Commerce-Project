package com.eCommerce.eCommerce.service.category;

import java.util.List;

import com.eCommerce.eCommerce.dto.CategoryDto;
import com.eCommerce.eCommerce.model.Category;
import com.eCommerce.eCommerce.request.category.CreateCategoryRequest;
import com.eCommerce.eCommerce.request.category.UpdateCategoryRequest;

public interface CategoryService {

	Category getCategoryById(Long id);

	List<Category> getAllCategories();

	Category createCategory(CreateCategoryRequest request);

	Category updateCategory(Long id, UpdateCategoryRequest request);

	void deleteCategory(Long id);

	CategoryDto convertToDto(Category category);
}
