
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
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Insira o nome do produto");
                    restaurante.cadastroProduto(new Produto(scanner.nextLine()));
                    break;
            }
        }while (opcao != 0);


    }
}
