package com.plasencia.msventas.service;

import com.plasencia.msventas.dto.VentaRequestDTO;
import com.plasencia.msventas.dto.VentaResponseDTO;
import com.plasencia.msventas.entity.EstadoVenta;

import java.util.List;

public interface VentaService {
    List<VentaResponseDTO> listarTodas();
    List<VentaResponseDTO> listarPorCliente(Long clienteId);
    List<VentaResponseDTO> listarPorEstado(EstadoVenta estado);
    VentaResponseDTO obtenerPorId(Long id);
    VentaResponseDTO crear(VentaRequestDTO dto);
    VentaResponseDTO actualizar(Long id, VentaRequestDTO dto);
    void eliminar(Long id);
    VentaResponseDTO confirmar(Long id);
    VentaResponseDTO anular(Long id);
}
