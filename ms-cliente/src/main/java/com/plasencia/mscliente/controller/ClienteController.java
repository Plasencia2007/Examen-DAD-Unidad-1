package com.plasencia.mscliente.controller;

import com.plasencia.mscliente.dto.ClienteRequestDTO;
import com.plasencia.mscliente.dto.ClienteResponseDTO;
import com.plasencia.mscliente.service.ClienteService;
import com.plasencia.mscliente.validation.groups.OnCreate;
import com.plasencia.mscliente.validation.groups.OnUpdate;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Validated // Necesario para validaciones de parámetros (@PathVariable, etc.)
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public List<ClienteResponseDTO> listarTodos() {
        return clienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public ClienteResponseDTO obtenerPorId(
            @PathVariable @Min(value = 1, message = "{validation.id.min}") Long id
    ) {
        return clienteService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponseDTO crear(
            @Validated(OnCreate.class) @RequestBody ClienteRequestDTO dto
    ) {
        return clienteService.crear(dto);
    }

    @PutMapping("/{id}")
    public ClienteResponseDTO actualizar(
            @PathVariable @Min(value = 1, message = "{validation.id.min}") Long id,
            @Validated(OnUpdate.class) @RequestBody ClienteRequestDTO dto
    ) {
        return clienteService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(
            @PathVariable @Min(value = 1, message = "{validation.id.min}") Long id
    ) {
        clienteService.eliminar(id);
    }
}
