# Biblioteca Console

Repositório: [github.com/davisrr-18/biblioteca-console](https://github.com/davisrr-18/biblioteca-console)

Aplicação de linha de comando para gestão de acervo, leitores e empréstimos. Persistência em memória: os dados são reiniciados ao encerrar o programa.

## Stack

- Java 21+
- Collections, Streams, Optional
- Saídas JSON construídas manualmente nas entidades

## Executar

```bash
javac -d out *.java
java -cp out BibliotecaApp
```

## Arquitetura

| Camada | Responsabilidade |
|--------|------------------|
| `BibliotecaApp` | Menu, entrada do usuário, mensagens |
| `BibliotecaService` | Regras de negócio e armazenamento |
| `Livro`, `Leitor` | Modelo de domínio e serialização JSON |

## Funcionalidades

- [x] Menu interativo com validação de opção numérica
- [x] Cadastro de livros (ID automático, título e autor obrigatórios, sem duplicidade título+autor)
- [x] Listagem de livros (texto e JSON)
- [x] Cadastro e consulta de leitores (JSON)
- [x] Empréstimo e devolução
- [ ] Busca de livros por título ou autor
- [ ] Relatório de exemplares disponíveis

## Regras de negócio

- Cada livro recebe um identificador numérico sequencial.
- Não é permitido cadastrar dois livros com o mesmo título **e** o mesmo autor (comparação sem distinção de maiúsculas/minúsculas).
- Leitores possuem ID sequencial e nome; empréstimos associam livro a leitor enquanto o exemplar estiver indisponível.

## Evolução prevista

Persistência em arquivo ou banco, API REST e testes automatizados.
