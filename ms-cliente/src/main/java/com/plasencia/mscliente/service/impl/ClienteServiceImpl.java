package com.plasencia.mscliente.service.impl;

import com.plasencia.mscliente.dto.ClienteRequestDTO;
import com.plasencia.mscliente.dto.ClienteResponseDTO;
import com.plasencia.mscliente.entity.Cliente;
import com.plasencia.mscliente.exception.EmailDuplicadoException;
import com.plasencia.mscliente.exception.ResourceNotFoundException;
import com.plasencia.mscliente.mapper.ClienteMapper;
import com.plasencia.mscliente.repository.ClienteRepository;
import com.plasencia.mscliente.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO obtenerPorId(Long id) {
        return clienteRepository.findById(id)
                .map(clienteMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public ClienteResponseDTO crear(ClienteRequestDTO dto) {
        // Validación defensiva (aunque ya se valida en el DTO con @UniqueEmail)
        if (clienteRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailDuplicadoException(dto.getEmail());
        }
        Cliente cliente = clienteMapper.toEntity(dto);
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    @Transactional
    public ClienteResponseDTO actualizar(Long id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));

        // Validación manual de email para el caso de actualización
        clienteRepository.findByEmail(dto.getEmail())
                .ifPresent(c -> {
                    if (!c.getId().equals(id)) {
                        throw new EmailDuplicadoException(dto.getEmail());
                    }
                });

        clienteMapper.updateEntity(cliente, dto);
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado con id: " + id);
        }
        clienteRepository.deleteById(id);
    }
}
