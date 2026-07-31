package com.food.api_delivery.mapper.request;

import com.food.api_delivery.dto.request.CadastroUsuarioRequestDTO;
import com.food.api_delivery.model.Usuario;

public final class UsuarioRequestMapper {

    private UsuarioRequestMapper() {}

    public static Usuario toEntity(CadastroUsuarioRequestDTO dto) {

        Usuario usuario = new Usuario();

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());

        return usuario;
    }
}