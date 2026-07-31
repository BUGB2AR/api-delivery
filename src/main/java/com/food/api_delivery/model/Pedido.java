package com.food.api_delivery.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    private Endereco enderecoEntrega;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    private BigDecimal valorTotal;

    @Builder.Default
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    public static Pedido novoPedido() {

        Pedido pedido = new Pedido();

        pedido.status = StatusPedido.CRIADO;
        pedido.valorTotal = BigDecimal.ZERO;

        return pedido;
    }

    public void adicionarItens(List<ItemPedido> itens) {
        itens.forEach(this::adicionarItem);
    }

    public void adicionarItem(ItemPedido item) {
        item.vincularPedido(this);
        itens.add(item);
        recalcularValorTotal();
    }

    private void recalcularValorTotal() {

        this.valorTotal = itens.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void atualizarStatus(StatusPedido status) {
        this.status = status;
    }
}
