package com.senai.nucitalanches.nucitalanches.entities;

import java.util.ArrayList;
import java.util.Scanner;

public class Produto {
    private String nome;
    private Integer preco;

    public Produto(String nome, Integer preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getPreco() {
        return preco;
    }

    public void setPreco(Integer preco) {
        this.preco = preco;
    }

    public void cadastroProduto() {
        Scanner entradaTexto = new Scanner(System.in);
        Scanner entradaNumero = new Scanner(System.in);
        System.out.println("Digite o nome do produto : ");
        nome = entradaTexto.nextLine();
        System.out.println("Digite o preço do produto : ");
        preco = entradaNumero.nextInt();
        if (nome == null) {
            System.err.print("Digite um nome!");
            return;
        }
        if (preco == null) {
            System.err.print("Digite um preço!");
            return;
        }
         nome = //cria array gab delicios
         preço = //cria array gab delicioso
    }

    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", preco=" + preco +
                '}';
    }

}
