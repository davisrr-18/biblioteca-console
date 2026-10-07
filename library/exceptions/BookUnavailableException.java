package library.exceptions;

public class BookUnavailableException extends RuntimeException {

    public BookUnavailableException(int id) {
        super("Book unavailable for loan: id " + id);
    }
}
