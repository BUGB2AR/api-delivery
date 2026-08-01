package com.food.api_delivery.service;

import com.food.api_delivery.dto.request.EnderecoRequestDTO;
import com.food.api_delivery.dto.response.EnderecoResponseDTO;
import com.food.api_delivery.exception.EnderecoNaoEncontradoException;
import com.food.api_delivery.model.Endereco;
import com.food.api_delivery.repository.EnderecoRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService service;

    @Mock
    private EnderecoRepository repository;

    @Test
    @DisplayName("Deve criar endereço com sucesso")
    void deveCriarEndereco() {

        EnderecoRequestDTO dto =
                new EnderecoRequestDTO(
                        "Rua A",
                        "100",
                        "Casa",
                        "Centro",
                        "Fortaleza",
                        "60000-000",
                        true
                );

        when(repository.save(any(Endereco.class)))
                .thenAnswer(invocation -> {
                    Endereco endereco = invocation.getArgument(0);
                    endereco.setId(UUID.randomUUID());
                    return endereco;
                });

        EnderecoResponseDTO response = service.criar(dto);

        assertNotNull(response.id());

        assertEquals("Rua A", response.logradouro());

        assertEquals("100", response.numero());
    }

    @Test
    @DisplayName("Deve buscar endereço por id")
    void deveBuscarEnderecoPorId() {

        UUID id = UUID.randomUUID();

        Endereco endereco = Endereco.builder()
                .id(id)
                .logradouro("Rua A")
                .numero("100")
                .complemento("Casa")
                .bairro("Centro")
                .cidade("Fortaleza")
                .cep("60000-000")
                .ativo(true)
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(endereco));

        EnderecoResponseDTO response = service.buscarPorId(id);

        assertEquals(id, response.id());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar endereço inexistente")
    void deveLancarExcecaoAoBuscarEnderecoInexistente() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EnderecoNaoEncontradoException.class, () -> service.buscarPorId(id));
    }

    @Test
    @DisplayName("Deve listar endereços")
    void deveListarEnderecos() {

        Endereco endereco = Endereco.builder()
                .id(UUID.randomUUID())
                .logradouro("Rua A")
                .numero("100")
                .complemento("Casa")
                .bairro("Centro")
                .cidade("Fortaleza")
                .cep("60000-000")
                .ativo(true)
                .build();

        when(repository.findAll()).thenReturn(List.of(endereco));

        List<EnderecoResponseDTO> resultado = service.listar();

        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Deve atualizar endereço")
    void deveAtualizarEndereco() {

        UUID id = UUID.randomUUID();

        Endereco endereco = Endereco.builder()
                .id(id)
                .logradouro("Rua Antiga")
                .numero("10")
                .complemento("Casa")
                .bairro("Centro")
                .cidade("Fortaleza")
                .cep("60000-000")
                .ativo(true)
                .build();

        EnderecoRequestDTO dto =
                new EnderecoRequestDTO(
                        "Rua Nova",
                        "20",
                        "Apartamento",
                        "Cocó",
                        "Fortaleza",
                        "60192-000",
                        true
                );

        when(repository.findById(id)).thenReturn(Optional.of(endereco));

        when(repository.save(any(Endereco.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EnderecoResponseDTO response = service.atualizar(id, dto);

        assertEquals("Rua Nova", response.logradouro());

        assertEquals("20", response.numero());

        assertEquals("60192-000", response.cep());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar endereço inexistente")
    void deveLancarExcecaoAoAtualizarEnderecoInexistente() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        EnderecoRequestDTO dto =
                new EnderecoRequestDTO(
                        "Rua Nova",
                        "20",
                        "Apartamento",
                        "Cocó",
                        "Fortaleza",
                        "60192-000",
                        true
                );

        assertThrows(EnderecoNaoEncontradoException.class, () -> service.atualizar(id, dto));
    }

    @Test
    @DisplayName("Deve excluir endereço")
    void deveInativarEndereco() {

        UUID id = UUID.randomUUID();

        Endereco endereco = Endereco.builder()
                .id(id)
                .logradouro("Rua A")
                .numero("100")
                .complemento("Casa")
                .bairro("Centro")
                .cidade("Fortaleza")
                .cep("60000-000")
                .ativo(true)
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(endereco));

        service.inativar(id);

        verify(repository, never()).delete(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao excluir endereço inexistente")
    void deveLancarExcecaoAoInativarEnderecoInexistente() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(EnderecoNaoEncontradoException.class, () -> service.inativar(id));
    }
}