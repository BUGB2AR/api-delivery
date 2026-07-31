package com.food.api_delivery.mapper.request;

import com.food.api_delivery.dto.request.EnderecoRequestDTO;
import com.food.api_delivery.model.Endereco;

public final class EnderecoRequestMapper {

    private EnderecoRequestMapper() {}

    public static Endereco toEntity(EnderecoRequestDTO dto) {

        Endereco endereco = new Endereco();

        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setCep(dto.cep());

        return endereco;
    }
}