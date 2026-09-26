# Pipeline de desenvolvimento

1. **Planejar:** escolha um card do Trello e confirme o critério de conclusão.
2. **Branch:** crie uma branch a partir de `main` com nome descritivo.
3. **Implementar:** faça a menor alteração necessária e mantenha o escopo da tarefa.
4. **Testar:** valide as regras de negócio e execute os testes existentes.
5. **Pull Request:** descreva o que mudou, como testar e vincule o card do Trello.
6. **Revisar:** pelo menos um integrante deve analisar o código e aprovar ou solicitar mudanças.
7. **Corrigir:** responda aos comentários na mesma branch e atualize o PR.
8. **Merge:** somente após aprovação e resolução dos comentários; o destino é `main`.
9. **Encerrar:** mova o card para **Concluído** e registre qualquer pendência.

## Critérios mínimos antes do merge

- Não há conflito com `main`.
- O código compila/executa.
- As regras de subtotal, total, remoção e finalização foram verificadas.
- O PR tem pelo menos uma aprovação.
- Não existem comentários pendentes.
