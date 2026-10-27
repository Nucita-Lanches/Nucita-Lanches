# Arquitetura atual

O projeto é uma aplicação Java modular, com um único `pom.xml` e o Maven Wrapper do repositório principal. O plugin JavaFX continua iniciando `HelloApplication`, agora com a interface de atendimento incorporada do repositório `Teste-JavaFX`. `console.Main` mantém o menu de texto existente.

## Pacotes Java

O prefixo dos pacotes abaixo é `com.senai.nucitalanches.nucitalanches`.

| Pacote | Responsabilidade |
| --- | --- |
| `domain` | Modelos originais `Produto`, `Mesa`, `Pedido`, `ItemPedido` e `StatusPedido`. |
| `application` | `Restaurante`, com produtos, mesas, criação de pedido, inclusão, alteração de quantidade e remoção de itens. |
| `storage` | Classe original `BancoDeDados`, singleton com produtos, mesas e pedidos em memória. |
| `console` | Menu original de cadastro/listagem de produtos e cadastro/consulta/listagem de mesas. |
| `ui` | `MenuController` e `UiIcons`: apresentação JavaFX e adaptação dos campos para chamadas às operações existentes. |
| Pacote base | `HelloApplication`, `Launcher` e o controller do exemplo original. |

## Fluxo da interface

`HelloApplication` carrega `view/cardapio.fxml`. O FXML usa `ui.MenuController`, com acesso reflexivo autorizado em `module-info.java` exclusivamente para `javafx.fxml`.

O controller mantém apenas o estado da apresentação: texto de busca, mesa selecionada para o próximo pedido e pedido exibido. Os registros são obtidos e alterados pelas classes originais.

- Produtos: `Restaurante.cadastroProduto` e `listarProdutos`.
- Mesas: `Restaurante.cadastrarMesa`, `consultarMesaPorNumero` e `listarMesas`.
- Pedidos: `Restaurante.criarPedido` e `adicionarItemAoPedido`.
- Alterações: `Restaurante.alterarQuantidadeItem` e `removerItemDoPedido`, verificando que o item pertence ao pedido.
- Valores: `ItemPedido.getPrecoUnitario`, `getSubtotal` e `Pedido.getTotal`.
- Consultas: `BancoDeDados.listarPedidos`, `Pedido.getItens` e os getters dos modelos.

Não há segunda cópia de `Restaurante`, dos modelos ou de `BancoDeDados`, nem dependência de rede entre os dois repositórios. As classes de negócio do principal são usadas diretamente.

## Regras preservadas

`Produto` valida nome não nulo/não vazio e preço finito não negativo. `Mesa` representa um número; positividade e unicidade são verificadas pelo armazenamento ao cadastrar. A interface converte o texto do campo de preço e mantém a precisão aceita pelo modelo, sem impor duas casas decimais ao cadastro.

`Pedido` continua associado a uma mesa cadastrada, começa `ABERTO` e recebe itens por meio das operações existentes. `getItens()` retorna uma cópia somente para leitura. `ItemPedido` exige produto não nulo e quantidade positiva.

Na Issue 04, `ItemPedido` passou a guardar o preço da inclusão em um `double` imutável. `getSubtotal()` multiplica esse valor pela quantidade atual. `Pedido.getTotal()` soma os subtotais sem armazenar um total em cache; alterações e remoções refletem na próxima consulta. Produtos repetidos são identificados pelo ID do catálogo e somam quantidade no item existente, mantendo seu preço.

`Restaurante` rejeita produtos não cadastrados e usa o produto do catálogo ao incluir; todos os produtos cadastrados são disponíveis. Quantidades não positivas e itens de outro pedido são rejeitados. O enum inclui `FINALIZADO`, mas a transição, o bloqueio após finalização e a persistência continuam fora das operações atuais.

## Recursos e execução

Os recursos estão sob `src/main/resources/com/senai/nucitalanches/nucitalanches/`:

- `view/cardapio.fxml`: tela de atendimento.
- `css/javafx-nucita-theme.css`: tema da interface e formulários.
- `images/logo.png`: imagem da marca usada pela interface.
- `hello-view.fxml`: exemplo original preservado.

A estrutura modular e os comandos `mvnw javafx:run` / `mvnw test` continuam na raiz. A compilação usa Java 21, compatível com a sintaxe e as APIs da interface incorporada. As versões de JavaFX, JUnit e dos plugins do principal foram mantidas.

## Testes

As regressões anteriores foram mantidas e ampliadas para os critérios da Issue 04. O cadastro de produtos foi incluído no preparo dos testes de pedidos, pois produtos inexistentes agora são rejeitados. `ui.MenuControllerIntegrationTest` abre `HelloApplication`, usa os botões e formulários reais e verifica as alterações nas mesmas coleções de `BancoDeDados`.

O teste da interface cobre cadastros, consultas, duplicidade de mesa, validações de preço/quantidade, criação e seleção de pedidos, associação de mesa e largura dos cartões. As capturas ficam em `target/ui-check/`; dados e capturas de teste não são carregados pelo programa.
