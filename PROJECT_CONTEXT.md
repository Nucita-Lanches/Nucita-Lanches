# Contexto do projeto

Nucita Lanches é um projeto de sistema para apoiar o atendimento de uma lanchonete e substituir o controle manual de pedidos. Esta página registra o domínio desejado; a implementação disponível hoje é menor e está descrita em [`docs/ARQUITETURA.md`](docs/ARQUITETURA.md).

## Conceitos do domínio

- **Produto:** item disponível para consumo, com nome e preço.
- **Mesa:** identifica uma mesa do restaurante.
- **Pedido:** deveria pertencer a uma mesa e reunir itens.
- **ItemPedido:** associa um produto a uma quantidade dentro do pedido.

## Regras planejadas

- Uma mesa pode possuir vários pedidos ao longo do tempo.
- Cada pedido pertence a uma mesa e pode conter vários itens.
- O subtotal de um item é `preço do produto × quantidade`.
- O total é a soma dos subtotais dos itens.
- Remover itens atualiza o total.
- Um pedido finalizado não aceita alterações próprias de um pedido aberto.

Essas regras orientam o planejamento. Não indicam funcionalidades já entregues: em particular, o modelo `Pedido` atual ainda não associa mesas, listas de itens, totais ou estado de finalização.
