package com.plasencia.msproducto.service;

import com.plasencia.msproducto.dto.ProductoRequestDTO;
import com.plasencia.msproducto.dto.ProductoResponseDTO;

import java.util.List;

public interface ProductoService {
    List<ProductoResponseDTO> listarTodos();
    List<ProductoResponseDTO> listarActivos();
    List<ProductoResponseDTO> listarPorCategoria(Long categoriaId);
    ProductoResponseDTO obtenerPorId(Long id);
    ProductoResponseDTO crear(ProductoRequestDTO dto);
    ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto);
    void eliminar(Long id);
    ProductoResponseDTO ajustarStock(Long id, int delta);
}
