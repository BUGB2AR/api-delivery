package com.food.api_delivery.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemPedidoResponseDTO(UUID id, String nomeProduto,
                                    Integer quantidade,
                                    BigDecimal precoUnitario,
                                    BigDecimal subtotal) {
}