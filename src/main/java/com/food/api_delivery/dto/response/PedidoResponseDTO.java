package com.food.api_delivery.dto.response;

import com.food.api_delivery.model.StatusPedido;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record PedidoResponseDTO(UUID id, UUID clienteId,
                                String clienteNome,
                                StatusPedido status,
                                BigDecimal valorTotal,
                                List<ItemPedidoResponseDTO> itens) {
}