import java.util.List;
import java.util.Scanner;

public class BibliotecaApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BibliotecaService service = new BibliotecaService();
        int opcao;

        do {
            imprimirMenu();
            opcao = lerInteiro(scanner, "Escolha uma opcao: ");

            switch (opcao) {
                case 1 -> cadastrarLivro(scanner, service);
                case 2 -> listarLivrosTexto(service);
                case 3 -> listarLivrosJson(service);
                case 4 -> cadastrarLeitor(scanner, service);
                case 5 -> buscarLeitorPorId(scanner, service);
                case 6 -> listarLeitoresJson(service);
                case 7 -> emprestarLivro(scanner, service);
                case 8 -> devolverLivro(scanner, service);
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opcao invalida ou ainda nao implementada.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    static void imprimirMenu() {
        System.out.println();
        System.out.println("=== Biblioteca ===");
        System.out.println("1 - Cadastrar livro");
        System.out.println("2 - Listar livros (texto)");
        System.out.println("3 - Listar livros (JSON)");
        System.out.println("4 - Cadastrar leitor");
        System.out.println("5 - Buscar leitor por id (JSON)");
        System.out.println("6 - Listar leitores (JSON)");
        System.out.println("7 - Emprestar livro");
        System.out.println("8 - Devolver livro");
        System.out.println("0 - Sair");
    }

    static void listarLivrosTexto(BibliotecaService service) {
        List<Livro> livros = service.listarLivros();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }
        for (Livro livro : livros) {
            String status = livro.isDisponivel()
                    ? "disponivel"
                    : "emprestado (leitor id " + livro.getLeitorEmprestimoId() + ")";
            System.out.printf(
                    "id=%d | %s | %s | %s%n",
                    livro.getId(),
                    livro.getTitulo(),
                    livro.getAutor(),
                    status);
        }
    }

    static void listarLivrosJson(BibliotecaService service) {
        System.out.println(service.listarLivrosComoJson());
    }

    static void cadastrarLeitor(Scanner scanner, BibliotecaService service) {
        String nome = lerLinha(scanner, "Nome do leitor: ");
        try {
            Leitor leitor = service.cadastrarLeitor(nome);
            System.out.println(leitor.toJson());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void buscarLeitorPorId(Scanner scanner, BibliotecaService service) {
        int id = lerInteiro(scanner, "Id do leitor: ");
        service.buscarLeitorPorId(id)
                .ifPresentOrElse(
                        leitor -> System.out.println(leitor.toJson()),
                        () -> System.out.println("Leitor nao encontrado."));
    }

    static void listarLeitoresJson(BibliotecaService service) {
        System.out.println(service.listarLeitoresComoJson());
    }

    static void emprestarLivro(Scanner scanner, BibliotecaService service) {
        int livroId = lerInteiro(scanner, "Id do livro: ");
        int leitorId = lerInteiro(scanner, "Id do leitor: ");
        try {
            service.emprestarLivro(livroId, leitorId);
            System.out.println("Emprestimo registrado.");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void devolverLivro(Scanner scanner, BibliotecaService service) {
        int livroId = lerInteiro(scanner, "Id do livro: ");
        try {
            service.devolverLivro(livroId);
            System.out.println("Devolucao registrada.");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void cadastrarLivro(Scanner scanner, BibliotecaService service) {
        String titulo = lerLinha(scanner, "Titulo: ");
        String autor = lerLinha(scanner, "Autor: ");
        try {
            Livro livro = service.cadastrarLivro(titulo, autor);
            System.out.println("Livro cadastrado com id " + livro.getId() + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static String lerLinha(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    static int lerInteiro(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String linha = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Digite um numero inteiro.");
            }
        }
    }
}
