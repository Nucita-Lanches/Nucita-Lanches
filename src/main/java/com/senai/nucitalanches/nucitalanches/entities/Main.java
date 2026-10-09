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
            System.out.println("Digite [3] para cadastrar mesa");
            System.out.println("Digite [4] para consultar mesa");
            System.out.println("Digite [5] para listar mesas");
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
                case 3:
                    System.out.println("insira o número da mesa");
                    int numeroMesa = scanner.nextInt();
                    boolean mesaCadastrada = restaurante.cadastrarMesa(new Mesa(numeroMesa));
                    if (mesaCadastrada) {
                        System.out.println("Mesa cadastrada");
                    }
                    break;
                case 4:
                    System.out.println("número da mesa que deseja consultar");
                    int numeroConsulta = scanner.nextInt();
                    Mesa mesaEncontrada = restaurante.consultarMesaPorNumero(numeroConsulta);
                    if (mesaEncontrada == null) {
                        System.out.println("Mesa não encontrada.");
                    } else {
                        System.out.println(mesaEncontrada);
                    }
                    break;
                case 5:
                    System.out.println("--- Lista de Mesas ---");
                    for (Mesa mesa : restaurante.listarMesas()) {
                        System.out.println(mesa);
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção n existe");
                    break;
            }
        } while (opcao != 0);

        scanner.close();
    }
}