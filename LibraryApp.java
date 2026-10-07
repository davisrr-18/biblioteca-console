import java.util.List;
import java.util.Scanner;

public class LibraryApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryService service = new LibraryService();
        int option;

        do {
            printMenu();
            option = readInt(scanner, "Choose an option: ");

            switch (option) {
                case 1 -> registerBook(scanner, service);
                case 2 -> listBooksText(service);
                case 3 -> listBooksJson(service);
                case 4 -> registerReader(scanner, service);
                case 5 -> findReaderById(scanner, service);
                case 6 -> listReadersJson(service);
                case 7 -> loanBook(scanner, service);
                case 8 -> returnBook(scanner, service);
                case 9 -> searchBooksByTerm(scanner, service);
                case 10 -> listAvailableBooks(service);
                case 0 -> System.out.println("Goodbye.");
                default -> System.out.println("Invalid or not yet implemented option.");
            }
        } while (option != 0);

        scanner.close();
    }

    static void printMenu() {
        System.out.println();
        System.out.println("=== Library ===");
        System.out.println("1 - Register book");
        System.out.println("2 - List books (text)");
        System.out.println("3 - List books (JSON)");
        System.out.println("4 - Register reader");
        System.out.println("5 - Find reader by id (JSON)");
        System.out.println("6 - List readers (JSON)");
        System.out.println("7 - Loan book");
        System.out.println("8 - Return book");
        System.out.println("9 - Search books (title or author)");
        System.out.println("10 - List available books");
        System.out.println("0 - Exit");
    }

    static void listBooksText(LibraryService service) {
        printBooks(service.listBooks(), "No books registered.");
    }

    static void searchBooksByTerm(Scanner scanner, LibraryService service) {
        String term = readLine(scanner, "Search term: ");
        printBooks(service.searchBooksByTerm(term), "No books found.");
    }

    static void listAvailableBooks(LibraryService service) {
        printBooks(service.listAvailableBooks(), "No books available.");
    }

    static void printBooks(List<Book> books, String emptyMessage) {
        if (books.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        for (Book book : books) {
            String status = book.isAvailable()
                    ? "available"
                    : "on loan (reader id " + book.getBorrowedReaderId() + ")";
            System.out.printf(
                    "id=%d | %s | %s | %s%n",
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    status);
        }
    }

    static void listBooksJson(LibraryService service) {
        System.out.println(service.listBooksAsJson());
    }

    static void registerReader(Scanner scanner, LibraryService service) {
        String name = readLine(scanner, "Reader name: ");
        try {
            Reader reader = service.registerReader(name);
            System.out.println(reader.toJson());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void findReaderById(Scanner scanner, LibraryService service) {
        int id = readInt(scanner, "Reader id: ");
        service.findReaderById(id)
                .ifPresentOrElse(
                        reader -> System.out.println(reader.toJson()),
                        () -> System.out.println("Reader not found."));
    }

    static void listReadersJson(LibraryService service) {
        System.out.println(service.listReadersAsJson());
    }

    static void loanBook(Scanner scanner, LibraryService service) {
        int bookId = readInt(scanner, "Book id: ");
        int readerId = readInt(scanner, "Reader id: ");
        try {
            service.loanBook(bookId, readerId);
            System.out.println("Loan recorded.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void returnBook(Scanner scanner, LibraryService service) {
        int bookId = readInt(scanner, "Book id: ");
        try {
            service.returnBook(bookId);
            System.out.println("Return recorded.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void registerBook(Scanner scanner, LibraryService service) {
        String title = readLine(scanner, "Title: ");
        String author = readLine(scanner, "Author: ");
        try {
            Book book = service.registerBook(title, author);
            System.out.println("Book registered with id " + book.getId() + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static String readLine(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter an integer.");
            }
        }
    }
}
