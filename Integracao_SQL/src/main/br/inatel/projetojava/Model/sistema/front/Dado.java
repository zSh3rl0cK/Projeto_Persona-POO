package main.br.inatel.projetojava.Model.sistema.front;

public class Dado {

    // Retorna uma string com a arte do dado baseada no valor
    public static String imprimirDado(int valor) {
        return switch (valor) {
            case 1 -> " _______\n" +
                    "|       |\n" +
                    "|   o   |\n" +
                    "|       |\n" +
                    " -------";
            case 2 -> " _______\n" +
                    "| o     |\n" +
                    "|       |\n" +
                    "|     o |\n" +
                    " -------";
            case 3 -> " _______\n" +
                    "| o     |\n" +
                    "|   o   |\n" +
                    "|     o |\n" +
                    " -------";
            case 4 -> " _______\n" +
                    "| o   o |\n" +
                    "|       |\n" +
                    "| o   o |\n" +
                    " -------";
            case 5 -> " _______\n" +
                    "| o   o |\n" +
                    "|   o   |\n" +
                    "| o   o |\n" +
                    " -------";
            case 6 -> " _______\n" +
                    "| o   o |\n" +
                    "| o   o |\n" +
                    "| o   o |\n" +
                    " -------";
            default -> "";
        };
    }
}