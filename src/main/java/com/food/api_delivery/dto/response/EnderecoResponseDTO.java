package com.food.api_delivery.dto.response;

import java.util.UUID;

public record EnderecoResponseDTO(UUID id,
                                  String logradouro,
                                  String numero,
                                  String complemento,
                                  String bairro,
                                  String cidade,
                                  String cep,
                                  Boolean ativo) {
}
