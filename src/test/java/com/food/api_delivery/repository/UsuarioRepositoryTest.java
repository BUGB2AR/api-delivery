package com.food.api_delivery.repository;

import com.food.api_delivery.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    @Test
    @DisplayName("Deve salvar usuário com sucesso")
    void deveSalvarUsuario() {

        Usuario usuario = Usuario.builder()
                .nome("Francisco3")
                .email("francisco3@email.com")
                .senha("123456")
                .build();

        Usuario usuarioSalvo = repository.save(usuario);
        assertNotNull(usuarioSalvo.getId());
        assertEquals("Francisco3", usuarioSalvo.getNome());
        assertEquals("francisco3@email.com", usuarioSalvo.getEmail());
    }

    @Test
    @DisplayName("Deve buscar usuário por e-mail")
    void deveBuscarUsuarioPorEmail() {
        Optional<Usuario> resultado = repository.findByEmail("francisco3@email.com");
        assertTrue(resultado.isPresent());
        assertEquals("Francisco3", resultado.get().getNome());
    }

    @Test
    @DisplayName("Não deve encontrar usuário inexistente")
    void naoDeveEncontrarUsuarioInexistente() {
        Optional<Usuario> resultado = repository.findByEmail("naoexiste@email.com");
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando o e-mail existir")
    void deveRetornarTrueQuandoEmailExistir() {
        boolean existe = repository.existsByEmail("francisco3@email.com");
        assertTrue(existe);
    }

    @Test
    @DisplayName("Deve retornar falso quando o e-mail não existir")
    void deveRetornarFalseQuandoEmailNaoExistir() {
        boolean existe = repository.existsByEmail("teste@email.com");
        assertFalse(existe);
    }

    @Test
    @DisplayName("Deve excluir usuário")
    void deveExcluirUsuario() {

        Usuario usuario = Usuario.builder()
                .nome("Francisco3")
                .email("francisco3@email.com")
                .senha("123456")
                .build();

        Usuario salvo = repository.save(usuario);
        repository.deleteById(salvo.getId());

        Optional<Usuario> resultado = repository.findById(salvo.getId());
        assertFalse(resultado.isPresent());
    }
}