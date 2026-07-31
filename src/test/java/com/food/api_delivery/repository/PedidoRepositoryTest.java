package com.food.api_delivery.repository;

import com.food.api_delivery.model.Cliente;
import com.food.api_delivery.model.Endereco;
import com.food.api_delivery.model.ItemPedido;
import com.food.api_delivery.model.Pedido;
import com.food.api_delivery.model.StatusPedido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Test
    @DisplayName("Deve salvar pedido com sucesso")
    void deveSalvarPedido() {

        Cliente cliente = clienteRepository.save(
                Cliente.builder()
                        .nome("Francisco3")
                        .telefone("85999999999")
                        .build()
        );

        Endereco endereco = enderecoRepository.save(
                Endereco.builder()
                        .logradouro("Rua A")
                        .numero("100")
                        .bairro("Centro")
                        .cidade("Fortaleza")
                        .cep("60000-000")
                        .build()
        );

        Pedido pedido = Pedido.novoPedido();

        pedido.definirCliente(cliente);
        pedido.definirEnderecoEntrega(endereco);

        ItemPedido item = ItemPedido.builder()
                .nomeProduto("Pizza")
                .quantidade(2)
                .precoUnitario(BigDecimal.valueOf(40))
                .build();

        pedido.adicionarItem(item);

        Pedido salvo = repository.save(pedido);

        assertNotNull(salvo.getId());

        assertEquals(StatusPedido.RECEBIDO, salvo.getStatus());

        assertEquals(BigDecimal.valueOf(80), salvo.getValorTotal());
    }

    @Test
    @DisplayName("Deve buscar pedido por id")
    void deveBuscarPedidoPorId() {

        Cliente cliente = clienteRepository.save(
                Cliente.builder()
                        .nome("Francisco3")
                        .telefone("85999999999")
                        .build()
        );

        Endereco endereco = enderecoRepository.save(
                Endereco.builder()
                        .logradouro("Rua A")
                        .numero("100")
                        .bairro("Centro")
                        .cidade("Fortaleza")
                        .cep("60000-000")
                        .build()
        );

        Pedido pedido = Pedido.novoPedido();

        pedido.definirCliente(cliente);
        pedido.definirEnderecoEntrega(endereco);

        Pedido salvo = repository.save(pedido);

        Optional<Pedido> resultado = repository.findById(salvo.getId());

        assertTrue(resultado.isPresent());

        assertEquals(salvo.getId(), resultado.get().getId());
    }

    @Test
    @DisplayName("Não deve encontrar pedido inexistente")
    void naoDeveEncontrarPedidoInexistente() {
        Optional<Pedido> resultado = repository.findById(java.util.UUID.randomUUID());
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Deve listar pedidos")
    void deveListarPedidos() {

        Cliente cliente = clienteRepository.save(
                Cliente.builder()
                        .nome("Francisco3")
                        .telefone("85999999999")
                        .build()
        );

        Endereco endereco = enderecoRepository.save(
                Endereco.builder()
                        .logradouro("Rua A")
                        .numero("100")
                        .bairro("Centro")
                        .cidade("Fortaleza")
                        .cep("60000-000")
                        .build()
        );

        Pedido pedido = Pedido.novoPedido();

        pedido.definirCliente(cliente);
        pedido.definirEnderecoEntrega(endereco);

        repository.save(pedido);

        List<Pedido> pedidos = repository.findAll();
        assertFalse(pedidos.isEmpty());
    }

    @Test
    @DisplayName("Deve excluir pedido")
    void deveExcluirPedido() {

        Cliente cliente = clienteRepository.save(
                Cliente.builder()
                        .nome("Francisco3")
                        .telefone("85999999999")
                        .build()
        );

        Endereco endereco = enderecoRepository.save(
                Endereco.builder()
                        .logradouro("Rua A")
                        .numero("100")
                        .bairro("Centro")
                        .cidade("Fortaleza")
                        .cep("60000-000")
                        .build()
        );

        Pedido pedido = Pedido.novoPedido();

        pedido.definirCliente(cliente);
        pedido.definirEnderecoEntrega(endereco);

        Pedido salvo = repository.save(pedido);

        repository.deleteById(salvo.getId());

        Optional<Pedido> resultado = repository.findById(salvo.getId());
        assertFalse(resultado.isPresent());
    }
}