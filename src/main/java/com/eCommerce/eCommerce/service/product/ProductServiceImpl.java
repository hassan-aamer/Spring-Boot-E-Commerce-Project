package com.eCommerce.eCommerce.service.product;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.eCommerce.eCommerce.dto.ProductDto;
import com.eCommerce.eCommerce.exceptions.ResourceNotFoundException;
import com.eCommerce.eCommerce.model.Category;
import com.eCommerce.eCommerce.model.Product;
import com.eCommerce.eCommerce.repository.category.CategoryRepository;
import com.eCommerce.eCommerce.repository.product.ProductRepository;
import com.eCommerce.eCommerce.request.product.CreateProductRequest;
import com.eCommerce.eCommerce.request.product.UpdateProductRequest;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	private final CategoryRepository categoryRepository;

	private final ModelMapper modelMapper;

	public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
			ModelMapper modelMapper) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public Product getProductById(Long productId) {
		return productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product createProduct(CreateProductRequest request) {
		Category category = categoryRepository.findById(request.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category not found!"));

		Product product = new Product();
		product.setName(request.getName());
		product.setBrand(request.getBrand());
		product.setPrice(request.getPrice());
		product.setInventory(request.getInventory());
		product.setDescription(request.getDescription());
		product.setCategory(category);

		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Long productId, UpdateProductRequest request) {
		Product product = getProductById(productId);

		Category category = categoryRepository.findById(request.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category not found!"));

		product.setName(request.getName());
		product.setBrand(request.getBrand());
		product.setPrice(request.getPrice());
		product.setInventory(request.getInventory());
		product.setDescription(request.getDescription());
		product.setCategory(category);

		return productRepository.save(product);
	}

	@Override
	public void deleteProduct(Long productId) {
		Product product = getProductById(productId);
		productRepository.delete(product);
	}

	@Override
	public ProductDto convertProductToDto(Product product) {
		ProductDto dto = modelMapper.map(product, ProductDto.class);

		if (product.getCategory() != null) {
			dto.setCategoryName(product.getCategory().getName());
		}

		return dto;
	}
}
