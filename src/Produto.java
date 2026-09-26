import java.util.Scanner;

public class Produto {
    private String nome;
    private Double preco;
    Scanner resposta = new Scanner(System.in);

    public Produto(String nome, Double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public void cadastrarProduto(String nome, Double preco){
        System.out.println("Qual o nome do produto que você quer cadastrar?");
        nome = resposta.nextLine();
        if(resposta == null){
            System.out.println("Digite um nome!");
            return;
        }
        System.out.println("Qual é o preço desse produto?");
        preco = resposta.nextDouble();
        if (resposta == null){
            System.out.println("Digite um preço!");
            return;
        }

    }
}
