package com.plasencia.msproducto.mapper;

import com.plasencia.msproducto.dto.CategoriaRequestDTO;
import com.plasencia.msproducto.dto.CategoriaResponseDTO;
import com.plasencia.msproducto.entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public Categoria toEntity(CategoriaRequestDTO dto) {
        if (dto == null) return null;
        return Categoria.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .activo(dto.getActivo() == null || dto.getActivo())
                .build();
    }

    public CategoriaResponseDTO toResponse(Categoria entity) {
        if (entity == null) return null;
        CategoriaResponseDTO response = CategoriaResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .activo(entity.getActivo())
                .build();
        
        response.setTotalProductos(entity.getProductos() != null ? entity.getProductos().size() : 0);
        
        return response;
    }

    public void updateEntity(Categoria entity, CategoriaRequestDTO dto) {
        if (dto == null || entity == null) return;
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        if (dto.getActivo() != null) {
            entity.setActivo(dto.getActivo());
        }
    }
}
