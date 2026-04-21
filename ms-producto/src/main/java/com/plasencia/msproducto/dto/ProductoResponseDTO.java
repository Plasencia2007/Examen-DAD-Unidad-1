package com.plasencia.msproducto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private Boolean activo;
    private CategoriaSimplificadaResponseDTO categoria;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CategoriaSimplificadaResponseDTO {
        private Long id;
        private String nombre;
    }
}
