package main.br.inatel.projetojava.Model.itens.lojas;

import main.br.inatel.projetojava.Model.itens.geral.Itens;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Protagonista;

import java.util.Map;
import java.util.Scanner;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;
import static main.br.inatel.projetojava.Model.sistema.menus.MethodUtil.confirmarAcao;

public abstract class LojaUtil {

    private static void processarCompra(Scanner sc, Lojas loja, Protagonista protagonista, Map<String, Itens> itens) {
        loja.mostrarItens();
        System.out.println(ANSI_GRAY + "Digite o nome do item: ");
        String nomeItem = sc.nextLine().trim();
        System.out.println(ANSI_RESET);

        if (itens.containsKey(nomeItem)) {
            loja.venderItem(protagonista, itens.get(nomeItem));
            System.out.println(ANSI_GREEN + "Item comprado com sucesso!" + ANSI_RESET);
        } else {
            try{
            System.out.println(ANSI_RED + "Item não encontrado!" + ANSI_RESET);
        }catch(IllegalStateException e){
            System.out.println(ANSI_RED + "Erro: " + e.getMessage() + ANSI_RESET);}
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
