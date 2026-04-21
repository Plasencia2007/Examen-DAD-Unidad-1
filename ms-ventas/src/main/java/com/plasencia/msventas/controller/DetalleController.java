package com.plasencia.msventas.controller;

import com.plasencia.msventas.dto.DetalleRequestDTO;
import com.plasencia.msventas.dto.DetalleResponseDTO;
import com.plasencia.msventas.service.DetalleService;
import com.plasencia.msventas.validation.groups.OnCreate;
import com.plasencia.msventas.validation.groups.OnUpdate;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles")
@RequiredArgsConstructor
@Validated
public class DetalleController {

    private final DetalleService detalleService;

    @GetMapping
    public List<DetalleResponseDTO> listar() {
        return detalleService.listarTodos();
    }

    @GetMapping("/{id}")
    public DetalleResponseDTO obtener(@PathVariable @Min(1) Long id) {
        return detalleService.obtenerPorId(id);
    }

    @GetMapping("/venta/{ventaId}")
    public List<DetalleResponseDTO> porVenta(@PathVariable @Min(1) Long ventaId) {
        return detalleService.listarPorVenta(ventaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DetalleResponseDTO crear(@Validated(OnCreate.class) @RequestBody DetalleRequestDTO dto) {
        return detalleService.crear(dto);
    }

    @PutMapping("/{id}")
    public DetalleResponseDTO actualizar(@PathVariable @Min(1) Long id,
                                         @Validated(OnUpdate.class) @RequestBody DetalleRequestDTO dto) {
        return detalleService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable @Min(1) Long id) {
        detalleService.eliminar(id);
    }
}
