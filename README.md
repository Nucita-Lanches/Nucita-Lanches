# Nucita Lanches

Aplicação desktop para organizar pedidos realizados nas mesas de um restaurante. O projeto propõe substituir o controle manual por um fluxo centralizado de cadastro, acompanhamento e fechamento de pedidos.

> **Estado do projeto:** este repositório contém a base de uma aplicação Java com JavaFX e Maven. As funcionalidades descritas como objetivo do sistema devem ser consideradas planejadas até que estejam implementadas e verificadas no código.

## Visão geral

O sistema foi pensado para apoiar a operação do restaurante, mantendo os pedidos associados às mesas e reunindo seus itens e valores em um só lugar.

### Objetivos funcionais

- Cadastrar produtos com nome e preço.
- Cadastrar mesas.
- Abrir pedidos e associá-los a uma mesa.
- Adicionar produtos aos pedidos e definir quantidades.
- Consultar os itens e os valores do pedido.
- Remover itens e atualizar os totais.
- Finalizar pedidos, impedindo alterações posteriores.

Esses itens refletem o contexto e as regras documentadas do projeto; consulte o código-fonte para confirmar a disponibilidade de cada função na versão atual.

## Regras de negócio

1. Uma mesa pode ter vários pedidos ao longo do tempo.
2. Cada pedido pertence a uma mesa e pode conter vários itens.
3. Cada item associa um produto a uma quantidade.
4. O subtotal de um item é calculado por `preço do produto × quantidade`.
5. O total do pedido é a soma dos subtotais dos itens.
6. Ao remover um item, o total deve ser recalculado.
7. Um pedido finalizado não deve aceitar alterações próprias de um pedido aberto.

## Modelo conceitual

| Entidade | Responsabilidade |
| --- | --- |
| `Produto` | Representa um item disponível para consumo, com nome e preço. |
| `Mesa` | Identifica uma mesa do restaurante. |
| `Pedido` | Pertence a uma mesa, reúne itens e possui estado aberto ou finalizado. |
| `ItemPedido` | Associa um produto a uma quantidade dentro de um pedido. |

## Tecnologias

- **Java** — linguagem da aplicação.
- **JavaFX** — interface gráfica e componentes de tela.
- **FXML** — suporte à descrição declarativa de interfaces.
- **Maven** — dependências e ciclo de build.
- **JUnit 5** — dependências para testes automatizados.

As versões e configurações vigentes estão definidas em [`pom.xml`](pom.xml).

## Requisitos

- JDK compatível com a configuração do projeto no Maven.
- Maven instalado, ou uso do Maven Wrapper incluído (`mvnw` / `mvnw.cmd`).
- Ambiente gráfico compatível com JavaFX para abrir a aplicação desktop.

> A configuração atual do compilador no `pom.xml` declara Java 9. Verifique a versão efetivamente usada no ambiente antes de executar ou alterar o projeto.

## Como executar

Clone o repositório e entre na pasta do projeto:

```bash
git clone https://github.com/Nucita-Lanches/Nucita-Lanches.git
cd Nucita-Lanches
```

No Windows, execute pelo Maven Wrapper:

```powershell
./mvnw.cmd clean javafx:run
```

No Linux ou macOS:

```bash
./mvnw clean javafx:run
```

Se preferir usar uma instalação local do Maven, substitua `./mvnw` por `mvn` (ou `mvnw.cmd` por `mvn`).

## Testes e compilação

Para executar os testes configurados:

```bash
./mvnw test
```

Para compilar o projeto:

```bash
./mvnw clean package
```

No Windows, use `./mvnw.cmd` no lugar de `./mvnw`. A existência das dependências de teste no build não garante, por si só, que já haja testes implementados.

## Estrutura do projeto

```text
.
├── .mvn/                  # Configuração do Maven Wrapper
├── src/main/              # Código e recursos da aplicação
├── pom.xml                # Dependências e configuração do build
├── mvnw                   # Maven Wrapper para Linux/macOS
├── mvnw.cmd               # Maven Wrapper para Windows
├── PROJECT_CONTEXT.md     # Contexto, entidades e regras de negócio
├── DEVELOPMENT_PIPELINE.md# Etapas de desenvolvimento e revisão
└── CONTRIBUTING.md        # Regras de contribuição
```

## Contribuição

Contribuições devem seguir o fluxo definido em [`CONTRIBUTING.md`](CONTRIBUTING.md):

1. Crie uma branch própria a partir de `main`.
2. Faça uma alteração pequena e relacionada a uma tarefa.
3. Abra um Pull Request direcionado a `main`.
4. Aguarde a revisão de pelo menos uma pessoa da equipe.
5. Resolva os comentários e faça merge após a aprovação.

Não faça alterações diretamente na branch `main`. Use nomes descritivos para branches, como `feat/cadastro-produtos`, `fix/remocao-item-pedido` ou `docs/regras-projeto`, e prefixos objetivos nos commits, como `feat:`, `fix:`, `docs:`, `test:` e `refactor:`.

Consulte também [`DEVELOPMENT_PIPELINE.md`](DEVELOPMENT_PIPELINE.md) para conhecer as etapas e os critérios de conclusão do projeto.

## Documentação relacionada

- [`PROJECT_CONTEXT.md`](PROJECT_CONTEXT.md) — propósito, entidades e regras de negócio.
- [`DEVELOPMENT_PIPELINE.md`](DEVELOPMENT_PIPELINE.md) — pipeline de desenvolvimento e critérios de merge.
- [`CONTRIBUTING.md`](CONTRIBUTING.md) — fluxo de contribuição.

## Licença

Não há licença identificada nesta documentação. Consulte o repositório ou seus responsáveis antes de redistribuir ou reutilizar o código.
