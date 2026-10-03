package com.senai.nucitalanches.nucitalanches.entities;

import java.util.ArrayList;

public class Restaurante {
    private ArrayList<Mesa> mesas;
    private ArrayList<Produto> produtos;
    private ArrayList<Pedido> Pedidos;
    private ArrayList<ItemPedido> itensPedidos;


    public void cadastroProduto(Produto produto) {
        produtos.add(produto);

    }
}



