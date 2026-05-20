package com.ecommers.productos.service;

import com.ecommers.productos.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto.ProductResponse getProductById(Long id);
    List<ProductDto.ProductResponse> getAllProducts();
    ProductDto.ProductResponse createProduct(ProductDto.ProductRequest request);
    ProductDto.ProductResponse updateProduct(Long id, ProductDto.ProductRequest request);
    void deleteProduct(Long id);
}
