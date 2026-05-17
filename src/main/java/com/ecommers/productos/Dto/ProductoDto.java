package com.ecommers.productos.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class ProductoDto {
    public record ProductoRequest(
            @NotBlank String nombre,
            String descripcion,
            @NotNull @Positive BigDecimal precio) {}

    public record ProductoResponse(
            Long id,
            String nombre,
            String descripcion,
            BigDecimal precio) {}

}