public class Leitor {

    private final int id;
    private final String nome;

    public Leitor(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String toJson() {
        return String.format("{\"id\":%d,\"nome\":\"%s\"}", id, Livro.escapeJson(nome));
    }
}
