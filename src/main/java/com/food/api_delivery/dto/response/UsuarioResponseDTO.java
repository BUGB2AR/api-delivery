package com.food.api_delivery.dto.response;

import java.util.UUID;

public record UsuarioResponseDTO(UUID id, String nome, String email) {
}