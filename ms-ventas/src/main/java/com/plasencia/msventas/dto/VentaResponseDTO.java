package com.plasencia.msventas.dto;

import com.plasencia.msventas.entity.EstadoVenta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaResponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private EstadoVenta estado;
    private BigDecimal total;
    private Long clienteId;
    private String clienteNombreCompleto;
    private String clienteEmail;
    private List<DetalleResponseDTO> detalles;
}
