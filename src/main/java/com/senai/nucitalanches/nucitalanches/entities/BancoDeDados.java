package com.senai.nucitalanches.nucitalanches.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class BancoDeDados {

    private static final BancoDeDados INSTANCIA = new BancoDeDados();

    // Produtos
    private final Map<Long, Produto> produtos = new LinkedHashMap<>();
    private Long ultimoIdProduto = 0L;

    // Mesas
    private final ArrayList<Mesa> mesas = new ArrayList<>();

    // Pedidos
    private final Map<Integer, Pedido> pedidos = new LinkedHashMap<>();
    private Integer ultimoNumeroPedido = 0;

    // Itens de Pedido
    private final ArrayList<ItemPedido> itensPedidos = new ArrayList<>();

    private BancoDeDados() {
    }

    public static BancoDeDados getInstancia() {
        return INSTANCIA;
    }

    // PRODUTOS
    public Produto salvarProduto(Produto produto) {
        Objects.requireNonNull(produto, "Produto não pode ser nulo.");
        ultimoIdProduto += 1;
        produto.setId(ultimoIdProduto);
        produtos.put(ultimoIdProduto, produto);
        return produto;
    }

    public Collection<Produto> listarProdutos() {
        return Collections.unmodifiableList(new ArrayList<>(produtos.values()));
    }

    public Map<Long, Produto> getProdutos() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(produtos));
    }

    public Produto buscarProdutoPorId(Long id) {
        return produtos.get(id);
    }

    // MESAS
    public boolean adicionarMesa(Mesa mesa) {
        if (mesa == null || mesa.getNumero() == null || mesa.getNumero() <= 0) {
            return false;
        }

        if (buscarMesaPorNumero(mesa.getNumero()) != null) {
            return false;
        }

        mesas.add(mesa);
        return true;
    }

    public Mesa buscarMesaPorNumero(Integer numero) {
        if (numero == null) {
            return null;
        }

        for (Mesa mesa : mesas) {
            if (mesa.getNumero() != null && mesa.getNumero().equals(numero)) {
                return mesa;
            }
        }

        return null;
    }

    public ArrayList<Mesa> listarMesas() {
        return new ArrayList<>(mesas);
    }

    public ArrayList<Mesa> getMesas() {
        return new ArrayList<>(mesas);
    }

    // ITENS DE PEDIDO
    public void adicionarItemPedido(ItemPedido itemPedido) {
        itensPedidos.add(itemPedido);
    }

    public ArrayList<ItemPedido> listarItensPedidos() {
        return new ArrayList<>(itensPedidos);
    }

    public ArrayList<ItemPedido> getItensPedidos() {
        return new ArrayList<>(itensPedidos);
    }

    // PEDIDOS
    public Pedido salvarPedido(Pedido pedido) {
        Objects.requireNonNull(pedido, "Pedido não pode ser nulo.");
        ultimoNumeroPedido += 1;
        pedido.setNumeroPedido(ultimoNumeroPedido);
        pedidos.put(ultimoNumeroPedido, pedido);
        return pedido;
    }

    public Collection<Pedido> listarPedidos() {
        return Collections.unmodifiableList(new ArrayList<>(pedidos.values()));
    }

    public Map<Integer, Pedido> getPedidos() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(pedidos));
    }

    public Pedido buscarPedidoPorNumero(Integer numeroPedido) {
        return pedidos.get(numeroPedido);
    }
}
