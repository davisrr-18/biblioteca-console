package library.controller;

import java.util.List;
import java.util.Scanner;

import library.entities.Book;
import library.entities.Reader;
import library.service.LibraryService;

/**
 * Console presentation layer: menu, input, and output (Spring MVC "controller" analogue).
 */
public class LibraryConsoleController {

    private static final String DIVIDER = "----------------------------------------";

    private final LibraryService libraryService;
    private final Scanner scanner;

    public LibraryConsoleController(LibraryService libraryService, Scanner scanner) {
        this.libraryService = libraryService;
        this.scanner = scanner;
    }

    public void run() {
        printWelcome();
        int option;

        do {
            printMenu();
            option = readInt("Choose an option: ");
            System.out.println();
            handleOption(option);
            if (option != 0) {
                waitForContinue();
            }
        } while (option != 0);
    }

    private void handleOption(int option) {
        switch (option) {
            case 1 -> registerBook();
            case 2 -> listBooksText();
            case 3 -> listBooksJson();
            case 4 -> searchBooksByTerm();
            case 5 -> listAvailableBooks();
            case 6 -> registerReader();
            case 7 -> findReaderById();
            case 8 -> listReadersJson();
            case 9 -> loanBook();
            case 10 -> returnBook();
            case 0 -> printGoodbye();
            default -> System.out.println("Invalid or not yet implemented option.");
        }
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("  Library Console");
        System.out.println("  In-memory catalog and loan management");
        System.out.println();
    }

    private void printGoodbye() {
        beginSection("Exit");
        System.out.println("Thank you for using Library Console. Goodbye.");
        endSection();
    }

    private void printMenu() {
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

    private void beginSection(String title) {
        System.out.println(DIVIDER);
        System.out.println(" " + title);
        System.out.println(DIVIDER);
        System.out.println();
    }

    private void endSection() {
        System.out.println();
    }

    private void waitForContinue() {
        System.out.println(DIVIDER);
        readLine("Press Enter to return to the menu...");
        System.out.println();
    }

    private void listBooksText() {
        beginSection("All books");
        printBooks(libraryService.listBooks(), "No books registered.");
        endSection();
    }

    private void searchBooksByTerm() {
        beginSection("Search books");
        String term = readLine("Search term: ");
        System.out.println();
        printBooks(libraryService.searchBooksByTerm(term), "No books found.");
        endSection();
    }

    private void listAvailableBooks() {
        beginSection("Available books");
        printBooks(libraryService.listAvailableBooks(), "No books available.");
        endSection();
    }

    private void printBooks(List<Book> books, String emptyMessage) {
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

    private static String truncate(String value, int maxLength) {
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength - 3) + "...";
    }

    private void listBooksJson() {
        beginSection("Books (JSON)");
        System.out.println(libraryService.listBooksAsJson());
        endSection();
    }

    private void registerReader() {
        beginSection("Register reader");
        String name = readLine("Reader name: ");
        System.out.println();
        try {
            Reader reader = libraryService.registerReader(name);
            System.out.println("  Registered:");
            System.out.println("  " + reader.toJson());
        } catch (IllegalArgumentException e) {
            System.out.println("  Error: " + e.getMessage());
        }
        endSection();
    }

    private void findReaderById() {
        beginSection("Find reader");
        int id = readInt("Reader id: ");
        System.out.println();
        libraryService.findReaderById(id)
                .ifPresentOrElse(
                        reader -> {
                            System.out.println("  Result:");
                            System.out.println("  " + reader.toJson());
                        },
                        () -> System.out.println("  Reader not found."));
        endSection();
    }

    private void listReadersJson() {
        beginSection("Readers (JSON)");
        System.out.println(libraryService.listReadersAsJson());
        endSection();
    }

    private void loanBook() {
        beginSection("Loan book");
        int bookId = readInt("Book id: ");
        int readerId = readInt("Reader id: ");
        System.out.println();
        try {
            libraryService.loanBook(bookId, readerId);
            System.out.println("  Loan recorded successfully.");
        } catch (RuntimeException e) {
            System.out.println("  Error: " + e.getMessage());
        }
        endSection();
    }

    private void returnBook() {
        beginSection("Return book");
        int bookId = readInt("Book id: ");
        System.out.println();
        try {
            libraryService.returnBook(bookId);
            System.out.println("  Return recorded successfully.");
        } catch (RuntimeException e) {
            System.out.println("  Error: " + e.getMessage());
        }
        endSection();
    }

    private void registerBook() {
        beginSection("Register book");
        String title = readLine("Title: ");
        String author = readLine("Author: ");
        System.out.println();
        try {
            Book book = libraryService.registerBook(title, author);
            System.out.println("  Book registered with id " + book.getId() + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("  Error: " + e.getMessage());
        }
        endSection();
    }

    private String readLine(String prompt) {
        System.out.print("  " + prompt);
        return scanner.nextLine();
    }

    private int readInt(String prompt) {
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
