package com.senai.nucitalanches.nucitalanches.entities;

public class Mesa {
    private final Integer numero;

    public Mesa(Integer numero) {
        this.numero = numero;
    }

    public Integer getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return "Mesa{" +
                "numero=" + numero +
                '}';
    }
}