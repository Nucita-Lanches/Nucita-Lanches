import java.util.ArrayList;

public class Mesa {
   private ArrayList<Pedido> pedidos;
    private Integer numero;

    public Mesa(ArrayList<Pedido> pedidos, Integer numero) {
        this.pedidos = pedidos;
        this.numero = numero;
    }
}
