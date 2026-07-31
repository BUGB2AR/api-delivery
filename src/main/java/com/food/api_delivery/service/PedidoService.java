package com.food.api_delivery.service;

import com.food.api_delivery.dto.request.AtualizarStatusPedidoRequestDTO;
import com.food.api_delivery.dto.request.PedidoRequestDTO;
import com.food.api_delivery.dto.response.PedidoResponseDTO;
import com.food.api_delivery.exception.ClienteNaoEncontradoException;
import com.food.api_delivery.exception.EnderecoNaoEncontradoException;
import com.food.api_delivery.exception.PedidoNaoEncontradoException;
import com.food.api_delivery.mapper.request.PedidoRequestMapper;
import com.food.api_delivery.mapper.response.PedidoResponseMapper;
import com.food.api_delivery.model.Cliente;
import com.food.api_delivery.model.Endereco;
import com.food.api_delivery.model.Pedido;
import com.food.api_delivery.repository.ClienteRepository;
import com.food.api_delivery.repository.EnderecoRepository;
import com.food.api_delivery.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;

    @Transactional
    public PedidoResponseDTO criar(PedidoRequestDTO dto) {

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado."));

        Endereco endereco = enderecoRepository.findById(dto.enderecoId())
                .orElseThrow(() -> new EnderecoNaoEncontradoException("Endereço não encontrado."));

        Pedido pedido = PedidoRequestMapper.toEntity(dto);

        pedido.definirCliente(cliente);
        pedido.definirEnderecoEntrega(endereco);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return PedidoResponseMapper.toResponse(pedidoSalvo);
    }

    @Transactional
    public PedidoResponseDTO atualizarStatus(UUID id, AtualizarStatusPedidoRequestDTO dto) {
        Pedido pedido = buscarPedidoEntityPorId(id);
        pedido.atualizarStatus(dto.status());

        Pedido pedidoAtualizado = pedidoRepository.save(pedido);
        return PedidoResponseMapper.toResponse(pedidoAtualizado);
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAll().stream().map(PedidoResponseMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPorId(UUID id) {
        Pedido pedido = buscarPedidoEntityPorId(id);
        return PedidoResponseMapper.toResponse(pedido);
    }

    private Pedido buscarPedidoEntityPorId(UUID id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não encontrado."));
    }
}