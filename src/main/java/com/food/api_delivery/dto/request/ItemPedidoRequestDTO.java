package com.food.api_delivery.dto.request;

import java.math.BigDecimal;

public record ItemPedidoRequestDTO(String nomeProduto,
                                   Integer quantidade,
                                   BigDecimal precoUnitario) {
}