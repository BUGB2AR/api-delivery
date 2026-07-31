package com.food.api_delivery.mapper;

import com.food.api_delivery.dto.request.PedidoRequestDTO;
import com.food.api_delivery.model.Pedido;

public final class PedidoMapper {

    private PedidoMapper() {}

    public static Pedido toEntity(PedidoRequestDTO dto) {

        Pedido pedido = Pedido.novoPedido();
        pedido.adicionarItens(ItemPedidoMapper.toEntityList(dto.itens()));

        return pedido;
    }
}