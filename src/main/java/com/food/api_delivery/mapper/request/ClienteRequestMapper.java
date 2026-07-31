package com.food.api_delivery.mapper.request;

import com.food.api_delivery.dto.request.ClienteRequestDTO;
import com.food.api_delivery.model.Cliente;

public final class ClienteRequestMapper {

    private ClienteRequestMapper() {}

    public static Cliente toEntity(ClienteRequestDTO dto) {

        Cliente cliente = new Cliente();

        cliente.setNome(dto.nome());
        cliente.setTelefone(dto.telefone());

        return cliente;
    }
}