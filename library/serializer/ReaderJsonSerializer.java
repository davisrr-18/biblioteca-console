package library.serializer;

import java.util.List;
import java.util.stream.Collectors;

import library.entities.Reader;

public final class ReaderJsonSerializer {

    private ReaderJsonSerializer() {
    }

    public static String toJson(Reader reader) {
        return String.format(
                "{\"id\":%d,\"name\":\"%s\"}",
                reader.getId(),
                JsonEscaper.escape(reader.getName()));
    }

    public static String toJsonArray(List<Reader> readers) {
        if (readers.isEmpty()) {
            return "[]";
        }
        return readers.stream()
                .map(ReaderJsonSerializer::toJson)
                .collect(Collectors.joining(",", "[", "]"));
    }
}
