package com.plasencia.msventas.client;

import com.plasencia.msventas.client.dto.ProductoExternoDTO;
import com.plasencia.msventas.client.fallback.ProductoClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-producto", fallback = ProductoClientFallback.class)
public interface ProductoClient {

    @GetMapping("/api/productos/{id}")
    ProductoExternoDTO obtenerPorId(@PathVariable("id") Long id);

    @PatchMapping("/api/productos/{id}/stock")
    ProductoExternoDTO ajustarStock(@PathVariable("id") Long id, @RequestParam("delta") int delta);
}
