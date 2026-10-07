import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LibraryService {

    private final Map<Integer, Book> booksById = new HashMap<>();
    private final Map<Integer, Reader> readersById = new HashMap<>();
    private int nextBookId = 1;
    private int nextReaderId = 1;

    public Optional<Book> findBookById(int id) {
        return Optional.ofNullable(booksById.get(id));
    }

    public Optional<Reader> findReaderById(int id) {
        return Optional.ofNullable(readersById.get(id));
    }

    public List<Book> listBooks() {
        return booksById.values().stream()
                .sorted(Comparator.comparingInt(Book::getId))
                .toList();
    }

    public String listBooksAsJson() {
        List<Book> books = listBooks();
        if (books.isEmpty()) {
            return "[]";
        }
        return books.stream()
                .map(Book::toJson)
                .collect(Collectors.joining(",", "[", "]"));
    }

    public List<Reader> listReaders() {
        return readersById.values().stream()
                .sorted(Comparator.comparingInt(Reader::getId))
                .toList();
    }

    public String listReadersAsJson() {
        List<Reader> readers = listReaders();
        if (readers.isEmpty()) {
            return "[]";
        }
        return readers.stream()
                .map(Reader::toJson)
                .collect(Collectors.joining(",", "[", "]"));
    }

    public Reader registerReader(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Reader name is required.");
        }

        String normalizedName = name.trim();

        for (Reader existing : readersById.values()) {
            if (existing.getName().equalsIgnoreCase(normalizedName)) {
                throw new IllegalArgumentException("Reader already registered with this name.");
            }
        }

        int id = nextReaderId++;
        Reader reader = new Reader(id, normalizedName);
        readersById.put(id, reader);
        return reader;
    }

    public Book registerBook(String title, String author) {
        if (title == null || title.isBlank() || author == null || author.isBlank()) {
            throw new IllegalArgumentException("Title and author are required.");
        }

        String normalizedTitle = title.trim();
        String normalizedAuthor = author.trim();

        for (Book existing : booksById.values()) {
            if (existing.getTitle().equalsIgnoreCase(normalizedTitle)
                    && existing.getAuthor().equalsIgnoreCase(normalizedAuthor)) {
                throw new IllegalArgumentException("Book already registered with the same title and author.");
            }
        }

        int id = nextBookId++;
        Book book = new Book(id, normalizedTitle, normalizedAuthor);
        booksById.put(id, book);
        return book;
    }

    public void loanBook(int bookId, int readerId) {
        Book book = findBookById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        findReaderById(readerId)
                .orElseThrow(() -> new ReaderNotFoundException(readerId));

        if (!book.isAvailable()) {
            throw new BookUnavailableException(bookId);
        }

        book.setAvailable(false);
        book.setBorrowedReaderId(readerId);
    }

    public List<Book> searchBooksByTerm(String term) {
        if (term == null || term.isBlank()) {
            return List.of();
        }
        String normalizedTerm = term.trim().toLowerCase(Locale.ROOT);
        return booksById.values().stream()
                .filter(book -> containsTerm(book, normalizedTerm))
                .sorted(Comparator.comparing(book -> book.getTitle().toLowerCase(Locale.ROOT)))
                .toList();
    }

    public List<Book> listAvailableBooks() {
        return booksById.values().stream()
                .filter(Book::isAvailable)
                .sorted(Comparator.comparing(book -> book.getTitle().toLowerCase(Locale.ROOT)))
                .toList();
    }

    private static boolean containsTerm(Book book, String normalizedTerm) {
        return book.getTitle().toLowerCase(Locale.ROOT).contains(normalizedTerm)
                || book.getAuthor().toLowerCase(Locale.ROOT).contains(normalizedTerm);
    }

    public void returnBook(int bookId) {
        Book book = findBookById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        if (book.isAvailable()) {
            throw new BookNotOnLoanException(bookId);
        }

        book.setAvailable(true);
        book.setBorrowedReaderId(null);
    }
}
