package com.ecommers.productos.service;

import com.ecommers.productos.Dto.ProductoDto;
import java.util.List;

public interface ProductoService {
    ProductoDto.ProductoResponse getProductoById(Long id);
    List<ProductoDto.ProductoResponse> getAllProductos();
    ProductoDto.ProductoResponse createProducto(ProductoDto.ProductoRequest request);
    ProductoDto.ProductoResponse updateProducto(Long id, ProductoDto.ProductoRequest request);
    void deleteProducto(Long id);
}