# Contexto do projeto — Restaurante

O sistema substitui o controle manual de pedidos realizados nas mesas de um restaurante.

## Classes principais

- **Produto:** representa algo disponível para consumo e possui nome e preço.
- **Mesa:** representa uma mesa do restaurante.
- **Pedido:** pertence a uma mesa, contém itens e possui estado aberto ou finalizado.
- **ItemPedido:** associa um produto a uma quantidade dentro de um pedido.

## Regras de negócio

- Uma mesa pode possuir vários pedidos ao longo do tempo.
- Cada pedido pertence a uma mesa.
- Um pedido possui vários itens.
- Cada item identifica um produto e sua quantidade.
- O subtotal do item é `preço do produto × quantidade`.
- O total do pedido é a soma dos subtotais de todos os itens.
- Remover um item deve atualizar o total do pedido.
- Um pedido finalizado não deve aceitar alterações de um pedido aberto.

## Funcionalidades

Cadastro de produtos e mesas; abertura e associação de pedidos a mesas; inclusão, consulta e remoção de produtos; definição de quantidades; cálculo de subtotais e total; finalização do pedido.
