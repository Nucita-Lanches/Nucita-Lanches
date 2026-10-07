package com.senai.nucitalanches.nucitalanches.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BancoDeDados {

    private static BancoDeDados instancia;

    // Produtos
    private final Map<Long, Produto> produtos = new HashMap<>();
    private Long ultimoIdProduto = 0L;

    // Mesas
    private final ArrayList<Mesa> mesas = new ArrayList<>();

    // Pedidos
    private final Map<Integer, Pedido> pedidos = new HashMap<>();
    private Integer ultimoNumeroPedido = 0;

    // Itens de Pedido
    private final ArrayList<ItemPedido> itensPedidos = new ArrayList<>();

    private BancoDeDados() {
    }

    public static BancoDeDados getInstancia() {
        if (instancia == null) {
            instancia = new BancoDeDados();
        }
        return instancia;
    }

    // PRODUTOS
    public Produto salvarProduto(Produto produto) {
        ultimoIdProduto += 1;
        produto.setId(ultimoIdProduto);
        produtos.put(ultimoIdProduto, produto);
        return produto;
    }

    public Collection<Produto> listarProdutos() {
        return produtos.values();
    }

    public Map<Long, Produto> getProdutos() {
        return produtos;
    }

    public Produto buscarProdutoPorId(Long id) {
        return produtos.get(id);
    }

    // MESAS
    public void adicionarMesa(Mesa mesa) {
        if (buscarMesaPorNumero(mesa.getNumero()) != null) {
            throw new IllegalArgumentException("Já existe uma mesa com esse identificador.");
        }
        mesas.add(mesa);
    }

    public Mesa buscarMesaPorNumero(Integer numero) {
        if (numero == null) {
            return null;
        }
        for (Mesa m : mesas) {
            if (m.getNumero() != null && m.getNumero().equals(numero)) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Mesa> listarMesas() {
        return mesas;
    }

    public ArrayList<Mesa> getMesas() {
        return mesas;
    }

    // ITENS DE PEDIDO
    public void adicionarItemPedido(ItemPedido itemPedido) {
        itensPedidos.add(itemPedido);
    }

    public ArrayList<ItemPedido> listarItensPedidos() {
        return itensPedidos;
    }

    public ArrayList<ItemPedido> getItensPedidos() {
        return itensPedidos;
    }

    // PEDIDOS
    public Pedido salvarPedido(Pedido pedido) {
        ultimoNumeroPedido += 1;
        pedido.setNumeroPedido(ultimoNumeroPedido);
        pedidos.put(ultimoNumeroPedido, pedido);
        return pedido;
    }

    public Collection<Pedido> listarPedidos() {
        return pedidos.values();
    }

    public Map<Integer, Pedido> getPedidos() {
        return pedidos;
    }

    public Pedido buscarPedidoPorNumero(Integer numeroPedido) {
        return pedidos.get(numeroPedido);
    }
}