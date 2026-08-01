package com.food.api_delivery.mapper.response;

import com.food.api_delivery.dto.response.EnderecoResponseDTO;
import com.food.api_delivery.model.Endereco;

public final class EnderecoResponseMapper {

    private EnderecoResponseMapper() {}

    public static EnderecoResponseDTO toResponse(Endereco endereco) {

        return new EnderecoResponseDTO(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(), endereco.getCidade(),
                endereco.getCep(),
                endereco.getAtivo()
        );
    }
}
