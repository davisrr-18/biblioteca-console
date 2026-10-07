public class LivroNaoEncontradoException extends RuntimeException {

    public LivroNaoEncontradoException(int id) {
        super("Livro nao encontrado: id " + id);
    }
}
