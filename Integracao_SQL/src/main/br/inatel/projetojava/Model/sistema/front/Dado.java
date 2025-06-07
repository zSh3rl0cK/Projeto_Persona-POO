package main.br.inatel.projetojava.Model.sistema.front;

public class Dado {

    // Retorna uma string com a arte do dado baseada no valor
    public static String imprimirDado(int valor) {
        return switch (valor) {
            case 1 -> """
                     _______
                    |       |
                    |   o   |
                    |       |
                     -------""";
            case 2 -> """
                     _______
                    | o     |
                    |       |
                    |     o |
                     -------""";
            case 3 -> """
                     _______
                    | o     |
                    |   o   |
                    |     o |
                     -------""";
            case 4 -> """
                     _______
                    | o   o |
                    |       |
                    | o   o |
                     -------""";
            case 5 -> """
                     _______
                    | o   o |
                    |   o   |
                    | o   o |
                     -------""";
            case 6 -> """
                     _______
                    | o   o |
                    | o   o |
                    | o   o |
                     -------""";
            default -> "";
        };
    }
}