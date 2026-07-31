package com.food.api_delivery.dto.response;

import java.util.UUID;

public record ClienteResponseDTO(UUID id, String nome, String telefone) {
}
