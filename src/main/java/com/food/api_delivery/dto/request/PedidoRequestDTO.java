package com.food.api_delivery.dto.request;

import java.util.List;
import java.util.UUID;

public record PedidoRequestDTO(UUID clienteId, UUID enderecoId, List<ItemPedidoRequestDTO> itens) {
}