package library.view;

import java.util.List;

import library.entities.Book;

public final class BookTextFormatter {

    private BookTextFormatter() {
    }

    public static String formatTable(List<Book> books, String emptyMessage) {
        if (books.isEmpty()) {
            return "  " + emptyMessage;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("  %-4s | %-24s | %-20s | %s%n", "ID", "Title", "Author", "Status"));
        builder.append("  ").append("-".repeat(72)).append('\n');
        for (Book book : books) {
            String status = book.isAvailable()
                    ? "available"
                    : "on loan (reader " + book.getBorrowedReaderId() + ")";
            builder.append(String.format(
                    "  %-4d | %-24s | %-20s | %s%n",
                    book.getId(),
                    truncate(book.getTitle(), 24),
                    truncate(book.getAuthor(), 20),
                    status));
        }
        return builder.toString().stripTrailing();
    }

    private static String truncate(String value, int maxLength) {
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength - 3) + "...";
    }
}
