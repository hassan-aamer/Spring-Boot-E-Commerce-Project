package com.eCommerce.eCommerce.controller.product;

import java.util.List;
import java.util.stream.Collectors;

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

import com.eCommerce.eCommerce.dto.ProductDto;
import com.eCommerce.eCommerce.model.Product;
import com.eCommerce.eCommerce.request.product.CreateProductRequest;
import com.eCommerce.eCommerce.request.product.UpdateProductRequest;
import com.eCommerce.eCommerce.response.ApiResponse;
import com.eCommerce.eCommerce.service.product.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        ProductDto productDto = productService.convertProductToDto(product);
        return ResponseEntity.ok(new ApiResponse("Product fetched successfully", productDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> productDtos = products.stream().map(productService::convertProductToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse("Products fetched successfully", productDtos));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        Product product = productService.createProduct(request);
        ProductDto productDto = productService.convertProductToDto(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Product created successfully", productDto));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long productId,
            @Valid @RequestBody UpdateProductRequest request) {
        Product product = productService.updateProduct(productId, request);
        ProductDto productDto = productService.convertProductToDto(product);
        return ResponseEntity.ok(new ApiResponse("Product updated successfully", productDto));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(new ApiResponse("Product deleted successfully", null));
    }
}

