package com.senai.nucitalanches.nucitalanches.entities;

public class Produto {
    private Long id;
    private String nome;
    private double preco;

    public Produto(String nome, double preco) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio ou nulo.");
        }
        if (preco < 0) {
            throw new IllegalArgumentException("Preço do produto não pode ser negativo.");
        }
        this.nome = nome.trim();
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio ou nulo.");
        }
        this.nome = nome.trim();
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (preco < 0) {
            throw new IllegalArgumentException("Preço do produto não pode ser negativo.");
        }
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                '}';
    }
}