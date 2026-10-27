package com.senai.nucitalanches.nucitalanches.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Representa um pedido de uma mesa e os itens incluídos nele. */
public class Pedido {
    private Integer numeroPedido;
    private final Mesa mesa;
    private final List<ItemPedido> itens = new ArrayList<>();
    private StatusPedido status = StatusPedido.ABERTO;

    /**
     * Cria um pedido para uma mesa.
     *
     * @param mesa mesa associada ao pedido
     * @throws IllegalArgumentException se a mesa for nula
     */
    public Pedido(Mesa mesa) {
        if (mesa == null) {
            throw new IllegalArgumentException("Mesa do pedido não pode ser nula.");
        }
        this.mesa = mesa;
    }

    /** Retorna o número atribuído ao pedido pelo armazenamento. */
    public Integer getNumeroPedido() {
        return numeroPedido;
    }

    /** Define o número do pedido atribuído pelo armazenamento. */
    public void setNumeroPedido(Integer numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    /** Retorna a mesa associada ao pedido. */
    public Mesa getMesa() {
        return mesa;
    }

    /** Retorna uma cópia somente para leitura dos itens do pedido. */
    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(new ArrayList<>(itens));
    }

    /**
     * Adiciona um item ou soma sua quantidade à do mesmo produto, mantendo o preço original.
     *
     * @param item item a ser adicionado
     * @return item incluído ou item existente cuja quantidade foi somada
     * @throws IllegalArgumentException se o item for nulo
     */
    public ItemPedido adicionarItem(ItemPedido item) {
        if (item == null) {
            throw new IllegalArgumentException("Item do pedido não pode ser nulo.");
        }
        for (ItemPedido existente : itens) {
            Produto produto = existente.getProduto();
            Produto recebido = item.getProduto();
            if (produto == recebido || (produto.getId() != null && produto.getId().equals(recebido.getId()))) {
                try {
                    existente.setQuantidade(Math.addExact(existente.getQuantidade(), item.getQuantidade()));
                } catch (ArithmeticException exception) {
                    throw new IllegalArgumentException("Quantidade total ultrapassa o limite permitido.");
                }
                return existente;
            }
        }
        itens.add(item);
        return item;
    }

    /** Atualiza a quantidade de um item que pertence a este pedido. */
    public void alterarQuantidadeItem(ItemPedido item, Integer quantidade) {
        validarItemDoPedido(item);
        item.setQuantidade(quantidade);
    }

    /** Remove um item deste pedido; o total é recalculado na próxima consulta. */
    public void removerItem(ItemPedido item) {
        validarItemDoPedido(item);
        itens.remove(item);
    }

    private void validarItemDoPedido(ItemPedido item) {
        if (item == null || !itens.contains(item)) {
            throw new IllegalArgumentException("Item não pertence ao pedido.");
        }
    }

    /** Soma os subtotais atuais, sem manter um total em cache. */
    public double getTotal() {
        return itens.stream().mapToDouble(ItemPedido::getSubtotal).sum();
    }

    /** Retorna o estado atual do pedido. */
    public StatusPedido getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "numeroPedido=" + numeroPedido +
                ", mesa=" + mesa +
                ", itens=" + itens +
                ", status=" + status +
                '}';
    }
}
