package com.plasencia.msventas.exception;

public class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(String producto, Integer disponible, Integer solicitado) {
        super("Stock insuficiente para el producto " + producto
                + ". Disponible: " + disponible + ", solicitado: " + solicitado + ".");
    }
}
