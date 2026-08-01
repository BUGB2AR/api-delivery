package com.food.api_delivery.service;

import com.food.api_delivery.dto.request.EnderecoRequestDTO;
import com.food.api_delivery.dto.response.EnderecoResponseDTO;
import com.food.api_delivery.exception.EnderecoNaoEncontradoException;
import com.food.api_delivery.mapper.request.EnderecoRequestMapper;
import com.food.api_delivery.mapper.response.EnderecoResponseMapper;
import com.food.api_delivery.model.Endereco;
import com.food.api_delivery.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository repository;

    @Transactional
    public EnderecoResponseDTO criar(EnderecoRequestDTO dto) {
        Endereco endereco = EnderecoRequestMapper.toEntity(dto);
        endereco.validar();

        repository.save(endereco);
        return EnderecoResponseMapper.toResponse(endereco);
    }

    @Transactional(readOnly = true)
    public List<EnderecoResponseDTO> listar() {
        return repository.findAll().stream().map(EnderecoResponseMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public EnderecoResponseDTO buscarPorId(UUID id) {
        Endereco endereco = buscarEndereco(id);
        return EnderecoResponseMapper.toResponse(endereco);
    }

    @Transactional
    public EnderecoResponseDTO atualizar(UUID id, EnderecoRequestDTO dto) {

        Endereco endereco = buscarEndereco(id);

        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setCep(dto.cep());

        repository.save(endereco);

        return EnderecoResponseMapper.toResponse(endereco);
    }

    @Transactional
    public void inativar(UUID id) {
        Endereco endereco = buscarEndereco(id);
        endereco.inativar();
    }

    @Transactional
    public void ativar(UUID id) {
        Endereco endereco = buscarEndereco(id);
        endereco.ativar();
    }

    private Endereco buscarEndereco(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EnderecoNaoEncontradoException("Endereço não encontrado."));
    }
}