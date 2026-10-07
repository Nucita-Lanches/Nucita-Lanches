package com.senai.nucitalanches.nucitalanches.entities;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Restaurante restaurante = new Restaurante();
        int opcao;

        do {
            System.out.println("Bem vindo ao sistema do nucita lanches");
            System.out.println("Digite [1] para cadastrar produto");
            System.out.println("Digite [2] para listar produtos");
            System.out.println("Digite [0] para sair");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Insira o nome do produto");
                    scanner.nextLine();
                    String nomeProduto = scanner.nextLine();
                    System.out.println("Insira o preço do produto");
                    double precoProduto = scanner.nextDouble();
                    restaurante.cadastroProduto(new Produto(nomeProduto, precoProduto));
                    System.out.println("Produto cadastrado com sucesso!");
                    break;
                case 2:
                    System.out.println("--- Lista de Produtos ---");
                    restaurante.listarProdutos();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);

        scanner.close();
    }
}