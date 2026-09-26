# Mapa entre GitHub Issues e Trello

Este arquivo deve ser usado até que as Issues sejam criadas diretamente no GitHub. Depois da criação, substitua “a criar” pelo número e pelo link real da Issue.

## Listas do Trello

1. **Backlog** — ideias e tarefas ainda não priorizadas.
2. **A fazer** — tarefas escolhidas para execução.
3. **Em desenvolvimento** — tarefa em uma branch.
4. **Em revisão** — tarefa com Pull Request aberto.
5. **Bloqueado** — tarefa aguardando decisão ou dependência.
6. **Concluído** — Issue encerrada e PR integrado.

## Cartões iniciais

| Cartão do Trello | Issue | Prioridade | Dependência |
|---|---|---:|---|
| Validar requisitos e regras de negócio | [#1](https://github.com/alwisgabriel/Nucita-Lanches/issues/1) | Alta | Nenhuma |
| Criar DER | [#2](https://github.com/alwisgabriel/Nucita-Lanches/issues/2) | Alta | `ISSUE-01` |
| Criar Diagrama de Classes | [#3](https://github.com/alwisgabriel/Nucita-Lanches/issues/3) | Alta | `ISSUE-01` |
| Criar Casos de Uso | [#4](https://github.com/alwisgabriel/Nucita-Lanches/issues/4) | Alta | `ISSUE-01` |
| Produtos do cardápio | [#5](https://github.com/alwisgabriel/Nucita-Lanches/issues/5) | Alta | `ISSUE-01`, `ISSUE-03` |
| Mesas do restaurante | [#6](https://github.com/alwisgabriel/Nucita-Lanches/issues/6) | Alta | `ISSUE-01`, `ISSUE-03` |
| Abrir pedido | [#7](https://github.com/alwisgabriel/Nucita-Lanches/issues/7) | Alta | `ISSUE-06` |
| Adicionar itens ao pedido | [#8](https://github.com/alwisgabriel/Nucita-Lanches/issues/8) | Alta | `ISSUE-05`, `ISSUE-07` |
| Cálculo do pedido | [#9](https://github.com/alwisgabriel/Nucita-Lanches/issues/9) | Alta | `ISSUE-08` |
| Alterar itens do pedido | [#10](https://github.com/alwisgabriel/Nucita-Lanches/issues/10) | Média | `ISSUE-08`, `ISSUE-09` |
| Finalizar pedido | [#11](https://github.com/alwisgabriel/Nucita-Lanches/issues/11) | Alta | `ISSUE-09`, `ISSUE-10` |
| Validações e erros | [#12](https://github.com/alwisgabriel/Nucita-Lanches/issues/12) | Alta | `ISSUE-05` a `ISSUE-11` |
| Testes | [#13](https://github.com/alwisgabriel/Nucita-Lanches/issues/13) | Média | `ISSUE-05` a `ISSUE-12` |
| README e entrega final | [#14](https://github.com/alwisgabriel/Nucita-Lanches/issues/14) | Alta | Todas as anteriores |

## Modelo de cartão

```markdown
## Objetivo

[Resumo da issue]

## Issue do GitHub

Número/link: substituir pelo link da Issue correspondente

## Critérios de aceite

- [ ] Critério 1
- [ ] Critério 2

## Branch

`tipo/nome-da-tarefa`

## Observações

Registrar decisões, bloqueios e links para Pull Requests.
```

## Como sincronizar

1. Criar a Issue usando o texto de `BACKLOG.md`.
2. Copiar o link da Issue para este arquivo.
3. Criar um cartão com o mesmo título no Trello.
4. Adicionar o link da Issue no cartão.
5. Mover o cartão conforme o estado da tarefa.
6. Adicionar o link do PR quando a implementação começar.
