package com.senai.nucitalanches.nucitalanches.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProdutoTest {

    @Test
    void cadastraEConsultaProdutoValido() {
        Produto produto = new Produto("Coxinha", 8.50);
        Restaurante restaurante = new Restaurante();

        restaurante.cadastroProduto(produto);

        assertNotNull(produto.getId());
        assertSame(produto, BancoDeDados.getInstancia().buscarProdutoPorId(produto.getId()));
        assertTrue(BancoDeDados.getInstancia().listarProdutos().contains(produto));
        assertEquals(8.50, produto.getPreco(), 0.001);
        assertEquals("Coxinha", produto.getNome());
    }

    @Test
    void aceitaPrecoIgualAZero() {
        assertEquals(0.0, new Produto("Água", 0.0).getPreco(), 0.001);
    }

    @Test
    void rejeitaNomeNuloOuEmBranco() {
        assertThrows(IllegalArgumentException.class, () -> new Produto(null, 1.0));
        assertThrows(IllegalArgumentException.class, () -> new Produto("", 1.0));
        assertThrows(IllegalArgumentException.class, () -> new Produto("   ", 1.0));
    }

    @Test
    void rejeitaPrecoNegativoOuNaoFinito() {
        assertThrows(IllegalArgumentException.class, () -> new Produto("Coxinha", -1.0));
        assertThrows(IllegalArgumentException.class, () -> new Produto("Coxinha", Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> new Produto("Coxinha", Double.POSITIVE_INFINITY));
    }

    @Test
    void settersTambemAplicamAsValidacoes() {
        Produto produto = new Produto("Coxinha", 8.50);

        assertThrows(IllegalArgumentException.class, () -> produto.setNome(" "));
        assertThrows(IllegalArgumentException.class, () -> produto.setPreco(-0.01));
    }
}