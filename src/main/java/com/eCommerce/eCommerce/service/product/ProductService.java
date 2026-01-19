package com.eCommerce.eCommerce.service.product;

import java.util.List;

import com.eCommerce.eCommerce.dto.ProductDto;
import com.eCommerce.eCommerce.model.Product;
import com.eCommerce.eCommerce.request.product.CreateProductRequest;
import com.eCommerce.eCommerce.request.product.UpdateProductRequest;

public interface ProductService {

	Product getProductById(Long productId);

	List<Product> getAllProducts();

	Product createProduct(CreateProductRequest request);

	Product updateProduct(Long productId, UpdateProductRequest request);

	void deleteProduct(Long productId);

	ProductDto convertProductToDto(Product product);
}
