package library.serializer;

import java.util.List;
import java.util.stream.Collectors;

import library.entities.Book;

public final class BookJsonSerializer {

    private BookJsonSerializer() {
    }

    public static String toJson(Book book) {
        String titleJson = JsonEscaper.escape(book.getTitle());
        String authorJson = JsonEscaper.escape(book.getAuthor());
        String readerPart = book.getBorrowedReaderId() == null
                ? "null"
                : String.valueOf(book.getBorrowedReaderId());
        return String.format(
                "{\"id\":%d,\"title\":\"%s\",\"author\":\"%s\",\"available\":%s,\"borrowedReaderId\":%s}",
                book.getId(),
                titleJson,
                authorJson,
                book.isAvailable(),
                readerPart);
    }

    public static String toJsonArray(List<Book> books) {
        if (books.isEmpty()) {
            return "[]";
        }
        return books.stream()
                .map(BookJsonSerializer::toJson)
                .collect(Collectors.joining(",", "[", "]"));
    }
}
