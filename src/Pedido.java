import java.util.ArrayList;

public class Pedido {
    private Integer numeroPedido;
    private ArrayList<ItemPedido> itensPedidos;

    public Pedido(Integer numeroPedido, ArrayList<ItemPedido> itensPedidos) {
        this.numeroPedido = numeroPedido;
        this.itensPedidos = itensPedidos;
    }
}
