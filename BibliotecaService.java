import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        return new ArrayList<>(livrosPorId.values());
    }

    public List<Leitor> listarLeitores() {
        return new ArrayList<>(leitoresPorId.values());
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
