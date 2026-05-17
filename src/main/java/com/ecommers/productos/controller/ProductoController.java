package com.ecommers.productos.controller;

import com.ecommers.productos.Dto.ProductoDto;
import com.ecommers.productos.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService service;

    @GetMapping
    public ResponseEntity<List<ProductoDto.ProductoResponse>> getAllProductos() {
        return ResponseEntity.ok(service.getAllProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto.ProductoResponse> getProductoById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProductoById(id));
    }

    @PostMapping
    public ResponseEntity<ProductoDto.ProductoResponse> createProducto(@Valid @RequestBody ProductoDto.ProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createProducto(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDto.ProductoResponse> updateProducto(@PathVariable Long id, @Valid @RequestBody ProductoDto.ProductoRequest request) {
        return ResponseEntity.ok(service.updateProducto(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        service.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }
}