# Contexto do projeto — Nucita Lanches

## Problema

O restaurante precisa substituir o controle manual de pedidos realizados nas mesas. O sistema deverá organizar os produtos disponíveis, as mesas e o ciclo de vida dos pedidos.

## Escopo inicial

O escopo desta entrega é um sistema simples de pedidos. Não estão confirmados neste momento pagamento, estoque, delivery, autenticação ou integração externa. Essas funcionalidades não devem ser adicionadas sem decisão do grupo.

## Classes do domínio

| Classe | Responsabilidade planejada |
|---|---|
| `Produto` | Representar um produto do cardápio, com nome, preço e disponibilidade. |
| `Mesa` | Representar uma mesa e seus pedidos. |
| `Pedido` | Controlar itens, status, subtotal, total e finalização. |
| `ItemPedido` | Relacionar um produto a uma quantidade dentro de um pedido. |

## Regras de negócio

1. Uma mesa pode possuir vários pedidos ao longo do tempo.
2. Cada pedido pertence a uma única mesa.
3. Um pedido possui um ou mais itens enquanto estiver válido.
4. Cada item identifica um produto e sua quantidade.
5. A quantidade deve ser maior que zero.
6. O preço deve ser maior que zero.
7. O subtotal do item é `preço do produto × quantidade`.
8. O total do pedido é a soma dos subtotais dos itens.
9. Remover um item deve atualizar o total.
10. Um pedido finalizado não pode aceitar inclusão, alteração ou remoção de itens.
11. Um pedido não deve ser finalizado sem itens.
12. Um produto indisponível não deve ser adicionado a novos pedidos.

As regras 5, 6, 10, 11 e 12 devem ser validadas pelo grupo antes da implementação definitiva.

## Fluxo principal

1. Cadastrar ou consultar produtos.
2. Cadastrar ou consultar mesas.
3. Abrir um pedido para uma mesa.
4. Adicionar produtos e quantidades.
5. Consultar subtotais e total.
6. Remover ou alterar itens enquanto o pedido estiver aberto.
7. Finalizar o pedido.

## Fora do escopo atual

- Pagamento;
- login e permissões;
- estoque;
- delivery;
- cupons e descontos;
- integração com WhatsApp ou aplicativos externos.
