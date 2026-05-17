package com.ecommers.productos.service.impl;

import com.ecommers.productos.Dto.ProductoDto;
import com.ecommers.productos.exception.ProductoNotFoundException;
import com.ecommers.productos.model.Producto;
import com.ecommers.productos.repository.ProductoRepository;
import com.ecommers.productos.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public ProductoDto.ProductoResponse getProductoById(Long id) {
        return toResponse(productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id)));
    }

    @Override
    public List<ProductoDto.ProductoResponse> getAllProductos() {
        return productoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public ProductoDto.ProductoResponse createProducto(ProductoDto.ProductoRequest request) {
        Producto entity = new Producto();
        entity.setNombre(request.nombre());
        entity.setDescripcion(request.descripcion());
        entity.setPrecio(request.precio());
        return toResponse(productoRepository.save(entity));
    }

    @Override
    @Transactional
    public ProductoDto.ProductoResponse updateProducto(Long id, ProductoDto.ProductoRequest request) {
        Producto entity = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
        entity.setNombre(request.nombre());
        entity.setDescripcion(request.descripcion());
        entity.setPrecio(request.precio());
        return toResponse(productoRepository.save(entity));
    }

    @Override
    @Transactional
    public void deleteProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductoNotFoundException(id);
        }
        productoRepository.deleteById(id);
    }

    private ProductoDto.ProductoResponse toResponse(Producto entity) {
        return new ProductoDto.ProductoResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getPrecio());
    }
}