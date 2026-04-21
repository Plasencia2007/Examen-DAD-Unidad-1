package com.plasencia.msventas.controller;

import com.plasencia.msventas.dto.VentaRequestDTO;
import com.plasencia.msventas.dto.VentaResponseDTO;
import com.plasencia.msventas.entity.EstadoVenta;
import com.plasencia.msventas.service.VentaService;
import com.plasencia.msventas.validation.groups.OnCreate;
import com.plasencia.msventas.validation.groups.OnUpdate;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@Validated
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public List<VentaResponseDTO> listar() {
        return ventaService.listarTodas();
    }

    @GetMapping("/{id}")
    public VentaResponseDTO obtener(@PathVariable @Min(1) Long id) {
        return ventaService.obtenerPorId(id);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<VentaResponseDTO> porCliente(@PathVariable @Min(1) Long clienteId) {
        return ventaService.listarPorCliente(clienteId);
    }

    @GetMapping("/estado/{estado}")
    public List<VentaResponseDTO> porEstado(@PathVariable EstadoVenta estado) {
        return ventaService.listarPorEstado(estado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VentaResponseDTO crear(@Validated(OnCreate.class) @RequestBody VentaRequestDTO dto) {
        return ventaService.crear(dto);
    }

    @PutMapping("/{id}")
    public VentaResponseDTO actualizar(@PathVariable @Min(1) Long id,
                                       @Validated(OnUpdate.class) @RequestBody VentaRequestDTO dto) {
        return ventaService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable @Min(1) Long id) {
        ventaService.eliminar(id);
    }

    @PostMapping("/{id}/confirmar")
    public VentaResponseDTO confirmar(@PathVariable @Min(1) Long id) {
        return ventaService.confirmar(id);
    }

    @PostMapping("/{id}/anular")
    public VentaResponseDTO anular(@PathVariable @Min(1) Long id) {
        return ventaService.anular(id);
    }
}
