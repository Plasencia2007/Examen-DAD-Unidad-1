package com.plasencia.msventas.client;

import com.plasencia.msventas.client.dto.ClienteExternoDTO;
import com.plasencia.msventas.client.fallback.ClienteClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-cliente", fallback = ClienteClientFallback.class)
public interface ClienteClient {

    @GetMapping("/api/clientes/{id}")
    ClienteExternoDTO obtenerPorId(@PathVariable("id") Long id);
}
