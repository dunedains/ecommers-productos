package com.ecommers.productos.controller;

import com.ecommers.productos.dto.ProductDto;
import com.ecommers.productos.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Catálogo de productos del e-commerce")
public class ProductController {

    private final ProductService service;

    @GetMapping
    @Operation(summary = "Listar productos",
            description = "Devuelve el catálogo paginado. Acepta parámetros page, size y sort.")
    @ApiResponse(responseCode = "200", description = "Página de productos")
    public ResponseEntity<Page<ProductDto.ProductResponse>> getAllProducts(
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.getAllProducts(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado",
                    content = @Content(examples = @ExampleObject(
                            value = "{\"id\":1,\"name\":\"Teclado\",\"description\":\"Mecánico\",\"price\":19.99}"))),
            @ApiResponse(responseCode = "404", description = "El producto no existe",
                    content = @Content(examples = @ExampleObject(
                            value = "{\"status\":404,\"message\":\"Producto no encontrado: 99\",\"timestamp\":\"...\"}")))
    })
    public ResponseEntity<ProductDto.ProductResponse> getProductById(
            @Parameter(description = "Id del producto") @PathVariable Long id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @PostMapping
    @Operation(summary = "Crear un producto",
            description = "Solo usuarios con rol ADMIN (validado en el API Gateway).")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos (nombre vacío, precio negativo, etc.)")
    })
    public ResponseEntity<ProductDto.ProductResponse> createProduct(@Valid @RequestBody ProductDto.ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createProduct(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "El producto no existe")
    })
    public ResponseEntity<ProductDto.ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto.ProductRequest request) {
        return ResponseEntity.ok(service.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado"),
            @ApiResponse(responseCode = "404", description = "El producto no existe")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
