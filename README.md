# Library Console

Repository: [github.com/davisrr-18/library-console](https://github.com/davisrr-18/library-console)

Command-line application to manage catalog, readers, and loans. In-memory storage: data is cleared when the program exits.

## Stack

- Java 21+
- Collections, Streams, Optional
- Hand-built JSON via `library.serializer` (entities stay persistence/format-agnostic)
- Layered packages (console precursor to Spring MVC)

## Run

```bash
find library -name "*.java" | xargs javac -d out
java -cp out library.app.LibraryApp
```

## Project layout

```
library/
  app/           Entry point (main)
  controller/    Console UI — menu and I/O
  service/       Business rules and in-memory storage
  entities/      Domain model (Book, Reader)
  serializer/    JSON mapping (Book, Reader)
  exceptions/    Domain-specific runtime exceptions
```

## Architecture

| Package | Role | Spring analogue (later) |
|---------|------|-------------------------|
| `library.app` | Bootstrap | `SpringApplication` |
| `library.controller` | User interaction | `@RestController` |
| `library.service` | Business logic | `@Service` |
| `library.entities` | Domain data | `@Entity` |
| `library.serializer` | Entity → JSON | Jackson DTOs / mappers (later) |
| `library.exceptions` | Error types | `@ControllerAdvice` handlers |

## Features

- [x] Interactive menu with numeric input validation
- [x] Book registration (auto ID, required title/author, no duplicate title+author)
- [x] Book listing (text and JSON)
- [x] Reader registration and lookup (JSON)
- [x] Loan and return
- [x] Book search by title or author
- [x] Available copies report

## Business rules

- Each book gets a sequential numeric id.
- Two books cannot share the same title **and** author (case-insensitive).
- Readers have sequential id and name; loans link a book to a reader while the copy is unavailable.

## Planned enhancements

- Maven module layout (`src/main/java`)
- Persistence (file or database)
- REST API replacing the console controller
- Automated tests (JUnit)
