package com.food.api_delivery.dto.request;

public record EnderecoRequestDTO(String logradouro,
                                 String numero,
                                 String complemento,
                                 String bairro,
                                 String cidade,
                                 String cep,
                                 boolean ativo) {
}
