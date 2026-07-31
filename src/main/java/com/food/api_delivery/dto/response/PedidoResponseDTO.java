package com.food.api_delivery.dto.response;

import com.food.api_delivery.model.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PedidoResponseDTO(
        UUID id,
        UUID clienteId,
        String clienteNome,
        UUID enderecoId,
        String enderecoEntrega,
        StatusPedido status,
        BigDecimal valorTotal,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao,
        List<ItemPedidoResponseDTO> itens
) {
}