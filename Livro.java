public class Livro {

    private final int id;
    private final String titulo;
    private final String autor;
    private boolean disponivel;
    private Integer leitorEmprestimoId;

    public Livro(int id, String titulo, String autor) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.disponivel = true;
        this.leitorEmprestimoId = null;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public Integer getLeitorEmprestimoId() {
        return leitorEmprestimoId;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void setLeitorEmprestimoId(Integer leitorEmprestimoId) {
        this.leitorEmprestimoId = leitorEmprestimoId;
    }

    public String toJson() {
        String tituloJson = escapeJson(titulo);
        String autorJson = escapeJson(autor);
        String leitorPart = leitorEmprestimoId == null
                ? "null"
                : String.valueOf(leitorEmprestimoId);
        return String.format(
                "{\"id\":%d,\"titulo\":\"%s\",\"autor\":\"%s\",\"disponivel\":%s,\"leitorEmprestimoId\":%s}",
                id, tituloJson, autorJson, disponivel, leitorPart);
    }

    static String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
