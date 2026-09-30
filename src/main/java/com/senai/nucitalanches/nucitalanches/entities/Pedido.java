package com.senai.nucitalanches.nucitalanches.entities;

public class Pedido {
    private Integer numeroPedido;
    private ItemPedido itemPedido;

    public Pedido(Integer numeroPedido, ItemPedido itemPedido) {
        this.numeroPedido = numeroPedido;
        this.itemPedido = itemPedido;
    }

    public Integer getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(Integer numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public ItemPedido getItemPedido() {
        return itemPedido;
    }

    public void setItemPedido(ItemPedido itemPedido) {
        this.itemPedido = itemPedido;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "numeroPedido=" + numeroPedido +
                ", itemPedido=" + itemPedido +
                '}';
    }
}
