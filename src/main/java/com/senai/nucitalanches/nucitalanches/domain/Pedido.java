package com.senai.nucitalanches.nucitalanches.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {
    private Integer numeroPedido;
    private final Mesa mesa;
    private final List<ItemPedido> itens = new ArrayList<>();
    private StatusPedido status = StatusPedido.ABERTO;

    public Pedido(Mesa mesa) {
        if (mesa == null) {
            throw new IllegalArgumentException("Mesa do pedido não pode ser nula.");
        }
        this.mesa = mesa;
    }

    public Integer getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(Integer numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(new ArrayList<>(itens));
    }

    public ItemPedido adicionarItem(ItemPedido item) {
        garantirAberto();
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

    public void alterarQuantidadeItem(ItemPedido item, Integer quantidade) {
        garantirAberto();
        validarItemDoPedido(item);
        item.setQuantidade(quantidade);
    }

    public void removerItem(ItemPedido item) {
        garantirAberto();
        validarItemDoPedido(item);
        itens.remove(item);
    }

    public void finalizar() {
        garantirAberto();
        if (itens.isEmpty()) {
            throw new IllegalArgumentException("Pedido sem itens não pode ser finalizado.");
        }
        status = StatusPedido.FINALIZADO;
    }

    private void garantirAberto() {
        if (status == StatusPedido.FINALIZADO) {
            throw new IllegalStateException("Pedido finalizado não pode ser alterado.");
        }
    }

    private void validarItemDoPedido(ItemPedido item) {
        if (item == null || !itens.contains(item)) {
            throw new IllegalArgumentException("Item não pertence ao pedido.");
        }
    }

    public double getTotal() {
        return itens.stream().mapToDouble(ItemPedido::getSubtotal).sum();
    }

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
