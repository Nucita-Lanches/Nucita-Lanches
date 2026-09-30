package com.senai.nucitalanches.nucitalanches.entities;

public class Mesa {
    private Pedido pedido;
    private Integer numero;

    public Mesa(Pedido pedido, Integer numero) {
        this.pedido = pedido;
        this.numero = numero;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Mesa{" +
                "pedido=" + pedido +
                ", numero=" + numero +
                '}';
    }
}
