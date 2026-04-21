package com.plasencia.msventas.service.impl;

import com.plasencia.msventas.client.ProductoClient;
import com.plasencia.msventas.client.dto.ProductoExternoDTO;
import com.plasencia.msventas.dto.DetalleRequestDTO;
import com.plasencia.msventas.dto.DetalleResponseDTO;
import com.plasencia.msventas.entity.Detalle;
import com.plasencia.msventas.entity.Venta;
import com.plasencia.msventas.exception.ResourceNotFoundException;
import com.plasencia.msventas.mapper.VentaMapper;
import com.plasencia.msventas.repository.DetalleRepository;
import com.plasencia.msventas.repository.VentaRepository;
import com.plasencia.msventas.service.DetalleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetalleServiceImpl implements DetalleService {

    private final DetalleRepository detalleRepository;
    private final VentaRepository ventaRepository;
    private final ProductoClient productoClient;
    private final VentaMapper ventaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<DetalleResponseDTO> listarTodos() {
        return detalleRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleResponseDTO> listarPorVenta(Long ventaId) {
        return detalleRepository.findByVentaId(ventaId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DetalleResponseDTO obtenerPorId(Long id) {
        return toResponse(findById(id));
    }

    @Override
    @Transactional
    public DetalleResponseDTO crear(DetalleRequestDTO dto) {
        Venta venta = ventaRepository.findById(dto.getVentaId())
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + dto.getVentaId()));

        ProductoExternoDTO producto = productoClient.obtenerPorId(dto.getProductoId());
        BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(dto.getCantidad()));

        Detalle detalle = Detalle.builder()
                .venta(venta)
                .productoId(dto.getProductoId())
                .cantidad(dto.getCantidad())
                .precioUnitario(producto.getPrecio())
                .subtotal(subtotal)
                .build();

        Detalle saved = detalleRepository.save(detalle);

        // Recalcular total de la venta
        recalcularTotal(venta);

        return toResponse(saved);
    }

    @Override
    @Transactional
    public DetalleResponseDTO actualizar(Long id, DetalleRequestDTO dto) {
        Detalle detalle = findById(id);

        if (dto.getProductoId() != null) {
            detalle.setProductoId(dto.getProductoId());
        }
        if (dto.getCantidad() != null) {
            detalle.setCantidad(dto.getCantidad());
        }

        ProductoExternoDTO producto = productoClient.obtenerPorId(detalle.getProductoId());
        detalle.setPrecioUnitario(producto.getPrecio());
        detalle.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(detalle.getCantidad())));

        Detalle saved = detalleRepository.save(detalle);

        // Recalcular total de la venta
        recalcularTotal(detalle.getVenta());

        return toResponse(saved);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Detalle detalle = findById(id);
        Venta venta = detalle.getVenta();
        detalleRepository.delete(detalle);
        recalcularTotal(venta);
    }

    // ─── helpers ────────────────────────────────────────────────────────────────

    private Detalle findById(Long id) {
        return detalleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle no encontrado con ID: " + id));
    }

    private DetalleResponseDTO toResponse(Detalle detalle) {
        ProductoExternoDTO producto = productoClient.obtenerPorId(detalle.getProductoId());
        return ventaMapper.toDetalleResponse(detalle, producto.getNombre());
    }

    private void recalcularTotal(Venta venta) {
        BigDecimal total = detalleRepository.findByVentaId(venta.getId()).stream()
                .map(Detalle::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        venta.setTotal(total);
        ventaRepository.save(venta);
    }
}
