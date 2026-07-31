package com.food.api_delivery.dto.request;

public record CadastroUsuarioRequestDTO(
        String nome,
        String email,
        String senha
) {
}