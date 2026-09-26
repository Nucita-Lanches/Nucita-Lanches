import java.util.ArrayList;

public class ItemPedido {
    private ArrayList<Produto> produtos;
    private Integer quantidade;

    public ItemPedido(ArrayList<Produto> produtos, Integer quantidade) {
        this.produtos = produtos;
        this.quantidade = quantidade;
    }
}
