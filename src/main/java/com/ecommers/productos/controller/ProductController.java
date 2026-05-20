package com.ecommers.productos.controller;

import com.ecommers.productos.dto.ProductDto;
import com.ecommers.productos.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductDto.ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto.ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto.ProductResponse> createProduct(@Valid @RequestBody ProductDto.ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createProduct(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto.ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto.ProductRequest request) {
        return ResponseEntity.ok(service.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
