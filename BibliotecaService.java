import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Regras de negócio da biblioteca. Metodos de emprestimo/devolucao serao adicionados em commits seguintes.
 */
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

    // Proximas partes: cadastrarLivro, cadastrarLeitor, emprestar, devolver, buscas com Stream
}
