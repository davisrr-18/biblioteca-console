public class LivroNaoEmprestadoException extends RuntimeException {

    public LivroNaoEmprestadoException(int id) {
        super("Livro nao esta emprestado: id " + id);
    }
}
