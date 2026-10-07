package com.senai.nucitalanches.nucitalanches.entities;

import java.util.ArrayList;

public class Restaurante {

    public void cadastroProduto(Produto produto) {
        BancoDeDados.getInstancia().salvarProduto(produto);
    }

    public void listarProdutos() {
        for (Produto p : BancoDeDados.getInstancia().listarProdutos()) {
            System.out.println(p);
        }
    }

    public void cadastrarMesa(Mesa mesa) {
        BancoDeDados.getInstancia().adicionarMesa(mesa);
    }

    public Mesa consultarMesaPorNumero(Integer numero) {
        return BancoDeDados.getInstancia().buscarMesaPorNumero(numero);
    }

    public ArrayList<Mesa> listarMesas() {
        return BancoDeDados.getInstancia().listarMesas();
    }
}