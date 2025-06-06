package main.br.inatel.projetojava.Model.itens.lojas;

import main.br.inatel.projetojava.Model.itens.geral.Itens;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Protagonista;

import java.util.Map;
import java.util.Scanner;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class LojaUtil {

    public static boolean confirmarAcao(Scanner sc, String mensagem) {
        while (true) {
            System.out.println(ANSI_GRAY + "\n" + mensagem + " (S/N)");
            String resposta = sc.nextLine().trim().toLowerCase(); // sem espaços e sem maiúsculas
            System.out.println(ANSI_RESET);

            if (resposta.equals("s") || resposta.equals("sim")) return true;
            if (resposta.equals("n") || resposta.equals("nao")) return false;

            System.out.println(ANSI_RED + "Escreva um valor válido!" + ANSI_RESET);
        }
    }

    private static void processarCompra(Scanner sc, Lojas loja, Protagonista protagonista, Map<String, Itens> itens) {
        loja.mostrarItens();
        System.out.println(ANSI_GRAY + "Digite o nome do item: ");
        String nomeItem = sc.nextLine().trim();
        System.out.println(ANSI_RESET);

        if (itens.containsKey(nomeItem)) {
            loja.venderItem(protagonista, itens.get(nomeItem));
            System.out.println(ANSI_GREEN + "Item comprado com sucesso!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "Item não encontrado!" + ANSI_RESET);
        }
    }

    public static void visitarLoja(Scanner sc, Lojas loja, Protagonista protagonista, Map<String, Itens> itens, String nomeLoja) {
        System.out.println(ANSI_CYAN + "\n----- " + nomeLoja + " -----" + ANSI_RESET);
        loja.mostraInfoLoja();

        while (confirmarAcao(sc, "Deseja comprar algum item?")) {
            processarCompra(sc, loja, protagonista, itens);
        }
    }
}
