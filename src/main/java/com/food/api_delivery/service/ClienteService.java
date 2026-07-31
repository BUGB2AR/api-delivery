package com.food.api_delivery.service;

import com.food.api_delivery.dto.request.ClienteRequestDTO;
import com.food.api_delivery.dto.response.ClienteResponseDTO;
import com.food.api_delivery.exception.ClienteNaoEncontradoException;
import com.food.api_delivery.mapper.request.ClienteRequestMapper;
import com.food.api_delivery.mapper.response.ClienteResponseMapper;
import com.food.api_delivery.model.Cliente;
import com.food.api_delivery.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional
    public ClienteResponseDTO criar(ClienteRequestDTO dto) {
        Cliente cliente = ClienteRequestMapper.toEntity(dto);
        repository.save(cliente);
        return ClienteResponseMapper.toResponse(cliente);
    }

    @Transactional
    public List<ClienteResponseDTO> listar() {
        return repository.findAll().stream().map(ClienteResponseMapper::toResponse).toList();
    }

    @Transactional
    public ClienteResponseDTO buscarPorId(UUID id) {
        Cliente cliente = repository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado."));
        return ClienteResponseMapper.toResponse(cliente);
    }

    @Transactional
    public ClienteResponseDTO atualizar(UUID id, ClienteRequestDTO dto) {
        Cliente cliente = repository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado."));

        cliente.setNome(dto.nome());
        cliente.setTelefone(dto.telefone());

        return ClienteResponseMapper.toResponse(repository.save(cliente));
    }

    @Transactional
    public void excluir(UUID id) {
        Cliente cliente = repository.findById(id).orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado."));
        repository.delete(cliente);
    }
}
