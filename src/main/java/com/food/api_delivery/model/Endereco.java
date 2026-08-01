package com.food.api_delivery.model;

import com.food.api_delivery.exception.BusinessException;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "enderecos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String logradouro;

    @Column(nullable = false, length = 20)
    private String numero;

    @Column(length = 100)
    private String complemento;

    @Column(nullable = false, length = 100)
    private String bairro;

    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(nullable = false, length = 10)
    private String cep;

    @Column(nullable = false)
    private Boolean ativo = true;

    public void inativar() {
        this.ativo = false;
    }
    public void ativar() {
        this.ativo = true;
    }

    @PrePersist
    @PreUpdate
    public void validar() {
        validarCampoTexto(this.logradouro, "O logradouro do endereço é obrigatório.");
        validarCampoTexto(this.numero, "O número do endereço é obrigatório.");
        validarCampoTexto(this.bairro, "O bairro é obrigatório.");
        validarCampoTexto(this.cidade, "A cidade é obrigatória.");
        validarCampoTexto(this.cep, "O CEP é obrigatório.");
    }

    private void validarCampoTexto(String valor, String mensagemErro) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new BusinessException(mensagemErro);
        }
    }
}
