package library.entities;

public class Reader {

    private final int id;
    private final String name;

    public Reader(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toJson() {
        return String.format("{\"id\":%d,\"name\":\"%s\"}", id, Book.escapeJson(name));
    }
}
