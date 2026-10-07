package library.controller;

import java.util.Scanner;

import library.service.LibraryService;

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

        scanner.close();
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

    private void printServiceOutput(String output) {
        System.out.println(output);
    }

    private void printServiceOutputIndented(String output) {
        System.out.println("  " + output);
    }

    private void listBooksText() {
        beginSection("All books");
        printServiceOutput(libraryService.listBooksAsText());
        endSection();
    }

    private void searchBooksByTerm() {
        beginSection("Search books");
        String term = readLine("Search term: ");
        System.out.println();
        printServiceOutput(libraryService.searchBooksAsText(term));
        endSection();
    }

    private void listAvailableBooks() {
        beginSection("Available books");
        printServiceOutput(libraryService.listAvailableBooksAsText());
        endSection();
    }

    private void listBooksJson() {
        beginSection("Books (JSON)");
        printServiceOutput(libraryService.listBooksAsJson());
        endSection();
    }

    private void registerReader() {
        beginSection("Register reader");
        String name = readLine("Reader name: ");
        System.out.println();
        try {
            printServiceOutput(libraryService.registerReaderDisplay(name));
        } catch (IllegalArgumentException e) {
            System.out.println("  Error: " + e.getMessage());
        }
        endSection();
    }

    private void findReaderById() {
        beginSection("Find reader");
        int id = readInt("Reader id: ");
        System.out.println();
        printServiceOutput(libraryService.findReaderDisplayById(id));
        endSection();
    }

    private void listReadersJson() {
        beginSection("Readers (JSON)");
        printServiceOutput(libraryService.listReadersAsJson());
        endSection();
    }

    private void loanBook() {
        beginSection("Loan book");
        int bookId = readInt("Book id: ");
        int readerId = readInt("Reader id: ");
        System.out.println();
        try {
            printServiceOutputIndented(libraryService.loanBookWithMessage(bookId, readerId));
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
            printServiceOutputIndented(libraryService.returnBookWithMessage(bookId));
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
            printServiceOutputIndented(libraryService.registerBookConfirmation(title, author));
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
