# Arquitetura atual

O projeto é uma aplicação Java modular empacotada pelo Maven. Há duas entradas independentes no código: o plugin JavaFX inicia `HelloApplication`; `console.Main` oferece um menu de texto para operações de produtos e mesas. A interface JavaFX ainda é uma tela de exemplo e não usa o domínio do restaurante.

## Pacotes Java

| Pacote | Responsabilidade atual |
| --- | --- |
| `...domain` | Modelos `Produto`, `Mesa`, `Pedido` e `ItemPedido`. |
| `...application` | `Restaurante`, fachada que encaminha operações de produto e mesa ao armazenamento. |
| `...storage` | `BancoDeDados`, singleton que mantém produtos, mesas, pedidos e itens em coleções em memória. Não há persistência em disco ou banco externo. |
| `...console` | `Main`, menu de texto para cadastro/listagem de produtos e cadastro/consulta/listagem de mesas. |
| `com.senai.nucitalanches.nucitalanches` | Inicialização e controller JavaFX. O FXML permanece em `src/main/resources` no caminho correspondente ao pacote. |

O prefixo completo dos quatro primeiros pacotes é `com.senai.nucitalanches.nucitalanches`.

## Estado do domínio

`Produto` valida nome não nulo/não vazio e preço finito não negativo. `Mesa` é apenas um número; a validação de número positivo e unicidade ocorre no armazenamento durante o cadastro. Os métodos do menu exibem mensagens de sucesso/erro conforme esse resultado.

`Pedido` contém somente um número e uma referência a um `ItemPedido`; `ItemPedido` contém uma referência a um produto e uma quantidade. Ainda não há associação entre pedido e mesa, coleção de itens por pedido, subtotal, total, remoção de item ou estado aberto/finalizado. `BancoDeDados` mantém coleções para pedidos e itens, mas o menu e `Restaurante` não oferecem operações para eles.

## Caminhos relevantes

```text
src/main/java/module-info.java
src/main/java/com/senai/nucitalanches/nucitalanches/
├── application/Restaurante.java
├── console/Main.java
├── domain/{Produto,Mesa,Pedido,ItemPedido}.java
├── storage/BancoDeDados.java
├── HelloApplication.java
├── HelloController.java
└── Launcher.java
src/main/resources/com/senai/nucitalanches/nucitalanches/hello-view.fxml
```

Os caminhos de recursos são mantidos separados da organização do domínio. A configuração de execução JavaFX no Maven e as declarações de módulo continuam apontando para o pacote JavaFX existente.
