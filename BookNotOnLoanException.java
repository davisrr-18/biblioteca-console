public class BookNotOnLoanException extends RuntimeException {

    public BookNotOnLoanException(int id) {
        super("Book is not on loan: id " + id);
    }
}
