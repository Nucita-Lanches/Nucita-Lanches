# Integração da interface JavaFX

## Origem e destino

- Base: `Nucita-Lanches/Nucita-Lanches`, commit `f30e1d1c86a10699b87a8db29fc2b1498f81faf6` da `main`.
- Interface: `Nucita-Lanches/Teste-JavaFX`, commit `2758cd125dd047922e9cd9749ecd97856b16ec11` da `main`, arquivos de `outputs/`.
- Branch de trabalho no principal: `feat/unificar-javafx-java`.

Foram incorporados o controller da interface, os ícones vetoriais, o FXML, o CSS, a imagem da marca e o teste da interface. Os pacotes e caminhos de recursos foram adaptados ao projeto principal.

## Uma única implementação das regras

Os arquivos existentes em `application/`, `domain/`, `storage/` e `console/` foram preservados. Os testes originais também foram mantidos.

A integração não traz as cópias de classes de negócio que existiam no projeto JavaFX. O controller usa diretamente os objetos e métodos do principal. Não foram incluídas operações de exclusão, edição, autenticação, pagamento, finalização ou persistência que não existam no principal.

O filtro de nome, a seleção da mesa para o próximo pedido e a seleção do pedido a exibir são estados da apresentação; não modificam as regras ou a associação dos pedidos existentes.

## Correspondência das ações

| Ação na tela | Operação existente |
| --- | --- |
| Cadastrar produto | `Restaurante.cadastroProduto(new Produto(...))` |
| Mostrar cardápio | `Restaurante.listarProdutos()` |
| Cadastrar mesa | `Restaurante.cadastrarMesa(new Mesa(...))` |
| Consultar mesa | `Restaurante.consultarMesaPorNumero(...)` |
| Listar mesas | `Restaurante.listarMesas()` |
| Novo pedido | `Restaurante.criarPedido(...)` |
| Adicionar produto e quantidade | `Restaurante.adicionarItemAoPedido(...)` |
| Consultar pedidos e itens | `BancoDeDados.listarPedidos()` e `Pedido.getItens()` |

O preço digitado é convertido para o tipo usado por `Produto`. A validação de valor finito/não negativo continua no modelo. A restrição de duas casas decimais da interface anterior não foi incorporada; a tela apenas formata valores monetários para exibição.

## Ajustes de execução

`HelloApplication`, já configurada no Maven, abre o FXML do atendimento. `Launcher`, o fluxo do console, `HelloController` e `hello-view.fxml` foram mantidos. `module-info.java` abre o pacote `ui` ao carregador FXML.

O compilador foi alinhado ao Java 21 utilizado pelo código da interface. O Maven Wrapper, as coordenadas do projeto, as dependências e as versões dos plugins do principal permanecem as mesmas. A execução passa a ser feita inteiramente a partir da raiz do principal.

## Verificação

Antes da integração, os 13 testes originais passaram. Depois da integração, a validação inclui esses mesmos testes e o fluxo completo da interface oficial. O comparativo com a base verifica que os arquivos de negócio, console e testes originais não sofreram alterações.

Comandos a partir da raiz, no Windows:

```powershell
.\mvnw.cmd test
.\mvnw.cmd javafx:run
```

O teste JavaFX requer sessão gráfica. O armazenamento continua em memória e todos os pedidos começam abertos, conforme o código original. As operações ainda planejadas não foram adicionadas à interface.
