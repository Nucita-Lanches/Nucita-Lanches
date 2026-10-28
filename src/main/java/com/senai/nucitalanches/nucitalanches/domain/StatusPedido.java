package com.senai.nucitalanches.nucitalanches.domain;

/** Estados disponíveis durante o ciclo de vida de um pedido. */
public enum StatusPedido {
    /** Pedido que ainda pode receber itens. */
    ABERTO,

    /** Pedido que representa uma conta finalizada. */
    FINALIZADO
}
