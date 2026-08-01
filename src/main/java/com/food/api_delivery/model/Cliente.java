package com.food.api_delivery.model;

import com.food.api_delivery.exception.BusinessException;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 20)
    private String telefone;

    @Builder.Default
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Pedido> pedidos = new ArrayList<>();

    public boolean possuiPedidos() {
        return this.pedidos != null && !this.pedidos.isEmpty();
    }

    public void validarExclusao() {
        if (possuiPedidos()) {
            throw new BusinessException("Não é possível excluir o cliente, pois existem pedidos vinculados a ele.");
        }
    }
}