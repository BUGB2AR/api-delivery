package com.food.api_delivery.dto.response;

import java.util.UUID;

public record AuthResponseDTO(
        UUID usuarioId,
        String nome,
        String email,
        String token
) {
}