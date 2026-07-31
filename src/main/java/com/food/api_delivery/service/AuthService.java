package com.food.api_delivery.service;

import com.food.api_delivery.dto.request.CadastroUsuarioRequestDTO;
import com.food.api_delivery.dto.request.LoginRequestDTO;
import com.food.api_delivery.dto.response.AuthResponseDTO;
import com.food.api_delivery.exception.CredenciaisInvalidasException;
import com.food.api_delivery.exception.EmailJaCadastradoException;
import com.food.api_delivery.mapper.request.UsuarioRequestMapper;
import com.food.api_delivery.model.Usuario;
import com.food.api_delivery.repository.UsuarioRepository;
import com.food.api_delivery.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public AuthResponseDTO cadastrar(CadastroUsuarioRequestDTO dto) {

        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new EmailJaCadastradoException(
                    "E-mail já cadastrado."
            );
        }

        Usuario usuario = UsuarioRequestMapper.toEntity(dto);

        usuario.setSenha(
                passwordEncoder.encode(dto.senha())
        );

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        String token = jwtService.gerarToken(
                usuarioSalvo.getEmail()
        );

        return new AuthResponseDTO(
                usuarioSalvo.getId(),
                usuarioSalvo.getNome(),
                usuarioSalvo.getEmail(),
                token
        );
    }

    @Transactional(readOnly = true)
    public AuthResponseDTO login(LoginRequestDTO dto) {

        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new CredenciaisInvalidasException(
                        "E-mail ou senha inválidos."
                ));

        boolean senhaValida = passwordEncoder.matches(
                dto.senha(),
                usuario.getSenha()
        );

        if (!senhaValida) {
            throw new CredenciaisInvalidasException(
                    "E-mail ou senha inválidos."
            );
        }

        String token = jwtService.gerarToken(
                usuario.getEmail()
        );

        return new AuthResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                token
        );
    }
}