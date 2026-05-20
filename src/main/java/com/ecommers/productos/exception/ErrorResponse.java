package com.ecommers.productos.exception;

public record ErrorResponse(int status, String message, String timestamp) {}
