package com.food.api_delivery.model;

import com.food.api_delivery.exception.BusinessException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_entrega_id", nullable = false)
    private Endereco enderecoEntrega;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusPedido status;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    @Builder.Default
    @OneToMany(
            mappedBy = "pedido",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ItemPedido> itens = new ArrayList<>();

    public static Pedido novoPedido() {
        Pedido pedido = new Pedido();

        pedido.status = StatusPedido.RECEBIDO;
        pedido.valorTotal = BigDecimal.ZERO;
        pedido.dataCriacao = LocalDateTime.now();
        pedido.dataAtualizacao = LocalDateTime.now();
        pedido.itens = new ArrayList<>();

        return pedido;
    }

    public void definirCliente(Cliente cliente) {
        if (cliente == null) {
            throw new BusinessException("Cliente é obrigatório.");
        }

        this.cliente = cliente;
        atualizarData();
    }

    public void definirEnderecoEntrega(Endereco enderecoEntrega) {
        if (enderecoEntrega == null) {
            throw new BusinessException("Endereço de entrega é obrigatório.");
        }

        this.enderecoEntrega = enderecoEntrega;
        atualizarData();
    }

    public void adicionarItens(List<ItemPedido> itens) {
        if (itens == null || itens.isEmpty()) {
            throw new BusinessException("O pedido deve possuir ao menos um item.");
        }

        itens.forEach(this::adicionarItem);
    }

    public void adicionarItem(ItemPedido item) {
        if (item == null) {
            throw new BusinessException("Item do pedido não pode ser nulo.");
        }

        item.vincularPedido(this);

        this.itens.add(item);

        recalcularValorTotal();
        atualizarData();
    }

    public void atualizarStatus(StatusPedido novoStatus) {
        if (novoStatus == null) {
            throw new BusinessException("Status do pedido é obrigatório.");
        }
        this.status.validarTransicaoPara(novoStatus);
        this.status = novoStatus;
        atualizarData();
    }


    private void recalcularValorTotal() {
        this.valorTotal = this.itens.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void atualizarData() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}