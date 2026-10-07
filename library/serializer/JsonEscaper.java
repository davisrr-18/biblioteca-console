package library.serializer;

final class JsonEscaper {

    private JsonEscaper() {
    }

    static String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
