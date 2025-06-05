package main.br.inatel.projetojava.Model.sistema.menus;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.br.inatel.projetojava.Model.exceptions.InvalidMenuInputException;
import main.br.inatel.projetojava.Model.threads.AudioManager;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class MenuDoJogo {

    public static int opcao;

    public static int mostrar_menu_principal() throws InvalidMenuInputException {
        AudioManager.getInstance().setGameStateMusic(AudioManager.GameState.MAIN_MENU);
        Scanner sc = new Scanner(System.in);
        System.out.println(ANSI_PURPLE +
                "╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                      🎮 MENU PRINCIPAL DO JOGO                     ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ [1] 🧭 Menu de Buscas                                              ║");
        System.out.println("║ [2] 🏰 Menu da Cidade/Combate                                      ║");
        System.out.println("║ [3] 🗄️  Menu SQL                                                   ║");
        System.out.println("║ [0] 🚪 Sair do Jogo                                                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝" + ANSI_RESET);

        System.out.print(ANSI_CYAN + "👉 Escolha uma opção: " + ANSI_RESET);
        try{
            opcao = sc.nextInt();
            if(opcao < 0 || opcao > 3) {
                throw new InvalidMenuInputException("Valor inválido! Tente novamente...");
            }
            sc.nextLine();

            switch(opcao) {
                case 0:
                    System.out.println(ANSI_YELLOW + "Saindo do jogo... Até logo! 👋" + ANSI_RESET);
                    break;
                case 1:
                    System.out.println(ANSI_GREEN + "Acessando Menu de Buscas... 🧭" + ANSI_RESET);
                    break;
                case 2:
                    System.out.println(ANSI_GREEN + "Acessando Menu da Cidade/Combate... 🏰" + ANSI_RESET);
                    break;
                case 3:
                    System.out.println(ANSI_GREEN + "Acessando Menu SQL... 🗄️" + ANSI_RESET);
                    break;
            }
            return opcao;
        }catch(InputMismatchException e){
            throw new InvalidMenuInputException("Por favor, insira um número inteiro...");
        }
    }
}