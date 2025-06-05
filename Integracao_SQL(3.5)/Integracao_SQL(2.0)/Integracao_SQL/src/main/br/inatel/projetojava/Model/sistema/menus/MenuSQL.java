package main.br.inatel.projetojava.Model.sistema.menus;

import main.br.inatel.projetojava.Model.exceptions.InvalidMenuInputException;

import java.util.InputMismatchException;
import java.util.Scanner;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class MenuSQL {
    public static int opcao;

    public static int mostrarMenuSQL() throws InvalidMenuInputException {
        Scanner sc = new Scanner(System.in);

        System.out.println(ANSI_PURPLE +
                "╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                      🗃️ MENU DE OPERAÇÕES SQL POR ENTIDADE                 ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ [ 1] ➕ Inserir Protagonista                                              ║");
        System.out.println("║ [ 2] 🔄 Atualizar Protagonista                                            ║");
        System.out.println("║ [ 3] ❌ Deletar Protagonista                                              ║");
        System.out.println("║ [ 4] 🔍 Selecionar (Buscar) Protagonista                                  ║");
        System.out.println("║ [ 5] ➕ Inserir NPC                                                       ║");
        System.out.println("║ [ 6] 🔄 Atualizar NPC                                                     ║");
        System.out.println("║ [ 7] ❌ Deletar NPC                                                       ║");
        System.out.println("║ [ 8] 🔍 Selecionar (Buscar) NPC                                           ║");
        System.out.println("║ [ 9] ➕ Inserir Usuário                                                   ║");
        System.out.println("║ [10] 🔄 Atualizar Usuário                                                 ║");
        System.out.println("║ [11] ❌ Deletar Usuário                                                   ║");
        System.out.println("║ [12] 🔍 Selecionar (Buscar) Usuário                                       ║");
        System.out.println("║ [ 0] 🚪 Sair                                                              ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════╝" + ANSI_RESET);

        System.out.print(ANSI_CYAN + "👉 Escolha uma opção: " + ANSI_RESET);

        try {
            opcao = sc.nextInt();
            if (opcao < 0 || opcao > 12) {
                throw new InvalidMenuInputException("Valor inválido! Tente novamente...");
            }
            sc.nextLine(); // limpa o buffer

            if (opcao == 0) {
                System.out.println(ANSI_YELLOW + "Saindo do menu SQL..." + ANSI_RESET);
            }

            return opcao;

        } catch (InputMismatchException e) {
            sc.nextLine(); // limpa o buffer para evitar erro contínuo
            throw new InvalidMenuInputException("Por favor, insira um número inteiro válido...");
        }
    }

}