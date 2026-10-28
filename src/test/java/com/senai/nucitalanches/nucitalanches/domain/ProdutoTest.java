package com.senai.nucitalanches.nucitalanches.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProdutoTest {

    @Test
    void removeEspacosNasBordasDoNome() {
        Produto produto = new Produto("  Suco  ", 5.5);

        assertEquals("Suco", produto.getNome());
    }

    @Test
    void rejeitaNomeNuloOuEmBranco() {
        assertThrows(IllegalArgumentException.class, () -> new Produto(null, 5.5));
        assertThrows(IllegalArgumentException.class, () -> new Produto("   ", 5.5));
    }

    @Test
    void rejeitaPrecoNegativoOuNaoFinito() {
        assertThrows(IllegalArgumentException.class, () -> new Produto("Suco", -0.01));
        assertThrows(IllegalArgumentException.class, () -> new Produto("Suco", Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> new Produto("Suco", Double.POSITIVE_INFINITY));
    }

    @Test
    void aceitaPrecoZero() {
        assertEquals(0.0, new Produto("Cortesia", 0).getPreco(), 1e-9);
    }
}
