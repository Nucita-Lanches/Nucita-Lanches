package com.senai.nucitalanches.nucitalanches.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Testa a associação do pedido com a mesa e o controle dos itens. */
class PedidoTest {

    @Test
    void totalDoPedidoVazioEhZero() {
        assertEquals(0.0, new Pedido(new Mesa(1)).getTotal(), 1e-9);
    }

    @Test
    void somaSubtotaisEAtualizaAposAlterarERemover() {
        Pedido pedido = new Pedido(new Mesa(1));
        ItemPedido suco = pedido.adicionarItem(new ItemPedido(new Produto("Suco", 5.5), 2));
        ItemPedido lanche = pedido.adicionarItem(new ItemPedido(new Produto("Lanche", 12), 3));
        assertEquals(47.0, pedido.getTotal(), 1e-9);
        pedido.alterarQuantidadeItem(suco, 4);
        assertEquals(58.0, pedido.getTotal(), 1e-9);
        pedido.removerItem(lanche);
        assertEquals(22.0, pedido.getTotal(), 1e-9);
        pedido.removerItem(suco);
        assertEquals(0.0, pedido.getTotal(), 1e-9);
    }

    @Test
    void produtoRepetidoSomaQuantidadeEMantemPrecoDaPrimeiraInclusao() {
        Pedido pedido = new Pedido(new Mesa(1));
        Produto produto = new Produto("Suco", 5.5);
        produto.setId(10L);
        ItemPedido original = pedido.adicionarItem(new ItemPedido(produto, 2));
        produto.setPreco(9.0);
        ItemPedido recebido = pedido.adicionarItem(new ItemPedido(produto, 3));
        assertSame(original, recebido);
        assertEquals(1, pedido.getItens().size());
        assertEquals(5, original.getQuantidade());
        assertEquals(27.5, pedido.getTotal(), 1e-9);
    }

    @Test
    void identificaProdutoRepetidoPeloIdSemConfundirProdutosSemId() {
        Pedido pedido = new Pedido(new Mesa(1));
        Produto primeiro = new Produto("Suco", 5.5); primeiro.setId(10L);
        Produto copia = new Produto("Suco", 9.0); copia.setId(10L);
        pedido.adicionarItem(new ItemPedido(primeiro, 1));
        pedido.adicionarItem(new ItemPedido(copia, 2));
        assertEquals(1, pedido.getItens().size());
        assertEquals(16.5, pedido.getTotal(), 1e-9);
        pedido.adicionarItem(new ItemPedido(new Produto("Mesmo nome", 1), 1));
        pedido.adicionarItem(new ItemPedido(new Produto("Mesmo nome", 1), 1));
        assertEquals(3, pedido.getItens().size());
    }

    @Test
    void rejeitaQuantidadeInvalidaSemAlterarQuantidadeOuTotal() {
        Pedido pedido = new Pedido(new Mesa(1));
        ItemPedido item = pedido.adicionarItem(new ItemPedido(new Produto("Suco", 5.5), 2));
        for (Integer quantidade : new Integer[] { null, 0, -1 }) {
            assertThrows(IllegalArgumentException.class, () -> pedido.alterarQuantidadeItem(item, quantidade));
            assertEquals(2, item.getQuantidade());
            assertEquals(11.0, pedido.getTotal(), 1e-9);
        }
    }

    @Test
    void rejeitaItemDeOutroPedidoOuNulo() {
        Pedido pedido = new Pedido(new Mesa(1));
        ItemPedido externo = new ItemPedido(new Produto("Suco", 5.5), 2);
        for (ItemPedido item : new ItemPedido[] { null, externo }) {
            assertThrows(IllegalArgumentException.class, () -> pedido.alterarQuantidadeItem(item, 3));
            assertThrows(IllegalArgumentException.class, () -> pedido.removerItem(item));
        }
        assertEquals(0.0, pedido.getTotal(), 1e-9);
    }

    @Test
    void rejeitaSomaQueUltrapassaIntegerSemModificarOItem() {
        Pedido pedido = new Pedido(new Mesa(1));
        Produto produto = new Produto("Suco", 1);
        ItemPedido item = pedido.adicionarItem(new ItemPedido(produto, Integer.MAX_VALUE));
        assertThrows(IllegalArgumentException.class, () -> pedido.adicionarItem(new ItemPedido(produto, 1)));
        assertEquals(Integer.MAX_VALUE, item.getQuantidade());
        assertEquals((double) Integer.MAX_VALUE, pedido.getTotal(), 1e-9);
    }

    /** Garante que o pedido começa ligado à mesa e aberto. */
    @Test
    void criaPedidoAbertoVinculadoAMesa() {
        Mesa mesa = new Mesa(1);

        Pedido pedido = new Pedido(mesa);

        assertSame(mesa, pedido.getMesa());
        assertEquals(StatusPedido.ABERTO, pedido.getStatus());
        assertEquals(0, pedido.getItens().size());
    }

    /** Garante que o modelo não aceita pedido sem mesa. */
    @Test
    void rejeitaMesaNula() {
        assertThrows(IllegalArgumentException.class, () -> new Pedido(null));
    }

    /** Confirma que o pedido guarda mais de um item na ordem de inclusão. */
    @Test
    void permiteAdicionarVariosItens() {
        Pedido pedido = new Pedido(new Mesa(1));
        ItemPedido primeiro = new ItemPedido(new Produto("Suco", 5.5), 1);
        ItemPedido segundo = new ItemPedido(new Produto("Lanche", 12.0), 2);

        pedido.adicionarItem(primeiro);
        pedido.adicionarItem(segundo);

        assertEquals(List.of(primeiro, segundo), pedido.getItens());
    }

    /** Mantém o pedido vazio quando recebe um item nulo. */
    @Test
    void rejeitaItemNulo() {
        Pedido pedido = new Pedido(new Mesa(1));

        assertThrows(IllegalArgumentException.class, () -> pedido.adicionarItem(null));
        assertEquals(0, pedido.getItens().size());
    }

    /** Impede alterações externas na lista e devolve uma cópia atualizada. */
    @Test
    void listaDeItensNaoPodeSerAlteradaPeloChamador() {
        Pedido pedido = new Pedido(new Mesa(1));
        ItemPedido item = new ItemPedido(new Produto("Suco", 5.5), 1);
        List<ItemPedido> itens = pedido.getItens();

        assertThrows(UnsupportedOperationException.class, () -> itens.add(item));

        pedido.adicionarItem(item);
        assertEquals(0, itens.size());
        assertEquals(1, pedido.getItens().size());
    }
}
