package com.ecommers.productos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class ProductDto {

    public record ProductRequest(
            @NotBlank(message = "El nombre es obligatorio") String name,
            String description,
            @NotNull(message = "El precio es obligatorio") @Positive(message = "El precio debe ser mayor a 0") BigDecimal price
    ) {}

    public record ProductResponse(
            Long id,
            String name,
            String description,
            BigDecimal price
    ) {}
}
