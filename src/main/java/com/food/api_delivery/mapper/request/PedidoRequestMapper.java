package com.food.api_delivery.mapper.request;

import com.food.api_delivery.dto.request.PedidoRequestDTO;
import com.food.api_delivery.mapper.ItemPedidoMapper;
import com.food.api_delivery.model.ItemPedido;
import com.food.api_delivery.model.Pedido;

import java.util.List;

public final class PedidoRequestMapper {

    private PedidoRequestMapper() {
    }

    public static Pedido toEntity(PedidoRequestDTO dto) {

        Pedido pedido = Pedido.novoPedido();
        List<ItemPedido> itens = ItemPedidoMapper.toEntityList(dto.itens());

        pedido.adicionarItens(itens);
        return pedido;
    }
}