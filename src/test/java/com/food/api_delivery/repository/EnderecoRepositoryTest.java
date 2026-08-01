package com.food.api_delivery.repository;

import com.food.api_delivery.model.Endereco;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class EnderecoRepositoryTest {

    @Autowired
    private EnderecoRepository repository;

    @Test
    @DisplayName("Deve salvar endereço com sucesso")
    void deveSalvarEndereco() {

        Endereco endereco = Endereco.builder()
                .logradouro("Rua das Flores")
                .numero("100")
                .complemento("Casa")
                .bairro("Centro")
                .cidade("Fortaleza")
                .cep("60000-000")
                .ativo(true)
                .build();

        Endereco enderecoSalvo = repository.save(endereco);

        assertNotNull(enderecoSalvo.getId());

        assertEquals("Rua das Flores", enderecoSalvo.getLogradouro());

        assertEquals("100", enderecoSalvo.getNumero());

        assertEquals("Centro", enderecoSalvo.getBairro());

        assertEquals("Fortaleza", enderecoSalvo.getCidade());

        assertEquals("60000-000", enderecoSalvo.getCep());
    }

    @Test
    @DisplayName("Deve buscar endereço por id")
    void deveBuscarEnderecoPorId() {

        Endereco endereco = Endereco.builder()
                .logradouro("Rua A")
                .numero("150")
                .complemento("Apto 301")
                .bairro("Aldeota")
                .cidade("Fortaleza")
                .cep("60150-000")
                .ativo(true)
                .build();

        Endereco enderecoSalvo = repository.save(endereco);
        Optional<Endereco> resultado = repository.findById(enderecoSalvo.getId());

        assertTrue(resultado.isPresent());

        assertEquals(enderecoSalvo.getId(), resultado.get().getId());

        assertEquals("Rua A", resultado.get().getLogradouro());
    }

    @Test
    @DisplayName("Não deve encontrar endereço inexistente")
    void naoDeveEncontrarEnderecoInexistente() {
        Optional<Endereco> resultado = repository.findById(UUID.randomUUID());
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Deve listar endereços")
    void deveListarEnderecos() {

        Endereco endereco = Endereco.builder()
                .logradouro("Rua B")
                .numero("200")
                .complemento("Bloco B")
                .bairro("Meireles")
                .cidade("Fortaleza")
                .cep("60160-000")
                .ativo(true)
                .build();

        repository.save(endereco);
        List<Endereco> enderecos = repository.findAll();
        assertFalse(enderecos.isEmpty());
    }

    @Test
    @DisplayName("Deve atualizar endereço")
    void deveAtualizarEndereco() {

        Endereco endereco = Endereco.builder()
                .logradouro("Rua Antiga")
                .numero("10")
                .complemento("Casa")
                .bairro("Centro")
                .cidade("Fortaleza")
                .cep("60000-000")
                .ativo(true)
                .build();

        Endereco enderecoSalvo = repository.save(endereco);

        enderecoSalvo.setLogradouro("Rua Nova");
        enderecoSalvo.setNumero("20");
        enderecoSalvo.setComplemento("Apartamento");
        enderecoSalvo.setBairro("Cocó");
        enderecoSalvo.setCidade("Fortaleza");
        enderecoSalvo.setCep("60192-000");
        enderecoSalvo.setAtivo(true);

        Endereco enderecoAtualizado = repository.save(enderecoSalvo);

        assertEquals("Rua Nova", enderecoAtualizado.getLogradouro());
        assertEquals("20", enderecoAtualizado.getNumero());

        assertEquals("Apartamento", enderecoAtualizado.getComplemento());

        assertEquals("Cocó", enderecoAtualizado.getBairro());

        assertEquals("60192-000", enderecoAtualizado.getCep());
    }

    @Test
    @DisplayName("Deve excluir endereço")
    void deveExcluirEndereco() {

        Endereco endereco = Endereco.builder()
                .logradouro("Rua para Excluir")
                .numero("300")
                .complemento("Casa")
                .bairro("Centro")
                .cidade("Fortaleza")
                .cep("60000-000")
                .ativo(true)
                .build();

        Endereco enderecoSalvo = repository.save(endereco);
        repository.deleteById(enderecoSalvo.getId());

        Optional<Endereco> resultado = repository.findById(enderecoSalvo.getId());
        assertFalse(resultado.isPresent());
    }
}