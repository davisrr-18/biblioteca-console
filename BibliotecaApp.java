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
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opcao invalida ou ainda nao implementada.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    static void imprimirMenu() {
        System.out.println();
        System.out.println("=== Biblioteca (Marco 1) ===");
        System.out.println("0 - Sair");
        System.out.println("(demais opcoes serao adicionadas aos poucos)");
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
