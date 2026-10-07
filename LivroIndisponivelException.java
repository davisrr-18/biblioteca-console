public class LivroIndisponivelException extends RuntimeException {

    public LivroIndisponivelException(int id) {
        super("Livro indisponivel para emprestimo: id " + id);
    }
}
