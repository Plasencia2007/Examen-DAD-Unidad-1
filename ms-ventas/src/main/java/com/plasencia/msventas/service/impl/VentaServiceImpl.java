package com.plasencia.msventas.service.impl;

import com.plasencia.msventas.client.ClienteClient;
import com.plasencia.msventas.client.ProductoClient;
import com.plasencia.msventas.client.dto.ClienteExternoDTO;
import com.plasencia.msventas.client.dto.ProductoExternoDTO;
import com.plasencia.msventas.dto.DetalleRequestDTO;
import com.plasencia.msventas.dto.VentaRequestDTO;
import com.plasencia.msventas.dto.VentaResponseDTO;
import com.plasencia.msventas.entity.Detalle;
import com.plasencia.msventas.entity.EstadoVenta;
import com.plasencia.msventas.entity.Venta;
import com.plasencia.msventas.exception.*;
import com.plasencia.msventas.mapper.VentaMapper;
import com.plasencia.msventas.repository.VentaRepository;
import com.plasencia.msventas.service.VentaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteClient clienteClient;
    private final ProductoClient productoClient;
    private final VentaMapper ventaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponseDTO> listarTodas() {
        return ventaRepository.findAll().stream()
                .map(this::toResponseEnriquecido)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponseDTO> listarPorCliente(Long clienteId) {
        return ventaRepository.findByClienteId(clienteId).stream()
                .map(this::toResponseEnriquecido)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponseDTO> listarPorEstado(EstadoVenta estado) {
        return ventaRepository.findByEstado(estado).stream()
                .map(this::toResponseEnriquecido)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public VentaResponseDTO obtenerPorId(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + id));
        return toResponseEnriquecido(venta);
    }

    @Override
    @Transactional(noRollbackFor = VentaInvalidaException.class)
    public VentaResponseDTO crear(VentaRequestDTO dto) {
        // 1. Validar cliente
        ClienteExternoDTO cliente = clienteClient.obtenerPorId(dto.getClienteId());
        if ("NO DISPONIBLE".equals(cliente.getApellido())) {
            throw new ClienteNoDisponibleException(
                    "El cliente con ID " + dto.getClienteId() + " no está disponible.");
        }

        // 2. Construir venta base
        Venta venta = ventaMapper.toEntity(dto);

        // 3. Procesar cada detalle — verificar producto, stock y capturar precio actual
        BigDecimal total = BigDecimal.ZERO;
        for (DetalleRequestDTO detalleReq : dto.getDetalles()) {
            ProductoExternoDTO producto = productoClient.obtenerPorId(detalleReq.getProductoId());

            if (!Boolean.TRUE.equals(producto.getActivo())) {
                throw new ProductoNoDisponibleException(
                        "El producto con ID " + detalleReq.getProductoId() + " no está activo.");
            }
            if (producto.getStock() < detalleReq.getCantidad()) {
                throw new StockInsuficienteException(
                        producto.getNombre(), producto.getStock(), detalleReq.getCantidad());
            }

            Detalle detalle = ventaMapper.toDetalle(detalleReq, producto.getPrecio());
            venta.addDetalle(detalle);
            total = total.add(detalle.getSubtotal());
        }

        venta.setTotal(total);
        Venta saved = ventaRepository.save(venta);

        // 4. Ajustar stock en ms-producto
        for (Detalle detalle : saved.getDetalles()) {
            try {
                productoClient.ajustarStock(detalle.getProductoId(), -detalle.getCantidad());
            } catch (Exception e) {
                log.error("Fallo al ajustar stock del producto {}. Anulando venta {}.",
                        detalle.getProductoId(), saved.getId(), e);
                saved.setEstado(EstadoVenta.ANULADA);
                ventaRepository.save(saved);
                throw new VentaInvalidaException(
                        "No se pudo ajustar el stock del producto " + detalle.getProductoId()
                                + ". La venta fue anulada automáticamente.");
            }
        }

        // 5. Confirmar
        saved.setEstado(EstadoVenta.CONFIRMADA);
        saved = ventaRepository.save(saved);

        return ventaMapper.toResponse(saved, cliente, resolveProductNames(saved.getDetalles()));
    }

    @Override
    @Transactional
    public VentaResponseDTO actualizar(Long id, VentaRequestDTO dto) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + id));
        if (venta.getEstado() != EstadoVenta.PENDIENTE) {
            throw new VentaInvalidaException(
                    "Solo se pueden editar ventas en estado PENDIENTE. Estado actual: " + venta.getEstado());
        }
        if (dto.getClienteId() != null) venta.setClienteId(dto.getClienteId());
        if (dto.getFecha() != null) venta.setFecha(dto.getFecha());
        return toResponseEnriquecido(ventaRepository.save(venta));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + id));
        ventaRepository.delete(venta);
    }

    @Override
    @Transactional
    public VentaResponseDTO confirmar(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + id));
        if (venta.getEstado() != EstadoVenta.PENDIENTE) {
            throw new VentaInvalidaException(
                    "Solo se pueden confirmar ventas en estado PENDIENTE. Estado actual: " + venta.getEstado());
        }
        venta.setEstado(EstadoVenta.CONFIRMADA);
        return toResponseEnriquecido(ventaRepository.save(venta));
    }

    @Override
    @Transactional
    public VentaResponseDTO anular(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + id));
        if (venta.getEstado() == EstadoVenta.ANULADA) {
            throw new VentaInvalidaException("La venta ya se encuentra anulada.");
        }

        // Devolver stock solo si estaba CONFIRMADA
        if (venta.getEstado() == EstadoVenta.CONFIRMADA) {
            for (Detalle detalle : venta.getDetalles()) {
                try {
                    productoClient.ajustarStock(detalle.getProductoId(), detalle.getCantidad());
                } catch (Exception e) {
                    log.warn("No se pudo devolver stock del producto {} al anular venta {}. Continuando.",
                            detalle.getProductoId(), id, e);
                }
            }
        }

        venta.setEstado(EstadoVenta.ANULADA);
        return toResponseEnriquecido(ventaRepository.save(venta));
    }

    // ─── helpers ────────────────────────────────────────────────────────────────

    private VentaResponseDTO toResponseEnriquecido(Venta venta) {
        ClienteExternoDTO cliente = clienteClient.obtenerPorId(venta.getClienteId());
        return ventaMapper.toResponse(venta, cliente, resolveProductNames(venta.getDetalles()));
    }

    private Map<Long, String> resolveProductNames(List<Detalle> detalles) {
        Map<Long, String> nombres = new HashMap<>();
        for (Detalle d : detalles) {
            ProductoExternoDTO p = productoClient.obtenerPorId(d.getProductoId());
            nombres.put(d.getProductoId(), p.getNombre());
        }
        return nombres;
    }
}
