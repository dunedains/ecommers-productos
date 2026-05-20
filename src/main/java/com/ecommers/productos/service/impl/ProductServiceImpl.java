package com.ecommers.productos.service.impl;

import lombok.extern.slf4j.Slf4j;
import com.ecommers.productos.dto.ProductDto;
import com.ecommers.productos.exception.ProductNotFoundException;
import com.ecommers.productos.model.Product;
import com.ecommers.productos.repository.ProductRepository;
import com.ecommers.productos.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    @Transactional(readOnly = true)
    public ProductDto.ProductResponse getProductById(Long id) {
        log.info("Buscando producto id={}", id);
        return toResponse(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto.ProductResponse> getAllProducts() {
        log.info("Listando todos los productos");
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public ProductDto.ProductResponse createProduct(ProductDto.ProductRequest request) {
        log.info("Creando producto name={}", request.name());
        Product entity = new Product();
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setPrice(request.price());
        return toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public ProductDto.ProductResponse updateProduct(Long id, ProductDto.ProductRequest request) {
        log.info("Actualizando producto id={}", id);
        Product entity = findOrThrow(id);
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setPrice(request.price());
        return toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        log.info("Eliminando producto id={}", id);
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
