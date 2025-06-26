package main.br.inatel.projetojava.Model.sistema.menus;

import main.br.inatel.projetojava.Model.itens.equipaveis.Arma;
import main.br.inatel.projetojava.Model.personagens.NPC;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Usuarios;
import main.br.inatel.projetojava.Model.personas.seres.Shadow;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;
import static main.br.inatel.projetojava.Model.sistema.front.Cores.ANSI_RESET;

public class MethodUtil {

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

    public static void listarNPCs(Map<String, NPC> npcs) {
        if (npcs.isEmpty()) {
            System.out.println(ANSI_YELLOW + "Nenhum NPC disponível no momento." + ANSI_RESET);
            return;
        }

        int count = 1;
        for (String nomeNPC : npcs.keySet()) {
            System.out.println(ANSI_BLUE + count++ + ". " + nomeNPC + ANSI_RESET);
        }
    }

    public static void listarUsuarios(Map<String, Usuarios> usuarios) {
        if (usuarios.isEmpty()) {
            System.out.println(ANSI_YELLOW + "Nenhum usuário cadastrado." + ANSI_RESET);
            return;
        }

        int count = 1;
        for (String nomeUsuario : usuarios.keySet()) {
            System.out.println(ANSI_BLUE + count++ + ". " + nomeUsuario + ANSI_RESET);
        }
    }

    public static void listarAliados(Map<String, Usuarios> usuarios) {
        if (usuarios.isEmpty()) {
            System.out.println(ANSI_YELLOW + "Nenhum usuário cadastrado." + ANSI_RESET);
            return;
        }

        int count = 1;
        boolean temAliados = false;

        for (String nomeAliado : usuarios.keySet()) {
            Usuarios usuario = usuarios.get(nomeAliado);
            if (!usuario.isVilao()) {
                System.out.println(ANSI_BLUE + count++ + ". " + nomeAliado + ANSI_RESET);
                temAliados = true;
            }
        }

        if (!temAliados) {
            System.out.println(ANSI_YELLOW + "Nenhum aliado encontrado." + ANSI_RESET);
        }
    }

    public static void listarShadowsArcana(List<Shadow> Shadow) {
        if (Shadow.isEmpty()) {
            System.out.println(ANSI_YELLOW + "Nenhum Shadow encontrado." + ANSI_RESET);
        }
        else{
            int count = 1;
            for (Shadow shadow : Shadow) {
                System.out.println(ANSI_BLUE + count++ + ". " + shadow.getArcana() + ANSI_RESET);
            }
        }
    }

    // Se for usado em outras classes, deve be public
    public static void consultarUsuario(Map<String, Usuarios> usuarios, String nomeUsuario) {
        if (!usuarios.containsKey(nomeUsuario)) {
            throw new IllegalArgumentException(ANSI_RED + "Usuário não encontrado! Verifique o nome digitado." + ANSI_RESET);
        }

        Usuarios usuario = usuarios.get(nomeUsuario);
        usuario.mostraInfoPersonagem();

        if (usuario.getPersonas() != null) {
            usuario.getPersonas().mostrarStatusPersona();
        } else {
            System.out.println(ANSI_RED + "Este usuário não possui personas." + ANSI_RESET);
        }
    }

    public static int selecionarDestinoItem(Scanner sc) {
        while (true) {
            System.out.println(ANSI_BLUE + "\nSelecione o destino:" + ANSI_RESET);
            System.out.println("1. Protagonista");
            System.out.println("2. Aliado");
            System.out.println("0. Cancelar");
            System.out.print(ANSI_GRAY + "Opção: " + ANSI_RESET);

            try {
                int opcao = sc.nextInt();
                sc.nextLine(); // Limpar buffer
                return opcao;
            } catch (InputMismatchException e) {
                System.out.println(ANSI_RED + "Erro: Digite apenas números!" + ANSI_RESET);
                sc.nextLine(); // Limpar buffer
            }
        }
    }
}
