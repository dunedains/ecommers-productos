package com.ecommers.productos.service;

import com.ecommers.productos.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductDto.ProductResponse getProductById(Long id);
    Page<ProductDto.ProductResponse> getAllProducts(Pageable pageable);
    ProductDto.ProductResponse createProduct(ProductDto.ProductRequest request);
    ProductDto.ProductResponse updateProduct(Long id, ProductDto.ProductRequest request);
    void deleteProduct(Long id);
}
