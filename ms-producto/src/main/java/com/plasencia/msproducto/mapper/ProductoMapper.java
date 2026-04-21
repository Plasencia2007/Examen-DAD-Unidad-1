package com.plasencia.msproducto.mapper;

import com.plasencia.msproducto.dto.ProductoRequestDTO;
import com.plasencia.msproducto.dto.ProductoResponseDTO;
import com.plasencia.msproducto.entity.Categoria;
import com.plasencia.msproducto.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public Producto toEntity(ProductoRequestDTO dto, Categoria categoria) {
        if (dto == null) return null;
        return Producto.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .precio(dto.getPrecio())
                .stock(dto.getStock())
                .activo(dto.getActivo() == null || dto.getActivo())
                .categoria(categoria)
                .build();
    }

    public ProductoResponseDTO toResponse(Producto entity) {
        if (entity == null) return null;
        return ProductoResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .precio(entity.getPrecio())
                .stock(entity.getStock())
                .activo(entity.getActivo())
                .categoria(ProductoResponseDTO.CategoriaSimplificadaResponseDTO.builder()
                        .id(entity.getCategoria().getId())
                        .nombre(entity.getCategoria().getNombre())
                        .build())
                .build();
    }

    public void updateEntity(Producto entity, ProductoRequestDTO dto, Categoria categoria) {
        if (dto == null || entity == null) return;
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setPrecio(dto.getPrecio());
        entity.setStock(dto.getStock());
        if (dto.getActivo() != null) {
            entity.setActivo(dto.getActivo());
        }
        entity.setCategoria(categoria);
    }
}
