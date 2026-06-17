package com.ecommers.productos.controller;

import com.ecommers.productos.dto.ProductDto;
import com.ecommers.productos.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProductDto.ProductResponse>>> getAllProducts() {
        List<EntityModel<ProductDto.ProductResponse>> products = service.getAllProducts().stream()
                .map(this::toModel)
                .toList();
        return ResponseEntity.ok(CollectionModel.of(products,
                linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductDto.ProductResponse>> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(toModel(service.getProductById(id)));
    }

    @PostMapping
    public ResponseEntity<EntityModel<ProductDto.ProductResponse>> createProduct(@Valid @RequestBody ProductDto.ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(service.createProduct(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProductDto.ProductResponse>> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto.ProductRequest request) {
        return ResponseEntity.ok(toModel(service.updateProduct(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<ProductDto.ProductResponse> toModel(ProductDto.ProductResponse product) {
        return EntityModel.of(product,
                linkTo(methodOn(ProductController.class).getProductById(product.id())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("productos"));
    }
}
