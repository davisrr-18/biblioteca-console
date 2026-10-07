# Library Console

Repository: [github.com/davisrr-18/biblioteca-console](https://github.com/davisrr-18/biblioteca-console)

Command-line application to manage catalog, readers, and loans. In-memory storage: data is cleared when the program exits.

## Stack

- Java 21+
- Collections, Streams, Optional
- Hand-built JSON on domain entities

## Run

```bash
javac -d out *.java
java -cp out LibraryApp
```

## Architecture

| Layer | Responsibility |
|--------|----------------|
| `LibraryApp` | Menu, user input, output |
| `LibraryService` | Business rules and storage |
| `Book`, `Reader` | Domain model and JSON serialization |

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

## Roadmap

File or database persistence, REST API, and automated tests.
