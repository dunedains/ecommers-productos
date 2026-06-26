package com.ecommers.productos.service.impl;

import com.ecommers.productos.dto.ProductDto.ProductRequest;
import com.ecommers.productos.dto.ProductDto.ProductResponse;
import com.ecommers.productos.exception.ProductNotFoundException;
import com.ecommers.productos.model.Product;
import com.ecommers.productos.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias de la lógica del catálogo de productos.
 * Se mockea el repositorio para aislar el servicio de la base de datos.
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceImpl service;

    @Test
    @DisplayName("createProduct: persiste el producto y devuelve la respuesta con su id")
    void createProduct_persisteYDevuelveResponse() {
        // Given
        when(repository.save(any(Product.class))).thenAnswer(inv -> {
            Product p = inv.getArgument(0);
            p.setId(1L);
            return p;
        });

        // When
        ProductResponse response = service.createProduct(
                new ProductRequest("Teclado", "Mecánico", new BigDecimal("19.99")));

        // Then
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Teclado");
        assertThat(response.price()).isEqualByComparingTo("19.99");
        verify(repository).save(any(Product.class));
    }

    @Test
    @DisplayName("getProductById: si no existe, lanza ProductNotFoundException")
    void getProductById_inexistente_lanzaExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getProductById(99L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("updateProduct: actualiza los campos de un producto existente")
    void updateProduct_existente_actualiza() {
        // Given
        Product existing = new Product();
        existing.setId(1L);
        existing.setName("Viejo");
        existing.setPrice(new BigDecimal("10.00"));
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));

        // When
        ProductResponse response = service.updateProduct(1L,
                new ProductRequest("Nuevo", "desc", new BigDecimal("25.50")));

        // Then
        assertThat(response.name()).isEqualTo("Nuevo");
        assertThat(response.price()).isEqualByComparingTo("25.50");
    }

    @Test
    @DisplayName("deleteProduct: si no existe, lanza excepción y no borra")
    void deleteProduct_inexistente_lanzaExcepcion() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.deleteProduct(99L))
                .isInstanceOf(ProductNotFoundException.class);
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("getAllProducts: mapea la página de entidades a respuestas")
    void getAllProducts_devuelveTodos() {
        Product p = new Product();
        p.setId(1L);
        p.setName("Mouse");
        p.setPrice(new BigDecimal("9.99"));
        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(p)));

        Page<ProductResponse> all = service.getAllProducts(PageRequest.of(0, 10));

        assertThat(all.getContent()).hasSize(1);
        assertThat(all.getContent().get(0).name()).isEqualTo("Mouse");
    }
}
