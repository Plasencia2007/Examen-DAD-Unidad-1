package com.plasencia.msproducto.service;

import com.plasencia.msproducto.dto.CategoriaRequestDTO;
import com.plasencia.msproducto.dto.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaService {
    List<CategoriaResponseDTO> listarTodas();
    List<CategoriaResponseDTO> listarActivas();
    CategoriaResponseDTO obtenerPorId(Long id);
    CategoriaResponseDTO crear(CategoriaRequestDTO dto);
    CategoriaResponseDTO actualizar(Long id, CategoriaRequestDTO dto);
    void eliminar(Long id);
}
