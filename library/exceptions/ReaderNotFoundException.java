package library.exceptions;

public class ReaderNotFoundException extends RuntimeException {

    public ReaderNotFoundException(int id) {
        super("Reader not found: id " + id);
    }
}
