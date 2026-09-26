# Backlog de Issues — Nucita Lanches

Este arquivo transforma o trabalho acadêmico em tarefas que podem ser copiadas para o GitHub Issues. Cada issue deve representar uma entrega pequena, verificável e relacionada a um cartão do Trello.

## Épicos

- `EPIC-01` — Análise e modelagem
- `EPIC-02` — Produtos e mesas
- `EPIC-03` — Pedidos
- `EPIC-04` — Regras e validações
- `EPIC-05` — Testes e documentação

## Labels sugeridas

`epic`, `feature`, `modelagem`, `backend`, `validation`, `test`, `documentation`, `bug`, `priority: high`, `priority: medium`, `priority: low`, `blocked`.

---

## ISSUE-01 — Validar requisitos e regras de negócio

**Tipo:** `documentation`  
**Épico:** `EPIC-01`  
**Prioridade:** Alta  
**Trello:** cartão “Validar requisitos e regras de negócio”

### Objetivo

Confirmar com o grupo o escopo real do sistema antes de criar os diagramas e implementar novas funcionalidades.

### Critérios de aceite

- [ ] Requisitos funcionais revisados.
- [ ] Requisitos não funcionais definidos.
- [ ] Regras confirmadas ou marcadas como decisão pendente.
- [ ] Funcionalidades fora do escopo registradas.

### Dependências

Nenhuma.

---

## ISSUE-02 — Criar Diagrama de Entidade-Relacionamento

**Tipo:** `modelagem`  
**Épico:** `EPIC-01`  
**Prioridade:** Alta  
**Trello:** cartão “Criar DER”

### Objetivo

Representar os dados necessários para produtos, mesas, pedidos e itens de pedido.

### Critérios de aceite

- [ ] Entidades e atributos estão definidos.
- [ ] Chaves e relacionamentos estão indicados.
- [ ] Cardinalidades estão explicadas.
- [ ] O DER é coerente com as regras de negócio.

### Dependências

`ISSUE-01`.

---

## ISSUE-03 — Criar Diagrama de Classes

**Tipo:** `modelagem`  
**Épico:** `EPIC-01`  
**Prioridade:** Alta  
**Trello:** cartão “Criar Diagrama de Classes”

### Objetivo

Representar classes, responsabilidades, atributos, métodos e relacionamentos da aplicação.

### Critérios de aceite

- [ ] As classes de domínio estão representadas.
- [ ] As responsabilidades não estão concentradas indevidamente em uma classe.
- [ ] Os métodos principais estão descritos.
- [ ] Encapsulamento e multiplicidades estão indicados.

### Dependências

`ISSUE-01`.

---

## ISSUE-04 — Criar Diagrama de Casos de Uso

**Tipo:** `modelagem`  
**Épico:** `EPIC-01`  
**Prioridade:** Alta  
**Trello:** cartão “Criar Casos de Uso”

### Objetivo

Representar atores e funcionalidades reais do sistema.

### Critérios de aceite

- [ ] Atores estão identificados.
- [ ] Casos de uso correspondem às funcionalidades planejadas.
- [ ] O limite do sistema está representado.
- [ ] Relações entre casos de uso foram usadas somente quando necessárias.

### Dependências

`ISSUE-01`.

---

## ISSUE-05 — Definir cadastro e consulta de produtos

**Tipo:** `feature`  
**Épico:** `EPIC-02`  
**Prioridade:** Alta  
**Trello:** cartão “Produtos do cardápio”

### Objetivo

Definir o comportamento necessário para cadastrar e consultar produtos do cardápio.

### Critérios de aceite

- [ ] Produto possui nome e preço.
- [ ] Produto pode ser consultado.
- [ ] Nome vazio é rejeitado.
- [ ] Preço menor ou igual a zero é rejeitado.
- [ ] A disponibilidade do produto está definida.

### Dependências

`ISSUE-01`, `ISSUE-03`.

---

## ISSUE-06 — Definir cadastro e consulta de mesas

**Tipo:** `feature`  
**Épico:** `EPIC-02`  
**Prioridade:** Alta  
**Trello:** cartão “Mesas do restaurante”

### Objetivo

Definir como mesas serão cadastradas, consultadas e relacionadas aos pedidos.

### Critérios de aceite

- [ ] Mesa possui identificação única.
- [ ] Mesa pode ser consultada.
- [ ] Não é possível cadastrar duas mesas com a mesma identificação.
- [ ] O relacionamento entre mesa e pedido está definido.

### Dependências

`ISSUE-01`, `ISSUE-03`.

---

## ISSUE-07 — Abrir pedido para uma mesa

**Tipo:** `feature`  
**Épico:** `EPIC-03`  
**Prioridade:** Alta  
**Trello:** cartão “Abrir pedido”

### Objetivo

Permitir iniciar um pedido vinculado a uma mesa existente.

### Critérios de aceite

- [ ] Pedido possui identificação.
- [ ] Pedido pertence a uma mesa.
- [ ] Pedido inicia com status aberto.
- [ ] Mesa inexistente não pode receber pedido.

### Dependências

`ISSUE-06`.

---

## ISSUE-08 — Adicionar produtos ao pedido

**Tipo:** `feature`  
**Épico:** `EPIC-03`  
**Prioridade:** Alta  
**Trello:** cartão “Adicionar itens ao pedido”

### Objetivo

Adicionar um produto disponível ao pedido com uma quantidade válida.

### Critérios de aceite

- [ ] Produto existente pode ser adicionado.
- [ ] Produto indisponível é rejeitado.
- [ ] Quantidade deve ser maior que zero.
- [ ] Pedido finalizado não aceita novos itens.
- [ ] O item mantém o produto e a quantidade.

### Dependências

`ISSUE-05`, `ISSUE-07`.

---

## ISSUE-09 — Calcular subtotal e total do pedido

**Tipo:** `feature`  
**Épico:** `EPIC-03`  
**Prioridade:** Alta  
**Trello:** cartão “Cálculo do pedido”

### Objetivo

Implementar a regra de cálculo dos valores do pedido.

### Critérios de aceite

- [ ] Subtotal usa preço multiplicado pela quantidade.
- [ ] Total soma os subtotais dos itens.
- [ ] Pedido sem itens possui total definido conforme decisão do grupo.
- [ ] Alterar ou remover item atualiza o total.

### Dependências

`ISSUE-08`.

---

## ISSUE-10 — Alterar e remover itens do pedido

**Tipo:** `feature`  
**Épico:** `EPIC-03`  
**Prioridade:** Média  
**Trello:** cartão “Alterar itens do pedido”

### Objetivo

Permitir corrigir a quantidade ou remover itens enquanto o pedido estiver aberto.

### Critérios de aceite

- [ ] Quantidade inválida é rejeitada.
- [ ] Item inexistente não pode ser alterado ou removido.
- [ ] Pedido finalizado não pode ser alterado.
- [ ] O total é atualizado após a operação.

### Dependências

`ISSUE-08`, `ISSUE-09`.

---

## ISSUE-11 — Finalizar pedido

**Tipo:** `feature`  
**Épico:** `EPIC-03`  
**Prioridade:** Alta  
**Trello:** cartão “Finalizar pedido”

### Objetivo

Encerrar o pedido e impedir novas alterações.

### Critérios de aceite

- [ ] Pedido aberto pode ser finalizado.
- [ ] Pedido sem itens não pode ser finalizado.
- [ ] Pedido finalizado muda de status.
- [ ] Pedido finalizado rejeita inclusão, alteração e remoção.

### Dependências

`ISSUE-09`, `ISSUE-10`.

---

## ISSUE-12 — Validar situações inválidas

**Tipo:** `validation`  
**Épico:** `EPIC-04`  
**Prioridade:** Alta  
**Trello:** cartão “Validações e erros”

### Objetivo

Centralizar e documentar o comportamento esperado para entradas inválidas.

### Critérios de aceite

- [ ] Erros possuem mensagens compreensíveis.
- [ ] Produto inexistente é tratado.
- [ ] Mesa inexistente é tratada.
- [ ] Pedido finalizado é protegido.
- [ ] Quantidade e preço inválidos são tratados.

### Dependências

`ISSUE-05` a `ISSUE-11`.

---

## ISSUE-13 — Criar testes das regras de negócio

**Tipo:** `test`  
**Épico:** `EPIC-05`  
**Prioridade:** Média  
**Trello:** cartão “Testes”

### Objetivo

Verificar automaticamente os cálculos, alterações e bloqueios do domínio.

### Critérios de aceite

- [ ] Teste de subtotal.
- [ ] Teste de total.
- [ ] Teste de remoção de item.
- [ ] Teste de pedido finalizado.
- [ ] Teste de entradas inválidas.

### Dependências

`ISSUE-05` a `ISSUE-12`.

---

## ISSUE-14 — Documentar execução e entrega

**Tipo:** `documentation`  
**Épico:** `EPIC-05`  
**Prioridade:** Alta  
**Trello:** cartão “README e entrega final”

### Objetivo

Documentar o sistema, sua execução, funcionalidades, diagramas e decisões de modelagem.

### Critérios de aceite

- [ ] README descreve o objetivo.
- [ ] README lista tecnologias.
- [ ] README explica como executar.
- [ ] Diagramas estão organizados.
- [ ] Funcionalidades implementadas estão listadas.
- [ ] Documentação não descreve funcionalidades inexistentes.

### Dependências

`ISSUE-01` a `ISSUE-13`.

## Ordem recomendada

`ISSUE-01` → `ISSUE-02`, `ISSUE-03`, `ISSUE-04` → `ISSUE-05` e `ISSUE-06` → `ISSUE-07` → `ISSUE-08` → `ISSUE-09` → `ISSUE-10` → `ISSUE-11` → `ISSUE-12` e `ISSUE-13` → `ISSUE-14`.
