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
        System.out.println("0 - Sair");
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
