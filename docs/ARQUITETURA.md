# Arquitetura atual

O projeto é uma aplicação Java modular empacotada pelo Maven. Há duas entradas independentes no código: o plugin JavaFX inicia `HelloApplication`; `console.Main` oferece um menu de texto para operações de produtos e mesas. A interface JavaFX ainda é uma tela de exemplo e não usa o domínio do restaurante.

## Pacotes Java

| Pacote | Responsabilidade atual |
| --- | --- |
| `...domain` | Modelos `Produto`, `Mesa`, `Pedido`, `ItemPedido` e o enum `StatusPedido`. |
| `...application` | `Restaurante`, operações para produtos, mesas e pedidos; cria pedido para mesa cadastrada e adiciona itens a pedido existente. |
| `...storage` | `BancoDeDados`, singleton que mantém produtos, mesas e pedidos em coleções em memória. Os itens pertencem ao pedido; não há persistência em disco ou banco externo. |
| `...console` | `Main`, menu de texto para cadastro/listagem de produtos e cadastro/consulta/listagem de mesas. O menu ainda não oferece operações de pedido. |
| `com.senai.nucitalanches.nucitalanches` | Inicialização e controller JavaFX. O FXML permanece em `src/main/resources` no caminho correspondente ao pacote. |

O prefixo completo dos quatro primeiros pacotes é `com.senai.nucitalanches.nucitalanches`.

## Estado do domínio

`Produto` valida nome não nulo/não vazio e preço finito não negativo. `Mesa` é apenas um número; a validação de número positivo e unicidade ocorre no armazenamento durante o cadastro. Os métodos do menu exibem mensagens de sucesso/erro conforme esse resultado.

`Pedido` contém número, mesa, lista de itens e status. É criado para uma mesa cadastrada por meio de `Restaurante`, começa como `ABERTO` e pode receber vários itens. `getItens()` devolve uma cópia somente para leitura. `ItemPedido` exige produto não nulo e quantidade positiva, inclusive quando esses valores são alterados pelos setters.

O enum `StatusPedido` define `ABERTO` e `FINALIZADO`, mas a transição entre estados ainda não está implementada. Também não há subtotal, total, remoção de item ou bloqueio de alterações após finalização. O menu de console e a tela JavaFX não oferecem o fluxo de pedidos; as operações estão disponíveis na camada `Restaurante`.

## Caminhos relevantes

```text
src/main/java/module-info.java
src/main/java/com/senai/nucitalanches/nucitalanches/
├── application/Restaurante.java
├── console/Main.java
├── domain/{Produto,Mesa,Pedido,ItemPedido,StatusPedido}.java
├── storage/BancoDeDados.java
├── HelloApplication.java
├── HelloController.java
└── Launcher.java
src/main/resources/com/senai/nucitalanches/nucitalanches/hello-view.fxml
```

Os caminhos de recursos são mantidos separados da organização do domínio. A configuração de execução JavaFX no Maven e as declarações de módulo continuam apontando para o pacote JavaFX existente.
