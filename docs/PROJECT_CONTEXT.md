# Contexto do projeto

Nucita Lanches é um projeto de sistema para apoiar o atendimento de uma lanchonete e substituir o controle manual de pedidos. Esta página separa o que o código já oferece das regras que ainda fazem parte do planejamento. A estrutura atual está descrita em [`ARQUITETURA.md`](ARQUITETURA.md).

## Conceitos do domínio

- **Produto:** item disponível para consumo, com nome e preço.
- **Mesa:** identifica uma mesa do restaurante.
- **Pedido:** pertence a uma mesa cadastrada, reúne itens e começa aberto.
- **ItemPedido:** associa um produto a uma quantidade dentro do pedido.

## Implementado

- Cadastrar e listar produtos com nome e preço, usando as validações de `Produto`.
- Associar uma imagem local a um produto pela interface JavaFX.
- Cadastrar mesas com número positivo e único.
- Manter vários pedidos associados à mesma mesa cadastrada.
- Criar pedidos para mesas cadastradas e atribuir número ao pedido.
- Adicionar ao pedido existente itens com produto não nulo e quantidade positiva.
- Manter os itens dentro do pedido e expor uma cópia somente para leitura da lista.
- Somar as quantidades do mesmo produto no pedido, preservando o preço da primeira inclusão.
- Calcular subtotal (`preço unitário guardado × quantidade`) e total pela soma dos subtotais, com `double`.
- Alterar quantidade positiva e remover item, atualizando os valores consultados.
- Finalizar pedidos que contenham itens e impedir novas inclusões, alterações ou remoções.
- Rejeitar produtos não cadastrados; todos os produtos do catálogo são considerados disponíveis.

A interface JavaFX utiliza essas operações para o atendimento, e o menu de console original continua disponível. O histórico e o estado atual da integração estão em [`INTEGRACAO-JAVAFX.md`](INTEGRACAO-JAVAFX.md); as regras aprovadas para valores e itens estão em [`ISSUE-04.md`](ISSUE-04.md).

## Limitações e fora do escopo atual

- O catálogo, os pedidos e as referências a imagens permanecem em memória e são apagados ao encerrar a aplicação. Os arquivos de imagem escolhidos pelo usuário não são copiados para o projeto.
