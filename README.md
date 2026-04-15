# Iniflex - Teste de Processo Seletivo

Projeto Java com todos os requisitos solicitados no desafio da Projedata.

## O que foi implementado

- Classe `Pessoa` com `nome` e `dataNascimento`.
- Classe `Funcionario` estendendo `Pessoa`, com `salario` e `funcao`.
- Classe principal `Principal` executando todos os itens pedidos:
  - inserção dos funcionários da tabela
  - remoção de João
  - impressão com data `dd/MM/yyyy` e número no padrão brasileiro
  - aumento de 10%
  - agrupamento por função
  - aniversariantes dos meses 10 e 12
  - funcionário mais velho (nome e idade)
  - ordenação alfabética
  - total dos salários
  - salários mínimos por funcionário (R$ 1212.00)

## Estrutura principal

- `src/main/java/br/com/projedata/Principal.java`
- `src/main/java/br/com/projedata/Main.java` (wrapper de compatibilidade)
- `src/main/java/br/com/projedata/modelos/Pessoa.java`
- `src/main/java/br/com/projedata/modelos/Funcionario.java`
- `src/main/java/br/com/projedata/dados/BancoFuncionarios.java`
- `src/main/java/br/com/projedata/dados/RepositorioDados.java`
- `src/main/java/br/com/projedata/servicos/ProcessoSeletivoService.java`
- `src/main/java/br/com/projedata/util/FormatadorSaida.java`
- `src/test/java/br/com/projedata/servicos/ProcessoSeletivoServiceTest.java`

## Como executar

```bash
cd /caminho/para/raiz/projeto
./gradlew test
./gradlew run
```

## Testes (JUnit)

A lógica principal foi coberta por testes automatizados JUnit, incluindo:

- remoção de funcionário
- aumento salarial
- agrupamento por função
- aniversariantes
- funcionário mais velho
- ordenação por nome
- total de salários e salários mínimos
- formatação de data e valor

