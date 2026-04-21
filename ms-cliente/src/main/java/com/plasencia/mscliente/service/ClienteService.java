package com.plasencia.mscliente.service;

import com.plasencia.mscliente.dto.ClienteRequestDTO;
import com.plasencia.mscliente.dto.ClienteResponseDTO;
import java.util.List;

public interface ClienteService {
    List<ClienteResponseDTO> listarTodos();
    ClienteResponseDTO obtenerPorId(Long id);
    ClienteResponseDTO crear(ClienteRequestDTO dto);
    ClienteResponseDTO actualizar(Long id, ClienteRequestDTO dto);
    void eliminar(Long id);
}
