package com.food.api_delivery.model;

import com.food.api_delivery.exception.BusinessException;

public enum StatusPedido {

    RECEBIDO {
        @Override
        public void validarTransicaoPara(StatusPedido novoStatus) {
            if (novoStatus != EM_PREPARO && novoStatus != CANCELADO) {
                throw new BusinessException("Pedido recebido só pode ir para em preparo ou cancelado.");
            }
        }
    },

    EM_PREPARO {
        @Override
        public void validarTransicaoPara(StatusPedido novoStatus) {
            if (novoStatus != SAIU_PARA_ENTREGA && novoStatus != CANCELADO) {
                throw new BusinessException("Pedido em preparo só pode ir para saiu para entrega ou cancelado.");
            }
        }
    },

    SAIU_PARA_ENTREGA {
        @Override
        public void validarTransicaoPara(StatusPedido novoStatus) {
            if (novoStatus != ENTREGUE) {
                throw new BusinessException("Pedido saiu para entrega só pode ir para entregue.");
            }
        }
    },

    ENTREGUE {
        @Override
        public void validarTransicaoPara(StatusPedido novoStatus) {
            throw new BusinessException("Pedido entregue não pode ter o status alterado.");
        }
    },

    CANCELADO {
        @Override
        public void validarTransicaoPara(StatusPedido novoStatus) {
            throw new BusinessException("Pedido cancelado não pode ter o status alterado.");
        }
    };

    public abstract void validarTransicaoPara(StatusPedido novoStatus);
}