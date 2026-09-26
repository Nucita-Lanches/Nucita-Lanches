# Regras de contribuição

Estas regras servem para manter o projeto organizado e permitir que o grupo demonstre a relação entre planejamento, implementação e entrega acadêmica.

## Fluxo obrigatório

1. Escolha uma issue do backlog e um cartão correspondente no Trello.
2. Crie uma branch própria a partir da branch de desenvolvimento definida pelo grupo.
3. Faça alterações pequenas e relacionadas a uma única tarefa.
4. Abra um Pull Request direcionado para a branch de desenvolvimento.
5. Aguarde a análise de pelo menos um colega.
6. Resolva os comentários e só faça merge após aprovação.
7. Ao concluir, atualize a issue e mova o cartão do Trello.

Não faça alterações diretamente na `main`.

## Padrão de branches

Use nomes descritivos, por exemplo:

- `feat/cadastro-produtos`
- `feat/calculo-total-pedido`
- `fix/remocao-item-pedido`
- `docs/regras-projeto`
- `chore/planejamento-entrega`

## Commits

Use prefixos objetivos: `feat:`, `fix:`, `docs:`, `test:` e `refactor:`.

## Checklist do Pull Request

- [ ] O card do Trello está relacionado.
- [ ] A issue está relacionada no Pull Request.
- [ ] A alteração está limitada à tarefa descrita.
- [ ] O projeto executa sem erros.
- [ ] Os testes relevantes foram executados ou há justificativa.
- [ ] Pelo menos um colega revisou o código.
- [ ] Todos os comentários da revisão foram resolvidos.
