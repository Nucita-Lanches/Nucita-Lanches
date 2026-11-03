# Integração da interface JavaFX

Este documento registra a origem da interface integrada e descreve como ela funciona no estado atual do projeto. O resumo principal e os comandos de execução ficam no [README](../README.md); a estrutura das classes está em [ARQUITETURA.md](ARQUITETURA.md).

## Histórico

A integração inicial foi entregue no [PR #19](https://github.com/Nucita-Lanches/Nucita-Lanches/pull/19). A interface veio do repositório [Nucita-Lanches/Teste-JavaFX](https://github.com/Nucita-Lanches/Teste-JavaFX), commit [`2758cd1`](https://github.com/Nucita-Lanches/Teste-JavaFX/commit/2758cd125dd047922e9cd9749ecd97856b16ec11), e foi adaptada para usar as classes de negócio deste repositório.

As limitações descritas durante aquele PR são históricas. Desde então, o projeto adicionou cálculo de valores e manutenção de itens, finalização de pedidos, imagens opcionais para produtos e o vídeo de abertura. O README e a documentação de arquitetura descrevem o comportamento atual.

## Arquitetura atual

`HelloApplication` inicia a reprodução de `videos/abertura.mp4` usando JavaFX Media. O vídeo inclui áudio; a pessoa pode pulá-lo ou aguardar o término. Se ocorrer uma falha de carregamento ou reprodução, a aplicação registra o erro e abre o atendimento. Em seguida, a tela carrega `view/cardapio.fxml`, controlada por `ui.MenuController`.

O controller usa diretamente as classes de `application`, `domain` e `storage`. Não há cópias das regras de negócio dentro da interface nem dependência de rede entre os projetos. `console.Main` mantém o fluxo de console.

## Operações disponíveis

| Ação na interface | Operação usada |
| --- | --- |
| Cadastrar e listar produtos | `Restaurante.cadastroProduto` e `Restaurante.listarProdutos` |
| Associar uma imagem a um produto | Atualiza `Produto.imagemUrl` para o arquivo local selecionado |
| Cadastrar, consultar e listar mesas | `Restaurante.cadastrarMesa`, `consultarMesaPorNumero` e `listarMesas` |
| Abrir pedido para mesa cadastrada | `Restaurante.criarPedido` |
| Adicionar produto cadastrado e quantidade | `Restaurante.adicionarItemAoPedido` |
| Consultar pedidos e itens | `BancoDeDados.listarPedidos` e `Pedido.getItens` |
| Alterar quantidade ou remover item | `Restaurante.alterarQuantidadeItem` e `removerItemDoPedido` |
| Consultar subtotal e total | `ItemPedido.getSubtotal` e `Pedido.getTotal` |
| Finalizar pedido | `Restaurante.finalizarPedido` |

O modelo valida os dados e impede alterações em pedidos finalizados. As operações de valores mantêm o preço do momento da inclusão e usam `double`, conforme as decisões registradas em [ISSUE-04.md](ISSUE-04.md).

## Recursos e limites

- O vídeo e o logo são recursos empacotados no projeto. JavaFX Media reproduz o vídeo na inicialização; o volume geral do sistema continua controlando a saída de áudio.
- As imagens dos produtos são escolhidas no computador e mantidas como URI no objeto `Produto`. O projeto não copia os arquivos nem persiste o catálogo.
- Produtos, mesas e pedidos ficam em memória e são apagados ao fechar o aplicativo.
- O fluxo de console continua disponível pela classe `com.senai.nucitalanches.nucitalanches.console.Main`.

## Validação

A suíte atual tem 38 testes, incluindo um teste de integração da interface. O teste define `nucita.skipIntro` para validar os formulários sem aguardar a reprodução do vídeo. Consulte o [README](../README.md) para os comandos atualizados e requisitos da sessão gráfica.
