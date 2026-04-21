package com.plasencia.msventas.exception;

public class ProductoNoDisponibleException extends RuntimeException {
    public ProductoNoDisponibleException(String message) {
        super(message);
    }
}
