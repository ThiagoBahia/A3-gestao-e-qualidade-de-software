import java.util.Scanner;

/**
 * Utilitários para leitura segura do console.
 */
public class ConsoleUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(scanner.nextLine().trim());
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    public static int readIntAllowEmpty(String prompt, int defaultValue) {
        System.out.print(prompt);
        String line = scanner.nextLine();
        if (line.trim().isEmpty()) return defaultValue;
        try {
            return Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Usando valor padrão: " + defaultValue);
            return defaultValue;
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = Double.parseDouble(scanner.nextLine().trim());
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número (ex: 12.50).\n");
            }
        }
    }

    public static double readDoubleAllowEmpty(String prompt, double defaultValue) {
        System.out.print(prompt);
        String line = scanner.nextLine();
        if (line.trim().isEmpty()) return defaultValue;
        try {
            return Double.parseDouble(line.trim());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Usando valor padrão: " + defaultValue);
            return defaultValue;
        }
    }
}
