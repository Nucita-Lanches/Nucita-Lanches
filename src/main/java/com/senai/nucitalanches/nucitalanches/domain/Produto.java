package com.senai.nucitalanches.nucitalanches.domain;

public class Produto {
    private Long id;
    private String nome;
    private double preco;
    private String imagemUrl;

    public Produto(String nome, double preco) {
        this(nome, preco, null);
    }

    public Produto(String nome, double preco, String imagemUrl) {
        setNome(nome);
        setPreco(preco);
        setImagemUrl(imagemUrl);
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
        validarNome(nome);
        this.nome = nome.trim();
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        validarPreco(preco);
        this.preco = preco;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = (imagemUrl == null || imagemUrl.trim().isEmpty()) ? null : imagemUrl.trim();
    }

    public boolean possuiImagem() {
        return imagemUrl != null && !imagemUrl.isEmpty();
    }

    private static void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio ou nulo.");
        }
    }

    private static void validarPreco(double preco) {
        if (!Double.isFinite(preco) || preco < 0) {
            throw new IllegalArgumentException("Preço deve ser um valor finito e não negativo.");
        }
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", imagemUrl='" + imagemUrl + '\'' +
                '}';
    }
}