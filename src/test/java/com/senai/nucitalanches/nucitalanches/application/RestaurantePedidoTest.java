package com.senai.nucitalanches.nucitalanches.application;

import com.senai.nucitalanches.nucitalanches.domain.ItemPedido;
import com.senai.nucitalanches.nucitalanches.domain.Mesa;
import com.senai.nucitalanches.nucitalanches.domain.Pedido;
import com.senai.nucitalanches.nucitalanches.domain.Produto;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Testa a criação de pedidos e a inclusão de itens pela aplicação. */
class RestaurantePedidoTest {
    private static final AtomicInteger PROXIMO_NUMERO_MESA = new AtomicInteger(1_000_000_000);

    private final Restaurante restaurante = new Restaurante();

    /** Cria uma mesa de teste com número ainda não usado. */
    private int cadastrarMesa() {
        int numero = PROXIMO_NUMERO_MESA.getAndIncrement();
        assertTrue(restaurante.cadastrarMesa(new Mesa(numero)));
        return numero;
    }

    private Produto cadastrarProduto(double preco) {
        Produto produto = new Produto("Suco", preco);
        restaurante.cadastroProduto(produto);
        return produto;
    }

    @Test
    void rejeitaProdutoNaoCadastradoOuNulo() {
        Pedido pedido = restaurante.criarPedido(cadastrarMesa());
        Produto inexistente = new Produto("Suco", 1); inexistente.setId(Long.MAX_VALUE);
        for (Produto produto : new Produto[] { null, new Produto("Sem cadastro", 1), inexistente }) {
            assertThrows(IllegalArgumentException.class, () -> restaurante.adicionarItemAoPedido(pedido.getNumeroPedido(), produto, 1));
        }
        assertTrue(pedido.getItens().isEmpty());
        assertEquals(0.0, pedido.getTotal(), 1e-9);
    }

    @Test
    void usaProdutoDoCatalogoQuandoRecebeCopiaComMesmoId() {
        Pedido pedido = restaurante.criarPedido(cadastrarMesa());
        Produto cadastrado = cadastrarProduto(5.5);
        Produto copia = new Produto("Valor diferente", 0); copia.setId(cadastrado.getId());
        ItemPedido item = restaurante.adicionarItemAoPedido(pedido.getNumeroPedido(), copia, 2);
        assertSame(cadastrado, item.getProduto());
        assertEquals(11.0, pedido.getTotal(), 1e-9);
    }

    @Test
    void somaProdutoRepetidoPreservaPrecoEPermiteAlterarERemover() {
        Pedido pedido = restaurante.criarPedido(cadastrarMesa());
        Produto produto = cadastrarProduto(5.5);
        ItemPedido item = restaurante.adicionarItemAoPedido(pedido.getNumeroPedido(), produto, 2);
        produto.setPreco(20);
        assertSame(item, restaurante.adicionarItemAoPedido(pedido.getNumeroPedido(), produto, 3));
        assertEquals(1, pedido.getItens().size());
        assertEquals(27.5, pedido.getTotal(), 1e-9);
        restaurante.alterarQuantidadeItem(pedido.getNumeroPedido(), item, 4);
        assertEquals(22.0, pedido.getTotal(), 1e-9);
        restaurante.removerItemDoPedido(pedido.getNumeroPedido(), item);
        assertEquals(0.0, pedido.getTotal(), 1e-9);
        ItemPedido novaInclusao = restaurante.adicionarItemAoPedido(pedido.getNumeroPedido(), produto, 1);
        assertEquals(20.0, novaInclusao.getPrecoUnitario(), 1e-9);
    }

    @Test
    void quantidadeInvalidaNaoAlteraPedidoNemTotal() {
        Pedido pedido = restaurante.criarPedido(cadastrarMesa());
        Produto produto = cadastrarProduto(5.5);
        ItemPedido item = restaurante.adicionarItemAoPedido(pedido.getNumeroPedido(), produto, 2);
        for (Integer quantidade : new Integer[] { null, 0, -1 }) {
            assertThrows(IllegalArgumentException.class, () -> restaurante.adicionarItemAoPedido(pedido.getNumeroPedido(), produto, quantidade));
            assertThrows(IllegalArgumentException.class, () -> restaurante.alterarQuantidadeItem(pedido.getNumeroPedido(), item, quantidade));
            assertEquals(2, item.getQuantidade());
            assertEquals(11.0, pedido.getTotal(), 1e-9);
        }
    }

    @Test
    void rejeitaAlteracaoERemocaoDeOutroPedido() {
        Pedido primeiro = restaurante.criarPedido(cadastrarMesa());
        Pedido segundo = restaurante.criarPedido(cadastrarMesa());
        ItemPedido item = restaurante.adicionarItemAoPedido(primeiro.getNumeroPedido(), cadastrarProduto(5.5), 2);
        assertThrows(IllegalArgumentException.class, () -> restaurante.alterarQuantidadeItem(segundo.getNumeroPedido(), item, 3));
        assertThrows(IllegalArgumentException.class, () -> restaurante.removerItemDoPedido(segundo.getNumeroPedido(), item));
        assertEquals(11.0, primeiro.getTotal(), 1e-9);
        assertEquals(0.0, segundo.getTotal(), 1e-9);
    }

    @Test
    void rejeitaAlteracaoERemocaoDePedidoInexistente() {
        ItemPedido item = new ItemPedido(new Produto("Suco", 5.5), 1);
        assertThrows(IllegalArgumentException.class, () -> restaurante.alterarQuantidadeItem(Integer.MAX_VALUE, item, 2));
        assertThrows(IllegalArgumentException.class, () -> restaurante.removerItemDoPedido(Integer.MAX_VALUE, item));
    }

    /** Cria pedido associado à mesa cadastrada e recebe um número do armazenamento. */
    @Test
    void criaPedidoParaMesaCadastrada() {
        int numeroMesa = cadastrarMesa();

        Pedido pedido = restaurante.criarPedido(numeroMesa);

        assertSame(restaurante.consultarMesaPorNumero(numeroMesa), pedido.getMesa());
        assertTrue(pedido.getNumeroPedido() > 0);
    }

    /** Recusa pedido quando o número informado não corresponde a uma mesa cadastrada. */
    @Test
    void rejeitaCriacaoParaMesaInexistente() {
        assertThrows(IllegalArgumentException.class, () -> restaurante.criarPedido(Integer.MAX_VALUE));
    }

    /** Inclui no pedido existente o produto e a quantidade informados. */
    @Test
    void adicionaItemAoPedidoExistente() {
        Pedido pedido = restaurante.criarPedido(cadastrarMesa());
        Produto produto = cadastrarProduto(5.5);

        ItemPedido item = restaurante.adicionarItemAoPedido(pedido.getNumeroPedido(), produto, 2);

        assertSame(produto, pedido.getItens().get(0).getProduto());
        assertSame(item, pedido.getItens().get(0));
        assertEquals(2, pedido.getItens().get(0).getQuantidade());
    }

    /** Recusa inclusão quando o número informado não corresponde a um pedido cadastrado. */
    @Test
    void rejeitaInclusaoEmPedidoInexistente() {
        assertThrows(IllegalArgumentException.class, () ->
                restaurante.adicionarItemAoPedido(Integer.MAX_VALUE, new Produto("Suco", 5.5), 1));
    }
}
