# Nucita Lanches

Aplicação Java desktop em estágio inicial. O código atual contém uma tela JavaFX de exemplo e um fluxo de console para cadastrar e consultar produtos e mesas. Os dados ficam apenas na memória durante a execução.

O modelo de pedidos está incompleto: `Pedido` guarda um número e um único `ItemPedido`; ainda não associa mesas, reúne itens, calcula totais nem controla finalização. O fluxo completo de pedidos descrito no contexto é planejamento, não funcionalidade disponível.

## Requisitos

- JDK 17 ou superior, necessário para executar JavaFX 21.
- Maven Wrapper incluído (`mvnw` / `mvnw.cmd`); não é necessário instalar Maven.
- Ambiente gráfico para abrir a tela JavaFX.

O compilador está configurado para o nível de linguagem Java 9 em [`pom.xml`](pom.xml). A execução do JavaFX 21 requer um JDK mais recente.

## Executar

No Linux ou macOS, na pasta do projeto:

```bash
bash ./mvnw javafx:run
```

No Windows:

```powershell
.\mvnw.cmd javafx:run
```

O comando abre a tela JavaFX de exemplo. Para usar o fluxo de cadastro e consulta no console, execute `com.senai.nucitalanches.nucitalanches.console.Main` pela IDE.

## Validar

Executar os testes configurados:

```bash
bash ./mvnw test
```

Compilar e empacotar:

```bash
bash ./mvnw clean package
```

No Windows PowerShell, use `.\mvnw.cmd` no lugar de `bash ./mvnw`. Atualmente não há testes em `src/test`; `test` valida a compilação, mas não cobre regras por testes automatizados.

## Estrutura

```text
src/main/java/com/senai/nucitalanches/nucitalanches/
├── application/  # Operações disponibilizadas por Restaurante
├── console/      # Menu de console (Main)
├── domain/       # Produto, Mesa, Pedido e ItemPedido
├── storage/      # Armazenamento singleton em memória (BancoDeDados)
├── HelloApplication.java
├── HelloController.java
└── Launcher.java
src/main/resources/com/senai/nucitalanches/nucitalanches/
└── hello-view.fxml
```

Detalhes do estado e das responsabilidades estão em [`docs/ARQUITETURA.md`](docs/ARQUITETURA.md). O contexto e as regras planejadas estão em [`PROJECT_CONTEXT.md`](PROJECT_CONTEXT.md).

## Contribuir

Crie uma branch a partir de `main`, faça alterações pequenas e abra um Pull Request para `main`, conforme [`CONTRIBUTING.md`](CONTRIBUTING.md). O fluxo de revisão está descrito em [`DEVELOPMENT_PIPELINE.md`](DEVELOPMENT_PIPELINE.md).
