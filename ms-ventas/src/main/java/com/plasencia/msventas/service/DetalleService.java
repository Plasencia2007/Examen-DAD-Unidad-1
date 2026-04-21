package com.plasencia.msventas.service;

import com.plasencia.msventas.dto.DetalleRequestDTO;
import com.plasencia.msventas.dto.DetalleResponseDTO;

import java.util.List;

public interface DetalleService {
    List<DetalleResponseDTO> listarTodos();
    List<DetalleResponseDTO> listarPorVenta(Long ventaId);
    DetalleResponseDTO obtenerPorId(Long id);
    DetalleResponseDTO crear(DetalleRequestDTO dto);
    DetalleResponseDTO actualizar(Long id, DetalleRequestDTO dto);
    void eliminar(Long id);
}
