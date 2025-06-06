package main.br.inatel.projetojava.Model.sistema.menus;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.br.inatel.projetojava.Model.exceptions.InvalidMenuInputException;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class MenuBuscas {
    // ANSI Cores

    public static int opcao;

    public static int mostrar_menu_buscas() throws InvalidMenuInputException {
        Scanner sc = new Scanner(System.in);
        System.out.println(ANSI_PURPLE +
                "╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    🧭 MENU PRINCIPAL DE BUSCAS                     ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ [1] 📜 Ver dados do protagonista                                   ║");
        System.out.println("║ [2] 🧍 Ver dados de um usuário específico                          ║");
        System.out.println("║ [3] 🧙 Ver dados de um NPC específico                              ║");
        System.out.println("║ [4] 🛒 Acessar loja de itens e comprar                             ║");
        System.out.println("║ [5] 🎁 Dar/Equipar itens                                           ║");
        System.out.println("║ [6] 👹 Ver dados dos inimigos (humanos ou sombras)                 ║");
        System.out.println("║ [7] 🎒 Mostrar inventário do protagonista                          ║");
        System.out.println("║ [8] 🎒 Mostrar inventário de um usuário                            ║");
        System.out.println("║ [0] 🚪 Sair                                                        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝" + ANSI_RESET);

        System.out.print(ANSI_CYAN + "👉 Escolha uma opção: " + ANSI_RESET);
        try{
            opcao = sc.nextInt();
            if(opcao < 0 || opcao > 8) {
                throw new InvalidMenuInputException("Valor inválido! Tente novamente...");
            }
            sc.nextLine();

            if(opcao == 0) {
                System.out.println(ANSI_YELLOW + "Saindo do menu de buscas...!" + ANSI_RESET);
            }
            return opcao;
        }catch(InputMismatchException e){
            throw new InvalidMenuInputException("Por favor, insira um número inteiro...");
        }
    }
}

