package com.food.api_delivery.repository;

import com.food.api_delivery.model.Cliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository repository;

    @Test
    @DisplayName("Deve salvar cliente com sucesso")
    void deveSalvarCliente() {

        Cliente cliente = Cliente.builder()
                .nome("Francisco3")
                .telefone("85999999999")
                .build();

        Cliente clienteSalvo = repository.save(cliente);

        assertNotNull(clienteSalvo.getId());
        assertEquals("Francisco3", clienteSalvo.getNome());
        assertEquals("85999999999", clienteSalvo.getTelefone());
    }

    @Test
    @DisplayName("Deve buscar cliente por id")
    void deveBuscarClientePorId() {

        Cliente cliente = Cliente.builder()
                .nome("Francisco3")
                .telefone("85999999999")
                .build();

        Cliente clienteSalvo = repository.save(cliente);
        Optional<Cliente> resultado = repository.findById(clienteSalvo.getId());
        assertEquals(clienteSalvo.getId(), resultado.orElseThrow().getId());
        assertEquals("Francisco3", resultado.orElseThrow().getNome());
    }

    @Test
    @DisplayName("Não deve encontrar cliente inexistente")
    void naoDeveEncontrarClienteInexistente() {
        Optional<Cliente> resultado = repository.findById(UUID.randomUUID());
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Deve listar clientes")
    void deveListarClientes() {

        Cliente cliente = Cliente.builder()
                .nome("Francisco3")
                .telefone("85999999999")
                .build();

        repository.save(cliente);

        List<Cliente> clientes = repository.findAll();
        assertFalse(clientes.isEmpty());
    }

    @Test
    @DisplayName("Deve atualizar cliente")
    void deveAtualizarCliente() {

        Cliente cliente = Cliente.builder()
                .nome("Francisco3")
                .telefone("85999999999")
                .build();

        Cliente clienteSalvo = repository.save(cliente);

        clienteSalvo.setNome("Francisco Paiva3");
        clienteSalvo.setTelefone("85888888888");

        Cliente atualizado = repository.save(clienteSalvo);

        assertEquals("Francisco Paiva3", atualizado.getNome());
        assertEquals("85888888888", atualizado.getTelefone());
    }

    @Test
    @DisplayName("Deve excluir cliente")
    void deveExcluirCliente() {

        Cliente cliente = Cliente.builder()
                .nome("Francisco3")
                .telefone("85999999999")
                .build();

        Cliente clienteSalvo = repository.save(cliente);
        repository.deleteById(clienteSalvo.getId());
        Optional<Cliente> resultado = repository.findById(clienteSalvo.getId());
        assertFalse(resultado.isPresent());
    }
}