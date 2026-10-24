package com.senai.nucitalanches.nucitalanches.application;

import com.senai.nucitalanches.nucitalanches.domain.Mesa;
import com.senai.nucitalanches.nucitalanches.domain.Produto;
import com.senai.nucitalanches.nucitalanches.storage.BancoDeDados;

import java.util.ArrayList;
import java.util.Collection;

public class Restaurante {

    public void cadastroProduto(Produto produto) {
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
}
