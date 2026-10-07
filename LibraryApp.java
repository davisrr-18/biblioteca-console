import java.util.List;
import java.util.Scanner;

public class LibraryApp {

    private static final String DIVIDER = "----------------------------------------";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryService service = new LibraryService();
        int option;

        printWelcome();

        do {
            printMenu();
            option = readInt(scanner, "Choose an option: ");
            System.out.println();

            switch (option) {
                case 1 -> registerBook(scanner, service);
                case 2 -> listBooksText(service);
                case 3 -> listBooksJson(service);
                case 4 -> searchBooksByTerm(scanner, service);
                case 5 -> listAvailableBooks(service);
                case 6 -> registerReader(scanner, service);
                case 7 -> findReaderById(scanner, service);
                case 8 -> listReadersJson(service);
                case 9 -> loanBook(scanner, service);
                case 10 -> returnBook(scanner, service);
                case 0 -> printGoodbye();
                default -> System.out.println("Invalid or not yet implemented option.");
            }

            if (option != 0) {
                waitForContinue(scanner);
            }
        } while (option != 0);

        scanner.close();
    }

    static void printWelcome() {
        System.out.println();
        System.out.println("  Library Console");
        System.out.println("  In-memory catalog and loan management");
        System.out.println();
    }

    static void printGoodbye() {
        beginSection("Exit");
        System.out.println("Thank you for using Library Console. Goodbye.");
        endSection();
    }

    static void printMenu() {
        System.out.println(DIVIDER);
        System.out.println(" MAIN MENU ");
        System.out.println(DIVIDER);
        System.out.println();
        System.out.println("  Books");
        System.out.println("    1  Register book");
        System.out.println("    2  List books (text)");
        System.out.println("    3  List books (JSON)");
        System.out.println("    4  Search books (title or author)");
        System.out.println("    5  List available books");
        System.out.println();
        System.out.println("  Readers");
        System.out.println("    6  Register reader");
        System.out.println("    7  Find reader by id (JSON)");
        System.out.println("    8  List readers (JSON)");
        System.out.println();
        System.out.println("  Loans");
        System.out.println("    9  Loan book");
        System.out.println("   10  Return book");
        System.out.println();
        System.out.println("    0  Exit");
        System.out.println();
    }

    static void beginSection(String title) {
        System.out.println(DIVIDER);
        System.out.println(" " + title);
        System.out.println(DIVIDER);
        System.out.println();
    }

    static void endSection() {
        System.out.println();
    }

    static void waitForContinue(Scanner scanner) {
        System.out.println(DIVIDER);
        readLine(scanner, "Press Enter to return to the menu...");
        System.out.println();
    }

    static void listBooksText(LibraryService service) {
        beginSection("All books");
        printBooks(service.listBooks(), "No books registered.");
        endSection();
    }

    static void searchBooksByTerm(Scanner scanner, LibraryService service) {
        beginSection("Search books");
        String term = readLine(scanner, "Search term: ");
        System.out.println();
        printBooks(service.searchBooksByTerm(term), "No books found.");
        endSection();
    }

    static void listAvailableBooks(LibraryService service) {
        beginSection("Available books");
        printBooks(service.listAvailableBooks(), "No books available.");
        endSection();
    }

    static void printBooks(List<Book> books, String emptyMessage) {
        if (books.isEmpty()) {
            System.out.println("  " + emptyMessage);
            return;
        }
        System.out.printf("  %-4s | %-24s | %-20s | %s%n", "ID", "Title", "Author", "Status");
        System.out.println("  " + "-".repeat(72));
        for (Book book : books) {
            String status = book.isAvailable()
                    ? "available"
                    : "on loan (reader " + book.getBorrowedReaderId() + ")";
            System.out.printf(
                    "  %-4d | %-24s | %-20s | %s%n",
                    book.getId(),
                    truncate(book.getTitle(), 24),
                    truncate(book.getAuthor(), 20),
                    status);
        }
    }

    static String truncate(String value, int maxLength) {
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength - 3) + "...";
    }

    static void listBooksJson(LibraryService service) {
        beginSection("Books (JSON)");
        System.out.println(service.listBooksAsJson());
        endSection();
    }

    static void registerReader(Scanner scanner, LibraryService service) {
        beginSection("Register reader");
        String name = readLine(scanner, "Reader name: ");
        System.out.println();
        try {
            Reader reader = service.registerReader(name);
            System.out.println("  Registered:");
            System.out.println("  " + reader.toJson());
        } catch (IllegalArgumentException e) {
            System.out.println("  Error: " + e.getMessage());
        }
        endSection();
    }

    static void findReaderById(Scanner scanner, LibraryService service) {
        beginSection("Find reader");
        int id = readInt(scanner, "Reader id: ");
        System.out.println();
        service.findReaderById(id)
                .ifPresentOrElse(
                        reader -> {
                            System.out.println("  Result:");
                            System.out.println("  " + reader.toJson());
                        },
                        () -> System.out.println("  Reader not found."));
        endSection();
    }

    static void listReadersJson(LibraryService service) {
        beginSection("Readers (JSON)");
        System.out.println(service.listReadersAsJson());
        endSection();
    }

    static void loanBook(Scanner scanner, LibraryService service) {
        beginSection("Loan book");
        int bookId = readInt(scanner, "Book id: ");
        int readerId = readInt(scanner, "Reader id: ");
        System.out.println();
        try {
            service.loanBook(bookId, readerId);
            System.out.println("  Loan recorded successfully.");
        } catch (RuntimeException e) {
            System.out.println("  Error: " + e.getMessage());
        }
        endSection();
    }

    static void returnBook(Scanner scanner, LibraryService service) {
        beginSection("Return book");
        int bookId = readInt(scanner, "Book id: ");
        System.out.println();
        try {
            service.returnBook(bookId);
            System.out.println("  Return recorded successfully.");
        } catch (RuntimeException e) {
            System.out.println("  Error: " + e.getMessage());
        }
        endSection();
    }

    static void registerBook(Scanner scanner, LibraryService service) {
        beginSection("Register book");
        String title = readLine(scanner, "Title: ");
        String author = readLine(scanner, "Author: ");
        System.out.println();
        try {
            Book book = service.registerBook(title, author);
            System.out.println("  Book registered with id " + book.getId() + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("  Error: " + e.getMessage());
        }
        endSection();
    }

    static String readLine(Scanner scanner, String prompt) {
        System.out.print("  " + prompt);
        return scanner.nextLine();
    }

    static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print("  " + prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("  Invalid input. Enter an integer.");
                System.out.println();
            }
        }
    }
}
