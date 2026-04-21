package com.plasencia.msventas.client.fallback;

import com.plasencia.msventas.client.ClienteClient;
import com.plasencia.msventas.client.dto.ClienteExternoDTO;
import org.springframework.stereotype.Component;

@Component
public class ClienteClientFallback implements ClienteClient {

    @Override
    public ClienteExternoDTO obtenerPorId(Long id) {
        return ClienteExternoDTO.builder()
                .id(id)
                .nombre("CLIENTE")
                .apellido("NO DISPONIBLE")
                .email("n/a")
                .build();
    }
}
