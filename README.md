# Nucita Lanches

Aplicação Java para organizar o atendimento de uma lanchonete. Este repositório reúne a **lógica do sistema e a interface JavaFX** no mesmo projeto Maven.

A interface utiliza as operações existentes de cadastro de produtos e mesas, criação de pedidos e inclusão de itens. O fluxo de console continua disponível. Os dados ficam apenas em memória durante a execução.

![Interface JavaFX integrada ao projeto principal, com dados de teste](docs/images/atendimento-javafx.png)

*A imagem usa cadastros criados no processo de teste. A aplicação inicia sem dados de demonstração.*

## Funcionalidades existentes

| Área | Operações disponíveis |
| --- | --- |
| Produtos | Cadastrar nome e preço e listar produtos. Na interface, a listagem pode ser filtrada por nome. |
| Mesas | Cadastrar um número positivo e único, consultar pelo número, listar e selecionar para atendimento. |
| Pedidos | Criar pedido para uma mesa cadastrada, com número atribuído pelo armazenamento e status inicial `ABERTO`. |
| Itens | Adicionar produtos cadastrados, somar quantidades do mesmo produto, alterar quantidade e remover itens. |
| Valores | Guardar o preço da inclusão, calcular subtotal por item e total atualizado do pedido, usando `double`. |
| Atendimento | Consultar os pedidos, selecionar um para continuar a inclusão de itens e manter vários pedidos para a mesma mesa. |

A camada JavaFX chama as classes de `application`, `domain` e `storage`. A [Issue 04](docs/ISSUE-04.md) acrescenta as operações de valores, alteração de quantidade e remoção, com as decisões aprovadas para o projeto. O armazenamento continua em memória.

**Ainda planejado:** finalização com bloqueio de alterações e persistência após fechar. O modelo possui o estado `FINALIZADO`, mas a operação de finalização ainda não existe.

## Requisitos

- **JDK 21**, usado para compilar o código da interface JavaFX incorporada.
- Maven Wrapper incluído (`mvnw` / `mvnw.cmd`) ou Maven instalado.
- Internet no primeiro carregamento das dependências.
- Sessão gráfica para abrir o JavaFX e executar o teste da interface.

As dependências do principal foram mantidas: JavaFX 21.0.6 e JUnit 5.12.1. O compilador foi alinhado ao Java 21 utilizado pela interface.

## Executar

Clone este repositório e entre na pasta que contém `pom.xml`. Se estiver consultando esta integração antes do merge, selecione a branch `feat/unificar-javafx-java`.

No Windows PowerShell:

```powershell
.\mvnw.cmd javafx:run
```

No Linux ou macOS:

```bash
bash ./mvnw javafx:run
```

No IntelliJ, abra o `pom.xml` da raiz, configure o SDK para Java 21, carregue as dependências e execute o objetivo Maven `javafx:run`.

O comando mantém a entrada `HelloApplication` e abre a interface de atendimento. Para usar o fluxo de console de produtos e mesas, execute `com.senai.nucitalanches.nucitalanches.console.Main` pela IDE.

## Usar a interface

1. Abra **Produtos**, informe nome e preço e cadastre os itens do cardápio.
2. Abra **Mesas**, cadastre ou consulte uma mesa e selecione **Usar no próximo pedido**.
3. Clique em **Novo pedido** para criar o atendimento vinculado à mesa.
4. No cardápio, clique em **Adicionar**, informe a quantidade e confirme.
5. Abra **Pedidos** e escolha **Continuar pedido** para incluir itens em um pedido já criado.

O campo de preço aceita ponto ou vírgula como separador decimal e entrega o valor ao modelo `Produto`, que valida nome e preço. Não há regra adicional de duas casas decimais no cadastro; a exibição monetária é apenas uma formatação da tela.

Adicionar novamente o mesmo produto soma sua quantidade em um único item e mantém o preço guardado na primeira inclusão. No painel existente, use **Alterar quantidade** ou **Remover**; subtotal e total são atualizados. Todos os produtos cadastrados estão disponíveis, e produtos inexistentes são rejeitados.

Ao fechar o programa, os produtos, mesas e pedidos da sessão são apagados, como no armazenamento original.

## Validar

Executar os testes:

```powershell
.\mvnw.cmd test
```

Compilar e empacotar:

```powershell
.\mvnw.cmd clean package
```

No Linux ou macOS, use `bash ./mvnw` no lugar de `.\mvnw.cmd`.

A validação da Issue 04 em **27/10/2026** cobre **30 testes**: 29 testes de domínio/aplicação e um teste de integração que abre a aplicação oficial e usa os formulários JavaFX. São verificados subtotal, total, produto repetido, preço da inclusão, alteração, remoção, rejeições sem modificar o pedido e layout em janela de 1220 × 730.

O teste JavaFX exige sessão gráfica. Para executar os testes de domínio/aplicação em ambiente sem tela:

```powershell
.\mvnw.cmd "-Dtest=RestaurantePedidoTest,PedidoTest,ItemPedidoTest" test
```

Relatórios: `target/surefire-reports/`. Capturas do teste da interface: `target/ui-check/`.

## Estrutura

```text
src/main/java/
├── module-info.java
└── com/senai/nucitalanches/nucitalanches/
    ├── application/          # Operações existentes de Restaurante
    ├── console/              # Menu de texto existente
    ├── domain/               # Modelos e validações existentes
    ├── storage/              # Armazenamento original em memória
    ├── ui/                   # Controller e ícones da interface incorporada
    ├── HelloApplication.java # Entrada JavaFX mantida
    ├── HelloController.java  # Controller do exemplo original, preservado
    └── Launcher.java         # Iniciador existente
src/main/resources/com/senai/nucitalanches/nucitalanches/
├── view/cardapio.fxml
├── css/javafx-nucita-theme.css
├── images/logo.png
└── hello-view.fxml           # Exemplo original preservado
src/test/java/                # Testes originais e integração JavaFX
```

A interface veio de [Nucita-Lanches/Teste-JavaFX](https://github.com/Nucita-Lanches/Teste-JavaFX), no commit [`2758cd1`](https://github.com/Nucita-Lanches/Teste-JavaFX/commit/2758cd125dd047922e9cd9749ecd97856b16ec11). O código do principal é a fonte das operações de negócio: a integração não copia outra implementação das regras nem depende do outro repositório para executar.

## Contribuir

Crie uma branch própria a partir de `main`, faça alterações relacionadas a uma tarefa e abra um Pull Request para revisão, conforme [`CONTRIBUTING.md`](CONTRIBUTING.md) e [`DEVELOPMENT_PIPELINE.md`](DEVELOPMENT_PIPELINE.md).

Documentação: [`docs/ARQUITETURA.md`](docs/ARQUITETURA.md), [`docs/INTEGRACAO-JAVAFX.md`](docs/INTEGRACAO-JAVAFX.md) e [`PROJECT_CONTEXT.md`](PROJECT_CONTEXT.md).
