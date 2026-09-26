# Pipeline de desenvolvimento

Este fluxo conecta backlog, GitHub, Trello e Pull Requests.

1. **Planejar:** escolha uma issue do `docs/BACKLOG.md` e confirme seu critério de aceite.
2. **Mover no Trello:** mova o cartão de **Backlog** para **A fazer**.
3. **Criar branch:** parta da branch de desenvolvimento definida pelo grupo e use um nome relacionado à issue.
4. **Executar:** implemente somente o escopo da issue.
5. **Testar:** verifique os critérios de aceite e as regras de negócio afetadas.
6. **Abrir PR:** descreva o problema, a solução, como testar e vincule a issue.
7. **Revisar:** outro integrante deve revisar o PR.
8. **Corrigir:** responda aos comentários na mesma branch.
9. **Integrar:** faça merge somente após aprovação e resolução dos comentários.
10. **Encerrar:** mova o cartão para **Concluído** e marque a issue como concluída.

## Estados do Trello

- **Backlog:** tarefa ainda não priorizada.
- **A fazer:** tarefa priorizada e pronta para começar.
- **Em desenvolvimento:** alguém está trabalhando nela.
- **Em revisão:** existe PR aguardando revisão.
- **Bloqueado:** depende de decisão, informação ou outra tarefa.
- **Concluído:** critérios de aceite atendidos e PR integrado.

## Critérios mínimos antes do merge

- A issue está vinculada ao PR.
- O cartão do Trello está vinculado à issue.
- O código compila ou executa.
- As regras afetadas foram verificadas.
- O PR possui revisão de outro integrante.
- Não existem comentários pendentes.
- A documentação foi atualizada quando necessário.

## Critérios mínimos antes do merge

- Não há conflito com `main`.
- O código compila/executa.
- As regras de subtotal, total, remoção e finalização foram verificadas.
- O PR tem pelo menos uma aprovação.
- Não existem comentários pendentes.
