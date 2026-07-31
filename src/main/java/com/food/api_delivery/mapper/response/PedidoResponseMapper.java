package com.food.api_delivery.mapper.response;

import com.food.api_delivery.dto.response.PedidoResponseDTO;
import com.food.api_delivery.mapper.ItemPedidoMapper;
import com.food.api_delivery.model.Endereco;
import com.food.api_delivery.model.Pedido;

public final class PedidoResponseMapper {

    private PedidoResponseMapper() {
    }

    public static PedidoResponseDTO toResponse(Pedido pedido) {
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getCliente().getId(),
                pedido.getCliente().getNome(),
                pedido.getEnderecoEntrega().getId(),
                formatarEndereco(pedido.getEnderecoEntrega()),
                pedido.getStatus(),
                pedido.getValorTotal(),
                pedido.getDataCriacao(),
                pedido.getDataAtualizacao(),
                ItemPedidoMapper.toResponseList(pedido.getItens())
        );
    }

    private static String formatarEndereco(Endereco endereco) {
        return endereco.getLogradouro()
                + ", "
                + endereco.getNumero()
                + " - "
                + endereco.getBairro()
                + ", "
                + endereco.getCidade()
                + " - CEP: "
                + endereco.getCep();
    }
}