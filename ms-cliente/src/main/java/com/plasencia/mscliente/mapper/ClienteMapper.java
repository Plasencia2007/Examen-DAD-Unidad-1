package com.plasencia.mscliente.mapper;

import com.plasencia.mscliente.dto.ClienteRequestDTO;
import com.plasencia.mscliente.dto.ClienteResponseDTO;
import com.plasencia.mscliente.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequestDTO dto) {
        return Cliente.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .build();
    }

    public ClienteResponseDTO toResponse(Cliente entity) {
        return ClienteResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .email(entity.getEmail())
                .telefono(entity.getTelefono())
                .direccion(entity.getDireccion())
                .build();
    }

    public void updateEntity(Cliente entity, ClienteRequestDTO dto) {
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setEmail(dto.getEmail());
        entity.setTelefono(dto.getTelefono());
        entity.setDireccion(dto.getDireccion());
    }
}
