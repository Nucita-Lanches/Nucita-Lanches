# Issue 04 — Produtos no pedido e valores

Implementação da [issue #6](https://github.com/Nucita-Lanches/Nucita-Lanches/issues/6), na branch `feat/issue-04-valores-pedido`.

## Decisões aprovadas antes da implementação

- Escopo completo na lógica Java e na interface JavaFX existente.
- Todos os produtos cadastrados estão disponíveis; não há um campo adicional de disponibilidade.
- Valores calculados com `double`.
- Adicionar o mesmo produto soma a quantidade em um único item.
- Guardar o preço do momento da inclusão. Ao somar unidades, o item existente mantém o preço da primeira inclusão.
- Manter as camadas existentes e acrescentar os testes da issue.

## Comportamento

`Restaurante.adicionarItemAoPedido` procura o produto pelo ID no catálogo e rejeita produto nulo ou não cadastrado. A inclusão usa o produto do catálogo, impedindo que um objeto recebido com o mesmo ID substitua o preço por outro valor.

`ItemPedido` captura o preço em `precoUnitario` na criação. `getSubtotal()` retorna `precoUnitario × quantidade`. Alterar o preço do produto no catálogo não muda esse valor guardado.

`Pedido.adicionarItem` reúne o produto repetido e retorna o item efetivamente pertencente ao pedido. A soma não aceita ultrapassar o limite do tipo `Integer`, para manter uma quantidade válida. Produtos de IDs distintos continuam separados, mesmo que tenham o mesmo nome.

`Restaurante.alterarQuantidadeItem` e `removerItemDoPedido` verificam que o pedido existe e que o item pertence a ele. Quantidades nulas, zero ou negativas são rejeitadas sem alterar o estado do pedido. Remoção é uma operação explícita, separada de alterar quantidade.

`Pedido.getTotal()` soma os subtotais atuais. Não há total em cache para sincronizar após inclusão, alteração ou remoção. As operações usam `double` sem arredondar valores armazenados; o formato monetário da tela usa a apresentação já existente.

## Exemplo

1. Produto cadastrado a `5.50`, incluído com quantidade `2`: subtotal e total `11.00`.
2. O preço do catálogo muda para `20.00`; adicionar mais uma unidade soma a quantidade para `3`, com total `16.50`.
3. Alterar a quantidade desse item para `4` produz total `22.00`.
4. Remover o item deixa o pedido vazio e o total em `0.00`.
5. Uma nova inclusão após a remoção captura o preço atual `20.00`.

## Interface reutilizada

O layout, os recursos e a navegação foram mantidos. O painel de pedidos existente ganhou subtotal por item, total do pedido e ações de alterar quantidade/remover. O formulário de quantidade existente é reutilizado para editar o item. A consulta de pedidos também mostra os valores guardados e os subtotais.

Finalização, pagamento, autenticação e persistência não fazem parte desta issue.

## Validação

30 testes: 29 de domínio/aplicação e um fluxo JavaFX que abre a aplicação, inclui, soma, altera e remove itens. São verificados cálculos com tolerância para `double`, preço capturado, produto inexistente, quantidade inválida, tentativa de modificar item de outro pedido e ausência de alterações após falhas. O teste da interface também confere os totais exibidos e o layout compacto.

Executar, na raiz:

```powershell
.\mvnw.cmd test
```

O teste JavaFX exige sessão gráfica. Todos os cadastros de teste existem apenas no processo de validação.
