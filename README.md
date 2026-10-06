# Biblioteca Console — Marco 1

Sistema de biblioteca em **Java 21+**, rodando no **terminal**, com dados **em memória** (reinicia ao fechar o programa).

Requisito local: `java -version` e `javac -version` na **21 ou superior** (o código não depende de versão acima da 21).

## Objetivo

Praticar OO, Collections, Streams, exceções customizadas e menu com `Scanner`, com histórico versionado em **Git só nesta pasta**.

## Tecnologias

- Java 21+ (sem Maven/Spring neste marco)
- `java.util` (Collections, Scanner, Stream, Optional)
- JSON **manual** em `toJson()` (sem Jackson/Gson)
- Git local (`git init` aqui dentro de `01-biblioteca-console/`)

## Decisões de domínio

- **Livro:** `id` int **auto-incremento** (gerado no service; usuário não informa id).
- **Leitor:** cadastro com `id` auto-incremento + `nome`.
- **Disponibilidade:** `boolean disponivel` em `Livro`; em empréstimo, `Integer leitorEmprestimoId` (null quando disponível).
- **Duplicidade de livro:** mesmo **título** e mesmo **autor** (case insensitive) não pode cadastrar de novo.
- **JSON:** fluxos de **Leitor** (cadastro, busca, listar) e **pelo menos uma** listagem de **Livros** em array JSON.
- **Exceções:** unchecked (`RuntimeException`) na camada de serviço para “não encontrado” / “indisponível” — documentado aqui por ser regra de negócio em app console.

## Arquitetura

- **`BibliotecaApp`** — `main`, menu em loop, `Scanner` (sem regra de negócio pesada).
- **`BibliotecaService`** — cadastro, empréstimo, devolução, buscas, contadores de id.
- **`Livro`**, **`Leitor`** — domínio + `toJson()`.
- **Exceções** — `LivroNaoEncontradoException`, `LeitorNaoEncontradoException`, `LivroIndisponivelException` (conforme implementação).

Compilar: `javac -d out *.java` → `java -cp out BibliotecaApp`

## Requisitos funcionais

- [ ] Menu principal em loop até o usuário sair.
- [ ] Tratar opção inválida e entrada numérica inválida (sem derrubar o programa).
- [ ] Cadastrar livro (id automático; sem duplicar título+autor).
- [ ] Listar todos os livros (texto legível).
- [ ] Listar livros em **JSON** (array).
- [ ] Buscar livros por título ou autor (Stream, contém, case insensitive).
- [ ] Cadastrar leitor; buscar e listar leitores em **JSON**.
- [ ] Emprestar livro (ids livro + leitor; só se disponível).
- [ ] Devolver livro; erro se id inválido ou já disponível.
- [ ] Listar disponíveis com **Stream**.

## Requisitos técnicos (trilha)

- [ ] `List` e/ou `Map` para armazenamento em memória.
- [ ] Pelo menos **duas exceções customizadas** na regra de negócio.
- [ ] **`Optional`** em buscas por id no service.
- [ ] Commits Git pequenos (uma etapa por commit).

## Git

- [x] Repositório apenas em `projetos/01-biblioteca-console/`
- [x] `.gitignore` (`out/`, `*.class`, IDE)

## Fora do escopo

Persistência, API REST, bibliotecas JSON, JUnit (evolução futura).
