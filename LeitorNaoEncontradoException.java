public class LeitorNaoEncontradoException extends RuntimeException {

    public LeitorNaoEncontradoException(int id) {
        super("Leitor nao encontrado: id " + id);
    }
}
