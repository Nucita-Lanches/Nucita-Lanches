package com.senai.nucitalanches.nucitalanches.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MesaTest {
    private static final int NUMERO_MESA = 987654;

    @Test
    void cadastraEConsultaMesaPorNumero() {
        Restaurante restaurante = new Restaurante();
        Mesa mesa = new Mesa(NUMERO_MESA);

        assertTrue(restaurante.cadastrarMesa(mesa));
        assertSame(mesa, restaurante.consultarMesaPorNumero(NUMERO_MESA));
        assertTrue(restaurante.listarMesas().contains(mesa));
    }

    @Test
    void rejeitaCadastroDeNumeroJaExistenteSemDuplicarMesa() {
        Restaurante restaurante = new Restaurante();
        Mesa mesaExistente = new Mesa(NUMERO_MESA + 1);

        assertTrue(restaurante.cadastrarMesa(mesaExistente));
        assertFalse(restaurante.cadastrarMesa(new Mesa(NUMERO_MESA + 1)));

        assertSame(mesaExistente, restaurante.consultarMesaPorNumero(NUMERO_MESA + 1));
        assertEquals(1, restaurante.listarMesas().stream()
                .filter(mesa -> mesa.getNumero().equals(NUMERO_MESA + 1))
                .count());
    }
}