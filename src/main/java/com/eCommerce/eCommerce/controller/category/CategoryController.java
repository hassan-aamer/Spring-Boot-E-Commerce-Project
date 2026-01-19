package com.eCommerce.eCommerce.controller.category;

import java.util.ArrayList;
import java.util.List;

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

import com.eCommerce.eCommerce.dto.CategoryDto;
import com.eCommerce.eCommerce.exceptions.ResourceNotFoundException;
import com.eCommerce.eCommerce.model.Category;
import com.eCommerce.eCommerce.request.category.CreateCategoryRequest;
import com.eCommerce.eCommerce.request.category.UpdateCategoryRequest;
import com.eCommerce.eCommerce.response.ApiResponse;
import com.eCommerce.eCommerce.service.category.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
		try {
			Category category = categoryService.getCategoryById(id);
			CategoryDto categoryDto = categoryService.convertToDto(category);
			return ResponseEntity.ok(new ApiResponse("Category fetched successfully", categoryDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping
	public ResponseEntity<ApiResponse> getAllCategories() {
		List<Category> categories = categoryService.getAllCategories();
		List<CategoryDto> categoryDtos = new ArrayList<>();
		for (Category category : categories) {
			categoryDtos.add(categoryService.convertToDto(category));
		}
		return ResponseEntity.ok(new ApiResponse("Categories fetched successfully", categoryDtos));
	}

	@PostMapping
	public ResponseEntity<ApiResponse> createCategory(@RequestBody CreateCategoryRequest request) {
		try {
			Category category = categoryService.createCategory(request);
			CategoryDto categoryDto = categoryService.convertToDto(category);
			return ResponseEntity.ok(new ApiResponse("Category created successfully", categoryDto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id,
			@RequestBody UpdateCategoryRequest request) {
		try {
			Category category = categoryService.updateCategory(id, request);
			CategoryDto categoryDto = categoryService.convertToDto(category);
			return ResponseEntity.ok(new ApiResponse("Category updated successfully", categoryDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
		try {
			categoryService.deleteCategory(id);
			return ResponseEntity.ok(new ApiResponse("Category deleted successfully", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

}
