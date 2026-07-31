package com.food.api_delivery.service;

import com.food.api_delivery.dto.request.CadastroUsuarioRequestDTO;
import com.food.api_delivery.dto.request.LoginRequestDTO;
import com.food.api_delivery.dto.response.AuthResponseDTO;
import com.food.api_delivery.exception.CredenciaisInvalidasException;
import com.food.api_delivery.exception.EmailJaCadastradoException;
import com.food.api_delivery.model.Usuario;
import com.food.api_delivery.repository.UsuarioRepository;
import com.food.api_delivery.security.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService service;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Test
    @DisplayName("Deve cadastrar usuário com sucesso")
    void deveCadastrarUsuarioComSucesso() {

        CadastroUsuarioRequestDTO dto =
                new CadastroUsuarioRequestDTO(
                        "Francisco",
                        "francisco@email.com",
                        "123456"
                );

        Usuario usuarioSalvo = Usuario.builder()
                .id(UUID.randomUUID())
                .nome("Francisco")
                .email("francisco@email.com")
                .senha("senha-criptografada")
                .build();

        when(usuarioRepository.existsByEmail(dto.email()))
                .thenReturn(false);

        when(passwordEncoder.encode(dto.senha()))
                .thenReturn("senha-criptografada");

        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(usuarioSalvo);

        when(jwtService.gerarToken(usuarioSalvo.getEmail()))
                .thenReturn("token-jwt-gerado");

        AuthResponseDTO response = service.cadastrar(dto);

        assertNotNull(response);

        assertEquals(usuarioSalvo.getId(), response.usuarioId());

        assertEquals("Francisco", response.nome());

        assertEquals("francisco@email.com", response.email());

        assertEquals("token-jwt-gerado", response.token());

        verify(usuarioRepository).existsByEmail(dto.email());

        verify(passwordEncoder).encode(dto.senha());

        verify(usuarioRepository).save(any(Usuario.class));

        verify(jwtService).gerarToken(usuarioSalvo.getEmail());
    }

    @Test
    @DisplayName("Deve lançar exceção ao cadastrar e-mail já existente")
    void deveLancarExcecaoQuandoEmailJaEstiverCadastrado() {

        CadastroUsuarioRequestDTO dto =
                new CadastroUsuarioRequestDTO(
                        "Francisco",
                        "francisco@email.com",
                        "123456"
                );

        when(usuarioRepository.existsByEmail(dto.email())).thenReturn(true);

        assertThrows(EmailJaCadastradoException.class, () -> service.cadastrar(dto));

        verify(usuarioRepository).existsByEmail(dto.email());

        verify(usuarioRepository, never()).save(any(Usuario.class));

        verify(passwordEncoder, never()).encode(dto.senha());

        verify(jwtService, never()).gerarToken(any());
    }

    @Test
    @DisplayName("Deve realizar login com sucesso")
    void deveRealizarLoginComSucesso() {

        LoginRequestDTO dto =
                new LoginRequestDTO(
                        "francisco@email.com",
                        "123456"
                );

        Usuario usuario = Usuario.builder()
                .id(UUID.randomUUID())
                .nome("Francisco")
                .email("francisco@email.com")
                .senha("senha-criptografada")
                .build();

        when(usuarioRepository.findByEmail(dto.email())).thenReturn(Optional.of(usuario));

        when(passwordEncoder.matches(dto.senha(), usuario.getSenha())).thenReturn(true);

        when(jwtService.gerarToken(usuario.getEmail())).thenReturn("token-jwt-gerado");

        AuthResponseDTO response = service.login(dto);

        assertNotNull(response);

        assertEquals(usuario.getId(), response.usuarioId());

        assertEquals("Francisco", response.nome());

        assertEquals("francisco@email.com", response.email());

        assertEquals("token-jwt-gerado", response.token());

        verify(usuarioRepository).findByEmail(dto.email());

        verify(passwordEncoder).matches(dto.senha(), usuario.getSenha());

        verify(jwtService).gerarToken(usuario.getEmail());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar login com e-mail inexistente")
    void deveLancarExcecaoQuandoEmailNaoExistirNoLogin() {

        LoginRequestDTO dto =
                new LoginRequestDTO(
                        "naoexiste@email.com",
                        "123456"
                );

        when(usuarioRepository.findByEmail(dto.email())).thenReturn(Optional.empty());

        assertThrows(CredenciaisInvalidasException.class, () -> service.login(dto));

        verify(usuarioRepository).findByEmail(dto.email());

        verify(passwordEncoder, never()).matches(any(), any());

        verify(jwtService, never()).gerarToken(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar login com senha inválida")
    void deveLancarExcecaoQuandoSenhaForInvalida() {

        LoginRequestDTO dto =
                new LoginRequestDTO(
                        "francisco@email.com",
                        "senha-errada"
                );

        Usuario usuario = Usuario.builder()
                .id(UUID.randomUUID())
                .nome("Francisco")
                .email("francisco@email.com")
                .senha("senha-criptografada")
                .build();

        when(usuarioRepository.findByEmail(dto.email())).thenReturn(Optional.of(usuario));

        when(passwordEncoder.matches(dto.senha(), usuario.getSenha())).thenReturn(false);

        assertThrows(CredenciaisInvalidasException.class, () -> service.login(dto));

        verify(usuarioRepository).findByEmail(dto.email());

        verify(passwordEncoder).matches(dto.senha(), usuario.getSenha());

        verify(jwtService, never()).gerarToken(any());
    }
}