package library.exceptions;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(int id) {
        super("Book not found: id " + id);
    }
}
