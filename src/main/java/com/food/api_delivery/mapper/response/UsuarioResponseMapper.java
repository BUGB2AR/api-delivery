package com.food.api_delivery.mapper.response;

import com.food.api_delivery.dto.response.UsuarioResponseDTO;
import com.food.api_delivery.model.Usuario;

public final class UsuarioResponseMapper {

    private UsuarioResponseMapper() {}

    public static UsuarioResponseDTO toResponse(Usuario usuario) {

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }
}