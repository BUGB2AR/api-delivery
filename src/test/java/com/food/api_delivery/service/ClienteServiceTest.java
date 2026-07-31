package com.food.api_delivery.service;

import com.food.api_delivery.dto.request.ClienteRequestDTO;
import com.food.api_delivery.dto.response.ClienteResponseDTO;
import com.food.api_delivery.exception.ClienteNaoEncontradoException;
import com.food.api_delivery.model.Cliente;
import com.food.api_delivery.repository.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @InjectMocks
    private ClienteService service;

    @Mock
    private ClienteRepository repository;

    @Test
    @DisplayName("Deve criar cliente com sucesso")
    void deveCriarCliente() {

        ClienteRequestDTO dto =
                new ClienteRequestDTO(
                        "Francisco",
                        "85999999999"
                );

        when(repository.save(any(Cliente.class)))
                .thenAnswer(invocation -> {
                    Cliente cliente = invocation.getArgument(0);
                    cliente.setId(UUID.randomUUID());
                    return cliente;
                });

        ClienteResponseDTO response = service.criar(dto);

        assertNotNull(response.id());

        assertEquals("Francisco", response.nome());

        assertEquals("85999999999", response.telefone());
    }

    @Test
    @DisplayName("Deve buscar cliente por id")
    void deveBuscarClientePorId() {

        UUID id = UUID.randomUUID();

        Cliente cliente = Cliente.builder()
                .id(id)
                .nome("Francisco")
                .telefone("85999999999")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(cliente));

        ClienteResponseDTO response = service.buscarPorId(id);

        assertEquals(id, response.id());

        assertEquals("Francisco", response.nome());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar cliente inexistente")
    void deveLancarExcecaoAoBuscarClienteInexistente() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> service.buscarPorId(id));
    }

    @Test
    @DisplayName("Deve listar clientes")
    void deveListarClientes() {

        Cliente cliente = Cliente.builder()
                .id(UUID.randomUUID())
                .nome("Francisco")
                .telefone("85999999999")
                .build();

        when(repository.findAll()).thenReturn(List.of(cliente));

        List<ClienteResponseDTO> response = service.listar();

        assertEquals(1, response.size());

        assertEquals("Francisco", response.get(0).nome());
    }

    @Test
    @DisplayName("Deve atualizar cliente")
    void deveAtualizarCliente() {

        UUID id = UUID.randomUUID();

        Cliente cliente = Cliente.builder()
                .id(id)
                .nome("Francisco")
                .telefone("85999999999")
                .build();

        ClienteRequestDTO dto =
                new ClienteRequestDTO(
                        "Francisco Paiva",
                        "85888888888"
                );

        when(repository.findById(id)).thenReturn(Optional.of(cliente));

        when(repository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ClienteResponseDTO response = service.atualizar(id, dto);

        assertEquals("Francisco Paiva", response.nome());

        assertEquals("85888888888", response.telefone());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar cliente inexistente")
    void deveLancarExcecaoAoAtualizarClienteInexistente() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        ClienteRequestDTO dto =
                new ClienteRequestDTO(
                        "Francisco",
                        "85999999999"
                );

        assertThrows(ClienteNaoEncontradoException.class, () -> service.atualizar(id, dto));
    }

    @Test
    @DisplayName("Deve excluir cliente")
    void deveExcluirCliente() {

        UUID id = UUID.randomUUID();

        Cliente cliente = Cliente.builder()
                .id(id)
                .nome("Francisco")
                .telefone("85999999999")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(cliente));

        service.excluir(id);

        verify(repository, times(1)).delete(cliente);
    }

    @Test
    @DisplayName("Deve lançar exceção ao excluir cliente inexistente")
    void deveLancarExcecaoAoExcluirClienteInexistente() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> service.excluir(id));
    }
}