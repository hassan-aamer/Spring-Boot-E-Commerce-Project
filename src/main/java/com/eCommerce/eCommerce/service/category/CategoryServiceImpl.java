package com.eCommerce.eCommerce.service.category;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.eCommerce.eCommerce.dto.CategoryDto;
import com.eCommerce.eCommerce.exceptions.ResourceNotFoundException;
import com.eCommerce.eCommerce.model.Category;
import com.eCommerce.eCommerce.repository.category.CategoryRepository;
import com.eCommerce.eCommerce.request.category.CreateCategoryRequest;
import com.eCommerce.eCommerce.request.category.UpdateCategoryRequest;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	private final ModelMapper modelMapper;

	public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Category createCategory(CreateCategoryRequest request) {
		if (categoryRepository.existsByName(request.getName())) {
			throw new IllegalArgumentException("Category with this name already exists");
		}
		Category category = modelMapper.map(request, Category.class);
		return categoryRepository.save(category);
	}

	@Override
	public Category updateCategory(Long id, UpdateCategoryRequest request) {
		Category category = getCategoryById(id);
		category.setName(request.getName());
		return categoryRepository.save(category);
	}

	@Override
	public void deleteCategory(Long id) {
		Category category = getCategoryById(id);
		categoryRepository.delete(category);
	}

	@Override
	public CategoryDto convertToDto(Category category) {
		return modelMapper.map(category, CategoryDto.class);
	}
}
