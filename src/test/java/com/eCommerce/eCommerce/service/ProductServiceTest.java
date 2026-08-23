package com.eCommerce.eCommerce.service;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.eCommerce.eCommerce.model.Category;
import com.eCommerce.eCommerce.model.Product;
import com.eCommerce.eCommerce.repository.category.CategoryRepository;
import com.eCommerce.eCommerce.repository.product.ProductRepository;
import com.eCommerce.eCommerce.request.product.CreateProductRequest;
import com.eCommerce.eCommerce.service.product.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getProductById_Success() {
        Product product = new Product("Laptop", "BrandX", BigDecimal.valueOf(1500.0), 10, "Gaming Laptop", null);
        product.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        assertEquals(BigDecimal.valueOf(1500.0), result.getPrice());
    }

    @Test
    void createProduct_Success() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        CreateProductRequest request = new CreateProductRequest();
        request.setCategoryId(1L);
        request.setName("Phone");
        request.setBrand("BrandY");
        request.setDescription("Smartphone");
        request.setPrice(BigDecimal.valueOf(800.0));
        request.setInventory(20);

        Product savedProduct = new Product("Phone", "BrandY", BigDecimal.valueOf(800.0), 20, "Smartphone", category);
        savedProduct.setId(2L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        Product result = productService.createProduct(request);

        assertNotNull(result);
        assertEquals("Phone", result.getName());
        verify(productRepository).save(any(Product.class));
    }
}

