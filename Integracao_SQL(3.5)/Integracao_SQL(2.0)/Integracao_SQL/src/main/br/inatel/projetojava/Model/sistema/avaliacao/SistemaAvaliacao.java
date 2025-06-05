package main.br.inatel.projetojava.Model.sistema.avaliacao;

import main.br.inatel.projetojava.Model.threads.AudioManager;

import java.util.Scanner;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class SistemaAvaliacao implements Avaliacao {

    Scanner scanner = new Scanner(System.in);

    @Override
    public void solicitarAvaliacao() {
        String resposta;

        while(true) {
            try {
                System.out.print(ANSI_GRAY + "Deseja avaliar o jogo? (S/N) ");
                resposta = scanner.nextLine();
                System.out.println(ANSI_RESET);

                // Verifica se a entrada é válida
                if (resposta.equalsIgnoreCase("S") || resposta.equalsIgnoreCase("Sim")) {
                    avaliarJogo();
                    break;
                } else if (resposta.equalsIgnoreCase("N") || resposta.equalsIgnoreCase("Nao")) {
                    System.out.println(ANSI_YELLOW + "Tudo bem! Obrigado por jogar!" + ANSI_RESET);
                    break;
                } else {
                    // Lança exceção personalizada para entrada inválida
                    throw new IllegalArgumentException("Entrada inválida! Use apenas 'S' ou 'N'.");
                }

            } catch (IllegalArgumentException e) {
                System.out.println(ANSI_RED + "Erro: " + e.getMessage() + ANSI_RESET);
            } catch (Exception e) {
                System.out.println(ANSI_RED + "Erro inesperado: " + e.getMessage() + ANSI_RESET);
            }
        }
    }

    private void avaliarJogo() {
        int nota;
        while (true) {
            try {
                System.out.println(ANSI_GRAY + "De 0 a 5, como você avalia o jogo?" + ANSI_RESET);
                nota = Integer.parseInt(scanner.nextLine());
                if (nota >= 0 && nota <= 5) break;
                else System.out.println(ANSI_RED + "Por favor, escreva uma nota entre 0 e 5!" + ANSI_RESET);
            } catch (NumberFormatException e) {
                System.out.println(ANSI_RED + "Erro: Por favor, digite apenas números!" + ANSI_RESET);
            }
        }

        System.out.println();
        System.out.println(ANSI_GRAY + "Se puder, faça um comentário! (Se não quiser, apenas pressione o enter)");
        String comentario = scanner.nextLine();
        System.out.print(ANSI_RESET);

        if (!comentario.isBlank()) {
            System.out.println(ANSI_YELLOW + "Comentário: " + comentario + ANSI_RESET);
        } else {
            AudioManager.getInstance().stopMusic();
            AudioManager.getInstance().shutdown();
            System.out.println(ANSI_YELLOW + "Tudo bem! Obrigado por jogar!" + ANSI_RESET);
        }

        AudioManager.getInstance().stopMusic();
        AudioManager.getInstance().shutdown();
        System.out.println(ANSI_GREEN + "Obrigado por avaliar e jogar nosso jogo!" + ANSI_RESET);
    }
}