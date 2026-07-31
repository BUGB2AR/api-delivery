package com.food.api_delivery.dto.request;

import java.time.LocalDateTime;

public record ApiErrorResponseDTO(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
}