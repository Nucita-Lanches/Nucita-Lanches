package com.senai.nucitalanches.nucitalanches.domain;

/** Representa um produto e sua quantidade dentro de um pedido. */
public class ItemPedido {
    private final Produto produto;
    private Integer quantidade;
    private final double precoUnitario;

    /**
     * Cria um item com produto e quantidade válidos.
     *
     * @param produto produto associado ao item
     * @param quantidade quantidade do produto
     * @throws IllegalArgumentException se o produto for nulo ou a quantidade não for positiva
     */
    public ItemPedido(Produto produto, Integer quantidade) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto do item não pode ser nulo.");
        }
        validarQuantidade(quantidade);
        this.produto = produto;
        this.quantidade = quantidade;
        precoUnitario = produto.getPreco();
    }


    /** Retorna o produto deste item. */
    public Produto getProduto() {
        return produto;
    }

    /** Retorna a quantidade deste item. */
    public Integer getQuantidade() {
        return quantidade;
    }

    /** Preço capturado na inclusão; alterações no catálogo não mudam este valor. */
    public double getPrecoUnitario() {
        return precoUnitario;
    }

    /** Calcula o valor do item com o preço da inclusão e a quantidade atual. */
    public double getSubtotal() {
        return precoUnitario * quantidade;
    }

    /**
     * Define uma quantidade maior que zero.
     *
     * @param quantidade nova quantidade
     * @throws IllegalArgumentException se a quantidade for nula ou não positiva
     */
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
