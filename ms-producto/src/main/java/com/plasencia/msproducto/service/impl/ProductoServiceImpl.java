package com.plasencia.msproducto.service.impl;

import com.plasencia.msproducto.dto.ProductoRequestDTO;
import com.plasencia.msproducto.dto.ProductoResponseDTO;
import com.plasencia.msproducto.entity.Categoria;
import com.plasencia.msproducto.entity.Producto;
import com.plasencia.msproducto.exception.ResourceNotFoundException;
import com.plasencia.msproducto.exception.StockInsuficienteException;
import com.plasencia.msproducto.mapper.ProductoMapper;
import com.plasencia.msproducto.repository.CategoriaRepository;
import com.plasencia.msproducto.repository.ProductoRepository;
import com.plasencia.msproducto.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listarTodos() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listarActivos() {
        return productoRepository.findByActivoTrue().stream()
                .map(productoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId).stream()
                .map(productoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDTO obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .map(productoMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + dto.getCategoriaId()));
        
        Producto producto = productoMapper.toEntity(dto, categoria);
        return productoMapper.toResponse(productoRepository.save(producto));
    }

    @Override
    @Transactional
    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + dto.getCategoriaId()));

        productoMapper.updateEntity(producto, dto, categoria);
        return productoMapper.toResponse(productoRepository.save(producto));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        producto.setActivo(false); // Soft delete
        productoRepository.save(producto);
    }

    @Override
    @Transactional
    public ProductoResponseDTO ajustarStock(Long id, int delta) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        
        int nuevoStock = producto.getStock() + delta;
        if (nuevoStock < 0) {
            throw new StockInsuficienteException("Stock insuficiente para el producto: " + producto.getNombre() + ". Stock actual: " + producto.getStock());
        }
        
        producto.setStock(nuevoStock);
        return productoMapper.toResponse(productoRepository.save(producto));
    }
}
