# Roteiro de apresentação — três integrantes (6 a 8 minutos)

## Preparação da equipe

- Abrir o projeto pelo `pom.xml` no IntelliJ, com JDK 17 ou superior.
- Deixar acessíveis `App`, `FabricaComponentes`, as fábricas concretas, os produtos, `SeletorFabrica` e `JanelaPrincipal`.
- Executar `mvn clean verify` antes da apresentação e conferir `target/surefire-reports/`. Os testes gráficos usam o mouse; aguardar o término antes de demonstrar.
- Manter o [README](../README.md) e as [evidências dos temas](VALIDACAO_TEMAS.md) disponíveis. Se faltar ambiente gráfico, mostrar as imagens como evidências anteriores e informar a limitação da demonstração ao vivo.

## Integrante 1 — problema, objetivo e demonstração (2 minutos)

**Fala sugerida:** “O Personalizador de Interface demonstra como criar componentes compatíveis com um tema. Uma única escolha determina a família do botão, do campo de texto e da caixa de seleção.”

1. Executar `App.main` no IntelliJ. Alternativamente, após o build, usar `java -jar target/abstract-factory-temas-1.0.0.jar`.
2. Mostrar a janela inicialmente no tema Claro e explicar os campos.
3. Tentar confirmar sem nome: apresentar a validação de campo obrigatório.
4. Digitar um nome, marcar notificações e selecionar Escuro.
5. Apontar a mudança conjunta dos três componentes e os nomes concretos no rodapé: `BotaoEscuro`, `CampoTextoEscuro` e `CaixaSelecaoEscura`.
6. Confirmar e mostrar nome, tema e notificações no diálogo.
7. Voltar a Claro, mostrar os valores preservados e a família `BotaoClaro`, `CampoTextoClaro`, `CaixaSelecaoClara`. Desmarcar notificações e confirmar novamente.

**Transição:** “A tela não escolhe cada produto concreto separadamente. A fábrica selecionada fornece a família inteira.”

## Integrante 2 — Abstract Factory e escolha técnica (2 a 3 minutos)

**Conceito:** “Abstract Factory é um padrão criacional que oferece uma interface para criar famílias de objetos relacionados sem o cliente especificar suas classes concretas.”

| Papel | Mostrar no código |
| --- | --- |
| Fábrica abstrata | `FabricaComponentes`: `criarBotao`, `criarCampoTexto`, `criarCaixaSelecao`, além de `tema` e `paleta`. |
| Fábricas concretas | `FabricaTemaClaro` e `FabricaTemaEscuro`: cada uma cria somente produtos de sua família. |
| Produtos abstratos | `Botao`, `CampoTexto` e `CaixaSelecao`: contratos pequenos com `criar(...)`. |
| Produtos concretos | As três classes de `produto/claro` e as três de `produto/escuro`, exibidas no rodapé. |
| Cliente | `JanelaPrincipal.aplicarTema`: solicita os três produtos à mesma fábrica. |
| Seleção e entrada | `SeletorFabrica.para(Tema)` escolhe a fábrica; `App` abre a janela na EDT. |

Mostrar as três chamadas de criação em `aplicarTema` e explicar: os produtos fornecem os componentes Swing; `Paleta`, `Paletas` e `EstilosComponentes` auxiliam na consistência visual. A troca preserva o seletor e substitui apenas o formulário, restaurando nome e seleção.

**Justificativa e comparação:**

- **Abstract Factory:** o contrato representa uma família completa com três tipos de produtos. Trocar a implementação troca o conjunto coordenado.
- **Simple Factory:** concentra a escolha de uma classe por parâmetro. O projeto usa essa ideia em `SeletorFabrica`, mas ela isoladamente não expressa os três contratos de produto nem sua criação como família.
- **Factory Method:** subclasses de um criador especializam a criação de um produto. Vários métodos poderiam ser coordenados, mas a necessidade aqui é fornecer a família por composição, sem subclasses da janela para cada tema. Métodos chamados `criar` não caracterizam automaticamente esse padrão.

**Transição:** “Essa separação também permite identificar princípios SOLID concretos, com limites claros.”

## Integrante 3 — SOLID, testes e encerramento (2 a 3 minutos)

| Princípio | Explicação com classes reais |
| --- | --- |
| **S — Responsabilidade única** | Fábricas criam produtos, `SeletorFabrica` escolhe a família, `EstilosComponentes` compartilha a montagem visual e `JanelaPrincipal` cuida da tela e das interações. |
| **O — Aberto/fechado** | Novas fábricas e produtos podem implementar os contratos sem mudar a lógica de `aplicarTema`. É necessário atualizar `Tema` e `SeletorFabrica` para disponibilizar uma nova opção. |
| **L — Substituição** | `FabricaTemaClaro` e `FabricaTemaEscuro` podem fornecer a família ao mesmo cliente, mantendo o comportamento do formulário. |
| **I — Segregação de interfaces** | `Botao`, `CampoTexto` e `CaixaSelecao` possuem operações específicas; nenhum produto implementa operações dos demais. |
| **D — Inversão de dependência** | A criação na janela depende dos contratos da fábrica e dos produtos. As classes concretas ficam nas fábricas e no seletor; Swing continua sendo a tecnologia da interface. |

Mostrar o resultado de `mvn clean verify`: na validação local documentada, **8 testes aprovados, sem falhas, erros ou ignorados**.

- `FabricaComponentesTest`: seis testes de famílias, paletas e entradas inválidas.
- `JanelaPrincipalTest`: dois testes gráficos, incluindo dez alternâncias por cliques reais, preservação do formulário, confirmação, cores, listeners e ausência de exceções não tratadas na EDT.
- Mostrar as imagens do [relatório de validação](VALIDACAO_TEMAS.md). Em ambiente sem tela, os testes gráficos são ignorados; explicar que isso não valida interação visual.
- Informar que a compilação usa `release 17`; a execução local documentada usou Temurin 24.0.2. O workflow configura Java 17 e Xvfb no Linux, sem confundir configuração com resultado remoto confirmado.

**Encerramento sugerido:** “O projeto demonstra a troca de uma família inteira por uma fábrica abstrata, mantendo coerência visual e o comportamento do formulário.”

## Respostas curtas para perguntas

- **Por que há `new`?** O padrão centraliza a escolha dos produtos concretos nas fábricas; componentes de estrutura e outros objetos continuam sendo instanciados normalmente.
- **Como adicionar outro tema?** Criar fábrica, paleta e três produtos, cadastrar em `Tema` e `SeletorFabrica`, adicionar testes. Isso descreve uma extensão possível; ela não foi implementada.
- **Abstract Factory impede qualquer mistura de temas?** O cliente usa a mesma fábrica para os três produtos e as fábricas existentes cumprem essa convenção, verificada por testes. O contrato Java sozinho não prova que uma nova implementação escolherá as cores corretas.
- **Por que não apenas mudar cores manualmente?** A criação dos três produtos coordenados é o objetivo acadêmico; estilização manual espalhada pela tela esconderia esse papel das famílias.
- **O que corrigiu a alternância?** Manter o content pane e o seletor estáveis evita removê-los enquanto o popup processa o evento. Somente o formulário é substituído, na EDT.
