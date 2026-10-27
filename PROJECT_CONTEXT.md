# Contexto do projeto

Nucita Lanches é um projeto de sistema para apoiar o atendimento de uma lanchonete e substituir o controle manual de pedidos. Esta página separa o que o código já oferece das regras que ainda fazem parte do planejamento. A estrutura atual está descrita em [`docs/ARQUITETURA.md`](docs/ARQUITETURA.md).

## Conceitos do domínio

- **Produto:** item disponível para consumo, com nome e preço.
- **Mesa:** identifica uma mesa do restaurante.
- **Pedido:** pertence a uma mesa cadastrada, reúne itens e começa aberto.
- **ItemPedido:** associa um produto a uma quantidade dentro do pedido.

## Implementado

- Cadastrar e listar produtos com nome e preço, usando as validações de `Produto`.
- Cadastrar mesas com número positivo e único.
- Manter vários pedidos associados à mesma mesa cadastrada.
- Criar pedidos para mesas cadastradas e atribuir número ao pedido.
- Adicionar ao pedido existente itens com produto não nulo e quantidade positiva.
- Manter os itens dentro do pedido e expor uma cópia somente para leitura da lista.

A interface JavaFX utiliza essas mesmas operações para o atendimento, e o menu de console original continua disponível. A integração preserva as classes de negócio e o armazenamento do principal; os detalhes estão em [`docs/INTEGRACAO-JAVAFX.md`](docs/INTEGRACAO-JAVAFX.md).

## Planejado

- O subtotal de um item é `preço do produto × quantidade`.
- O total é a soma dos subtotais dos itens.
- Remover itens atualiza o total.
- Um pedido finalizado não aceita alterações próprias de um pedido aberto.

O modelo contém o status `FINALIZADO`, mas ainda não há operação para alterar o status nem regra que bloqueie alterações após a finalização. Os dados continuam apenas em memória.
