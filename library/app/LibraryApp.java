package library.app;

import java.util.Scanner;

import library.controller.LibraryConsoleController;
import library.service.LibraryService;

public class LibraryApp {

    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();
        Scanner scanner = new Scanner(System.in);
        LibraryConsoleController controller = new LibraryConsoleController(libraryService, scanner);
        controller.run();
        scanner.close();
    }
}
