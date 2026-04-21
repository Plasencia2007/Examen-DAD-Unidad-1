package com.plasencia.msventas.client.fallback;

import com.plasencia.msventas.client.ProductoClient;
import com.plasencia.msventas.client.dto.ProductoExternoDTO;
import com.plasencia.msventas.exception.ProductoNoDisponibleException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductoClientFallback implements ProductoClient {

    @Override
    public ProductoExternoDTO obtenerPorId(Long id) {
        return ProductoExternoDTO.builder()
                .id(id)
                .nombre("PRODUCTO NO DISPONIBLE")
                .precio(BigDecimal.ZERO)
                .stock(0)
                .activo(false)
                .build();
    }

    @Override
    public ProductoExternoDTO ajustarStock(Long id, int delta) {
        throw new ProductoNoDisponibleException(
                "No se pudo ajustar el stock del producto " + id + ". ms-producto no está disponible.");
    }
}
