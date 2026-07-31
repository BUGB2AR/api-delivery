package com.food.api_delivery.dto.request;

public record LoginRequestDTO(
        String email,
        String senha
) {
}