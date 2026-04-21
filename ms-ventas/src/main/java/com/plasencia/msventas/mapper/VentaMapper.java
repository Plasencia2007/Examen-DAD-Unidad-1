package com.plasencia.msventas.mapper;

import com.plasencia.msventas.client.dto.ClienteExternoDTO;
import com.plasencia.msventas.dto.DetalleRequestDTO;
import com.plasencia.msventas.dto.DetalleResponseDTO;
import com.plasencia.msventas.dto.VentaRequestDTO;
import com.plasencia.msventas.dto.VentaResponseDTO;
import com.plasencia.msventas.entity.Detalle;
import com.plasencia.msventas.entity.EstadoVenta;
import com.plasencia.msventas.entity.Venta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class VentaMapper {

    public Venta toEntity(VentaRequestDTO dto) {
        return Venta.builder()
                .clienteId(dto.getClienteId())
                .fecha(dto.getFecha())
                .estado(EstadoVenta.PENDIENTE)
                .total(BigDecimal.ZERO)
                .build();
    }

    public Detalle toDetalle(DetalleRequestDTO dto, BigDecimal precioUnitario) {
        BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(dto.getCantidad()));
        return Detalle.builder()
                .productoId(dto.getProductoId())
                .cantidad(dto.getCantidad())
                .precioUnitario(precioUnitario)
                .subtotal(subtotal)
                .build();
    }

    public VentaResponseDTO toResponse(Venta entity, ClienteExternoDTO cliente, Map<Long, String> nombresProductos) {
        List<DetalleResponseDTO> detallesDTO = entity.getDetalles().stream()
                .map(d -> toDetalleResponse(d, nombresProductos.getOrDefault(d.getProductoId(), "PRODUCTO NO DISPONIBLE")))
                .collect(Collectors.toList());

        return VentaResponseDTO.builder()
                .id(entity.getId())
                .fecha(entity.getFecha())
                .estado(entity.getEstado())
                .total(entity.getTotal())
                .clienteId(entity.getClienteId())
                .clienteNombreCompleto(cliente.getNombre() + " " + cliente.getApellido())
                .clienteEmail(cliente.getEmail())
                .detalles(detallesDTO)
                .build();
    }

    public DetalleResponseDTO toDetalleResponse(Detalle detalle, String nombreProducto) {
        return DetalleResponseDTO.builder()
                .id(detalle.getId())
                .productoId(detalle.getProductoId())
                .productoNombre(nombreProducto)
                .cantidad(detalle.getCantidad())
                .precioUnitario(detalle.getPrecioUnitario())
                .subtotal(detalle.getSubtotal())
                .build();
    }
}
