package com.food.api_delivery.service;

import com.food.api_delivery.dto.request.AtualizarStatusPedidoRequestDTO;
import com.food.api_delivery.dto.request.ItemPedidoRequestDTO;
import com.food.api_delivery.dto.request.PedidoRequestDTO;
import com.food.api_delivery.dto.response.PedidoResponseDTO;
import com.food.api_delivery.exception.ClienteNaoEncontradoException;
import com.food.api_delivery.exception.EnderecoNaoEncontradoException;
import com.food.api_delivery.exception.PedidoNaoEncontradoException;
import com.food.api_delivery.model.Cliente;
import com.food.api_delivery.model.Endereco;
import com.food.api_delivery.model.Pedido;
import com.food.api_delivery.model.StatusPedido;
import com.food.api_delivery.repository.ClienteRepository;
import com.food.api_delivery.repository.EnderecoRepository;
import com.food.api_delivery.repository.PedidoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @InjectMocks
    private PedidoService service;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Test
    @DisplayName("Deve criar pedido com sucesso")
    void deveCriarPedido() {

        UUID clienteId = UUID.randomUUID();
        UUID enderecoId = UUID.randomUUID();

        Cliente cliente = Cliente.builder()
                .id(clienteId)
                .nome("Francisco3")
                .telefone("85999999999")
                .build();

        Endereco endereco = Endereco.builder()
                .id(enderecoId)
                .logradouro("Rua A")
                .numero("100")
                .bairro("Centro")
                .cidade("Fortaleza")
                .cep("60000-000")
                .build();

        PedidoRequestDTO dto = new PedidoRequestDTO(clienteId, enderecoId, List.of(new ItemPedidoRequestDTO("Pizza", 2, BigDecimal.valueOf(40))));

        when(clienteRepository.findById(clienteId))
                .thenReturn(Optional.of(cliente));

        when(enderecoRepository.findById(enderecoId))
                .thenReturn(Optional.of(endereco));

        when(pedidoRepository.save(any(Pedido.class)))
                .thenAnswer(invocation -> {
                    Pedido pedido = invocation.getArgument(0);
                    pedido.setId(UUID.randomUUID());
                    return pedido;
                });

        PedidoResponseDTO response = service.criar(dto);

        assertNotNull(response);

        assertEquals(StatusPedido.RECEBIDO, response.status());

        assertEquals(BigDecimal.valueOf(80), response.valorTotal());
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente não existir")
    void deveLancarExcecaoClienteNaoEncontrado() {

        UUID clienteId = UUID.randomUUID();
        UUID enderecoId = UUID.randomUUID();

        PedidoRequestDTO dto =
                new PedidoRequestDTO(
                        clienteId,
                        enderecoId,
                        List.of()
                );

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> service.criar(dto));
    }

    @Test
    @DisplayName("Deve lançar exceção quando endereço não existir")
    void deveLancarExcecaoEnderecoNaoEncontrado() {

        UUID clienteId = UUID.randomUUID();
        UUID enderecoId = UUID.randomUUID();

        Cliente cliente = Cliente.builder()
                .id(clienteId)
                .nome("Francisco3")
                .telefone("85999999999")
                .build();

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.empty());

        PedidoRequestDTO dto =
                new PedidoRequestDTO(
                        clienteId,
                        enderecoId,
                        List.of()
                );

        assertThrows(EnderecoNaoEncontradoException.class, () -> service.criar(dto)
        );
    }

    @Test
    @DisplayName("Deve atualizar status do pedido")
    void deveAtualizarStatus() {

        UUID pedidoId = UUID.randomUUID();

        Cliente cliente = Cliente.builder()
                .id(UUID.randomUUID())
                .nome("Francisco3")
                .telefone("85999999999")
                .build();

        Endereco endereco = Endereco.builder()
                .id(UUID.randomUUID())
                .logradouro("Rua A")
                .numero("100")
                .bairro("Centro")
                .cidade("Fortaleza")
                .cep("60000-000")
                .build();

        Pedido pedido = Pedido.novoPedido();

        pedido.setId(pedidoId);

        pedido.definirCliente(cliente);

        pedido.definirEnderecoEntrega(endereco);

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));

        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AtualizarStatusPedidoRequestDTO dto =
                new AtualizarStatusPedidoRequestDTO(
                        StatusPedido.EM_PREPARO
                );

        PedidoResponseDTO response =
                service.atualizarStatus(
                        pedidoId,
                        dto
                );

        assertEquals(StatusPedido.EM_PREPARO, response.status());

        assertEquals(pedidoId, response.id());
    }

    @Test
    @DisplayName("Deve lançar exceção quando pedido não existir")
    void deveLancarExcecaoPedidoNaoEncontrado() {

        UUID pedidoId = UUID.randomUUID();

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.empty());

        AtualizarStatusPedidoRequestDTO dto =
                new AtualizarStatusPedidoRequestDTO(
                        StatusPedido.EM_PREPARO
                );

        assertThrows(
                PedidoNaoEncontradoException.class,
                () -> service.atualizarStatus(
                        pedidoId,
                        dto
                )
        );
    }

    @Test
    @DisplayName("Deve buscar pedido por id")
    void deveBuscarPedidoPorId() {

        UUID pedidoId = UUID.randomUUID();

        Cliente cliente = Cliente.builder()
                .id(UUID.randomUUID())
                .nome("Francisco3")
                .telefone("85999999999")
                .build();

        Endereco endereco = Endereco.builder()
                .id(UUID.randomUUID())
                .logradouro("Rua A")
                .numero("100")
                .bairro("Centro")
                .cidade("Fortaleza")
                .cep("60000-000")
                .build();

        Pedido pedido = Pedido.novoPedido();

        pedido.setId(pedidoId);

        pedido.definirCliente(cliente);

        pedido.definirEnderecoEntrega(endereco);

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));

        PedidoResponseDTO response = service.buscarPorId(pedidoId);

        assertEquals(pedidoId, response.id());
    }
}