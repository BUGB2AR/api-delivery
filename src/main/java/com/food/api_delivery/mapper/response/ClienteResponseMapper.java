package com.food.api_delivery.mapper.response;

import com.food.api_delivery.dto.response.ClienteResponseDTO;
import com.food.api_delivery.model.Cliente;

public final class ClienteResponseMapper {

    private ClienteResponseMapper() {}

    public static ClienteResponseDTO toResponse(Cliente cliente) {
        return new ClienteResponseDTO(cliente.getId(), cliente.getNome(), cliente.getTelefone()
        );
    }
}