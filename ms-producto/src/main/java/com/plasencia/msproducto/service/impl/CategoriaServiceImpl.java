package com.plasencia.msproducto.service.impl;

import com.plasencia.msproducto.dto.CategoriaRequestDTO;
import com.plasencia.msproducto.dto.CategoriaResponseDTO;
import com.plasencia.msproducto.entity.Categoria;
import com.plasencia.msproducto.exception.NombreDuplicadoException;
import com.plasencia.msproducto.exception.ResourceNotFoundException;
import com.plasencia.msproducto.mapper.CategoriaMapper;
import com.plasencia.msproducto.repository.CategoriaRepository;
import com.plasencia.msproducto.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarActivas() {
        return categoriaRepository.findByActivoTrue().stream()
                .map(categoriaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDTO obtenerPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(categoriaMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public CategoriaResponseDTO crear(CategoriaRequestDTO dto) {
        Categoria categoria = categoriaMapper.toEntity(dto);
        return categoriaMapper.toResponse(categoriaRepository.save(categoria));
    }

    @Override
    @Transactional
    public CategoriaResponseDTO actualizar(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));

        // Validación manual de nombre duplicado si cambió el nombre
        if (!categoria.getNombre().equalsIgnoreCase(dto.getNombre()) && 
            categoriaRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new NombreDuplicadoException("Ya existe una categoría con el nombre: " + dto.getNombre());
        }

        categoriaMapper.updateEntity(categoria, dto);
        return categoriaMapper.toResponse(categoriaRepository.save(categoria));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));
        categoria.setActivo(false); // Soft delete
        categoriaRepository.save(categoria);
    }
}
