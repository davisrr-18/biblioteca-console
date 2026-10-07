import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class BibliotecaService {

    private final Map<Integer, Livro> livrosPorId = new HashMap<>();
    private final Map<Integer, Leitor> leitoresPorId = new HashMap<>();
    private int proximoIdLivro = 1;
    private int proximoIdLeitor = 1;

    public Optional<Livro> buscarLivroPorId(int id) {
        return Optional.ofNullable(livrosPorId.get(id));
    }

    public Optional<Leitor> buscarLeitorPorId(int id) {
        return Optional.ofNullable(leitoresPorId.get(id));
    }

    public List<Livro> listarLivros() {
        return livrosPorId.values().stream()
                .sorted(Comparator.comparingInt(Livro::getId))
                .toList();
    }

    public String listarLivrosComoJson() {
        List<Livro> livros = listarLivros();
        if (livros.isEmpty()) {
            return "[]";
        }
        return livros.stream()
                .map(Livro::toJson)
                .collect(Collectors.joining(",", "[", "]"));
    }

    public List<Leitor> listarLeitores() {
        return leitoresPorId.values().stream()
                .sorted(Comparator.comparingInt(Leitor::getId))
                .toList();
    }

    public String listarLeitoresComoJson() {
        List<Leitor> leitores = listarLeitores();
        if (leitores.isEmpty()) {
            return "[]";
        }
        return leitores.stream()
                .map(Leitor::toJson)
                .collect(Collectors.joining(",", "[", "]"));
    }

    public Leitor cadastrarLeitor(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do leitor e obrigatorio.");
        }

        String nomeNormalizado = nome.trim();

        for (Leitor existente : leitoresPorId.values()) {
            if (existente.getNome().equalsIgnoreCase(nomeNormalizado)) {
                throw new IllegalArgumentException("Leitor ja cadastrado com este nome.");
            }
        }

        int id = proximoIdLeitor++;
        Leitor leitor = new Leitor(id, nomeNormalizado);
        leitoresPorId.put(id, leitor);
        return leitor;
    }

    public Livro cadastrarLivro(String titulo, String autor) {
        if (titulo == null || titulo.isBlank() || autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("Titulo e autor sao obrigatorios.");
        }

        String tituloNormalizado = titulo.trim();
        String autorNormalizado = autor.trim();

        for (Livro existente : livrosPorId.values()) {
            if (existente.getTitulo().equalsIgnoreCase(tituloNormalizado)
                    && existente.getAutor().equalsIgnoreCase(autorNormalizado)) {
                throw new IllegalArgumentException("Livro ja cadastrado com mesmo titulo e autor.");
            }
        }

        int id = proximoIdLivro++;
        Livro livro = new Livro(id, tituloNormalizado, autorNormalizado);
        livrosPorId.put(id, livro);
        return livro;
    }

}
