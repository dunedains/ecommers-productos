package com.ecommers.productos.service.impl;

import com.ecommers.productos.dto.ProductDto;
import com.ecommers.productos.exception.ProductNotFoundException;
import com.ecommers.productos.model.Product;
import com.ecommers.productos.repository.ProductRepository;
import com.ecommers.productos.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    @Transactional(readOnly = true)
    public ProductDto.ProductResponse getProductById(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto.ProductResponse> getAllProducts() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public ProductDto.ProductResponse createProduct(ProductDto.ProductRequest request) {
        Product entity = new Product();
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setPrice(request.price());
        return toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public ProductDto.ProductResponse updateProduct(Long id, ProductDto.ProductRequest request) {
        Product entity = findOrThrow(id);
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setPrice(request.price());
        return toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!repository.existsById(id)) throw new ProductNotFoundException(id);
        repository.deleteById(id);
    }

    private Product findOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    private ProductDto.ProductResponse toResponse(Product p) {
        return new ProductDto.ProductResponse(p.getId(), p.getName(), p.getDescription(), p.getPrice());
    }
}
