package com.senai.nucitalanches.nucitalanches.application;

import com.senai.nucitalanches.nucitalanches.domain.ItemPedido;
import com.senai.nucitalanches.nucitalanches.domain.Mesa;
import com.senai.nucitalanches.nucitalanches.domain.Pedido;
import com.senai.nucitalanches.nucitalanches.domain.Produto;
import com.senai.nucitalanches.nucitalanches.storage.BancoDeDados;

import java.util.ArrayList;
import java.util.Collection;

public class Restaurante {

    public void cadastroProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo.");
        }
        BancoDeDados.getInstancia().salvarProduto(produto);
    }

    public Collection<Produto> listarProdutos() {
        return BancoDeDados.getInstancia().listarProdutos();
    }

    public boolean cadastrarMesa(Mesa mesa) {
        return BancoDeDados.getInstancia().adicionarMesa(mesa);
    }

    public Mesa consultarMesaPorNumero(Integer numero) {
        return BancoDeDados.getInstancia().buscarMesaPorNumero(numero);
    }

    public ArrayList<Mesa> listarMesas() {
        return BancoDeDados.getInstancia().listarMesas();
    }

    public Pedido criarPedido(Integer numeroMesa) {
        BancoDeDados bancoDeDados = BancoDeDados.getInstancia();
        Mesa mesa = bancoDeDados.buscarMesaPorNumero(numeroMesa);
        if (mesa == null) {
            throw new IllegalArgumentException("Mesa não cadastrada.");
        }

        return bancoDeDados.salvarPedido(new Pedido(mesa));
    }

    public ItemPedido adicionarItemAoPedido(Integer numeroPedido, Produto produto, Integer quantidade) {
        Pedido pedido = BancoDeDados.getInstancia().buscarPedidoPorNumero(numeroPedido);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado.");
        }

        Produto cadastrado = produto == null ? null : BancoDeDados.getInstancia().buscarProdutoPorId(produto.getId());
        if (cadastrado == null) {
            throw new IllegalArgumentException("Produto não cadastrado.");
        }

        return pedido.adicionarItem(new ItemPedido(cadastrado, quantidade));
    }

    public void alterarQuantidadeItem(Integer numeroPedido, ItemPedido item, Integer quantidade) {
        buscarPedido(numeroPedido).alterarQuantidadeItem(item, quantidade);
    }

    public void removerItemDoPedido(Integer numeroPedido, ItemPedido item) {
        buscarPedido(numeroPedido).removerItem(item);
    }

    public void finalizarPedido(Integer numeroPedido) {
        buscarPedido(numeroPedido).finalizar();
    }

    private Pedido buscarPedido(Integer numeroPedido) {
        Pedido pedido = BancoDeDados.getInstancia().buscarPedidoPorNumero(numeroPedido);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado.");
        }
        return pedido;
    }
}
