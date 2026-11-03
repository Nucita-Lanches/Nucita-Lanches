package com.senai.nucitalanches.nucitalanches.domain;

public class ItemPedido {
    private final Produto produto;
    private Integer quantidade;
    private final double precoUnitario;

    public ItemPedido(Produto produto, Integer quantidade) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto do item não pode ser nulo.");
        }
        validarQuantidade(quantidade);
        this.produto = produto;
        this.quantidade = quantidade;
        precoUnitario = produto.getPreco();
    }


    public Produto getProduto() {
        return produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public double getSubtotal() {
        return precoUnitario * quantidade;
    }

    void setQuantidade(Integer quantidade) {
        validarQuantidade(quantidade);
        this.quantidade = quantidade;
    }

    private static void validarQuantidade(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade do item deve ser maior que zero.");
        }
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "produto=" + produto +
                ", quantidade=" + quantidade +
                '}';
    }
}
