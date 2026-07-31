package com.food.api_delivery.mapper;

import com.food.api_delivery.dto.request.ItemPedidoRequestDTO;
import com.food.api_delivery.model.ItemPedido;
import java.util.List;

public final class ItemPedidoMapper {

    private ItemPedidoMapper() {}

    public static ItemPedido toEntity(ItemPedidoRequestDTO dto) {

        ItemPedido item = new ItemPedido();

        item.setNomeProduto(dto.nomeProduto());
        item.setQuantidade(dto.quantidade());
        item.setPrecoUnitario(dto.precoUnitario());

        return item;
    }

    public static List<ItemPedido> toEntityList(List<ItemPedidoRequestDTO> itens) {
        return itens.stream().map(ItemPedidoMapper::toEntity).toList();
    }
}
