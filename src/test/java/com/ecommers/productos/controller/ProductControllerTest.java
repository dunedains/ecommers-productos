package com.ecommers.productos.controller;

import com.ecommers.productos.dto.ProductDto.ProductResponse;
import com.ecommers.productos.exception.ProductNotFoundException;
import com.ecommers.productos.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas de la capa web (controller) del catálogo.
 * @WebMvcTest carga solo el slice web; el servicio se sustituye por un mock.
 */
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService service;

    @Test
    @DisplayName("GET /api/productos -> 200 con la página de productos")
    void getAll_devuelve200() throws Exception {
        when(service.getAllProducts(any(Pageable.class))).thenReturn(
                new PageImpl<>(List.of(new ProductResponse(1L, "Mouse", "BT", new BigDecimal("9.99")))));

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Mouse"));
    }

    @Test
    @DisplayName("GET /api/productos/{id} existente -> 200")
    void getById_existente_devuelve200() throws Exception {
        when(service.getProductById(1L))
                .thenReturn(new ProductResponse(1L, "Mouse", "BT", new BigDecimal("9.99")));

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /api/productos/{id} inexistente -> 404 (GlobalExceptionHandler)")
    void getById_inexistente_devuelve404() throws Exception {
        when(service.getProductById(99L)).thenThrow(new ProductNotFoundException(99L));

        mockMvc.perform(get("/api/productos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/productos válido -> 201")
    void create_valido_devuelve201() throws Exception {
        when(service.createProduct(any()))
                .thenReturn(new ProductResponse(1L, "Teclado", "Mecánico", new BigDecimal("19.99")));

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Teclado\",\"description\":\"Mecánico\",\"price\":19.99}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("POST /api/productos inválido (nombre vacío) -> 400 (validación)")
    void create_invalido_devuelve400() throws Exception {
        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"description\":\"x\",\"price\":19.99}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("DELETE /api/productos/{id} -> 204")
    void delete_devuelve204() throws Exception {
        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isNoContent());
    }
}
