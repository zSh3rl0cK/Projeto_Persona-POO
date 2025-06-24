package main.br.inatel.projetojava.Model.sistema.menus;

import main.br.inatel.projetojava.Model.exceptions.InvalidMenuInputException;

import java.sql.SQLOutput;
import java.util.InputMismatchException;
import java.util.Scanner;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class MenuSQL {
    public static int opcao;

    public static int mostrarMenuSQL() throws InvalidMenuInputException {
        Scanner sc = new Scanner(System.in);

        System.out.println(ANSI_PURPLE +
                "╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                      🗃 MENU DE OPERAÇÕES SQL POR ENTIDADE                 ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ [1] ➕ Operações de INSERT                                                ║");
        System.out.println("║ [2] 🔄 Operações de UPDATE                                                ║");
        System.out.println("║ [3] ❌ Operações de DELETE                                                ║");
        System.out.println("║ [4] 🔍 Operações de SELECT                                                ║");
        System.out.println("║ [0] 🚪 Sair                                                               ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════╝" + ANSI_RESET);

        System.out.print(ANSI_CYAN + "👉 Escolha uma opção: " + ANSI_RESET);

        try {
            opcao = sc.nextInt();
            if (opcao < 0 || opcao > 5) {
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

    public static int mostrarMenuInsert() throws InvalidMenuInputException {
        Scanner sc = new Scanner(System.in);

        System.out.println(ANSI_GREEN +
                "╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        ➕ MENU DE OPERAÇÕES INSERT                         ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ [1] ➕ Inserir Protagonista                                              ║");
        System.out.println("║ [2] ➕ Inserir NPC                                                       ║");
        System.out.println("║ [3] ➕ Inserir Usuário                                                   ║");
        System.out.println("║ [0] ⬅ Voltar ao menu anterior                                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════╝" + ANSI_RESET);

        System.out.print(ANSI_CYAN + "👉 Escolha uma opção: " + ANSI_RESET);

        try {
            opcao = sc.nextInt();
            if (opcao < 0 || opcao > 3) {
                throw new InvalidMenuInputException("Valor inválido! Tente novamente...");
            }
            sc.nextLine(); // limpa o buffer

            if (opcao == 0) {
                System.out.println(ANSI_YELLOW + "Voltando ao menu anterior..." + ANSI_RESET);
            }

            return opcao;

        } catch (InputMismatchException e) {
            sc.nextLine(); // limpa o buffer para evitar erro contínuo
            throw new InvalidMenuInputException("Por favor, insira um número inteiro válido...");
        }
    }

    public static int mostrarMenuUpdate() throws InvalidMenuInputException {
        Scanner sc = new Scanner(System.in);

        System.out.println(ANSI_BLUE +
                "╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        🔄 MENU DE OPERAÇÕES UPDATE                         ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ [1] 🔄 Atualizar Protagonista                                            ║");
        System.out.println("║ [2] 🔄 Atualizar NPC                                                     ║");
        System.out.println("║ [3] 🔄 Atualizar Usuário                                                 ║");
        System.out.println("║ [0] ⬅ Voltar ao menu anterior                                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════╝" + ANSI_RESET);

        System.out.print(ANSI_CYAN + "👉 Escolha uma opção: " + ANSI_RESET);

        try {
            opcao = sc.nextInt();
            if (opcao < 0 || opcao > 3) {
                throw new InvalidMenuInputException("Valor inválido! Tente novamente...");
            }
            sc.nextLine(); // limpa o buffer

            if (opcao == 0) {
                System.out.println(ANSI_YELLOW + "Voltando ao menu anterior..." + ANSI_RESET);
            }

            return opcao;

        } catch (InputMismatchException e) {
            sc.nextLine(); // limpa o buffer para evitar erro contínuo
            throw new InvalidMenuInputException("Por favor, insira um número inteiro válido...");
        }
    }

    public static int mostrarMenuDelete() throws InvalidMenuInputException {
        Scanner sc = new Scanner(System.in);

        System.out.println(ANSI_RED +
                "╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        ❌ MENU DE OPERAÇÕES DELETE                         ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ [1] ❌ Deletar Protagonista                                              ║");
        System.out.println("║ [2] ❌ Deletar NPC                                                       ║");
        System.out.println("║ [3] ❌ Deletar Usuário                                                   ║");
        System.out.println("║ [0] ⬅ Voltar ao menu anterior                                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════╝" + ANSI_RESET);

        System.out.print(ANSI_CYAN + "👉 Escolha uma opção: " + ANSI_RESET);

        try {
            opcao = sc.nextInt();
            if (opcao < 0 || opcao > 3) {
                throw new InvalidMenuInputException("Valor inválido! Tente novamente...");
            }
            sc.nextLine(); // limpa o buffer

            if (opcao == 0) {
                System.out.println(ANSI_YELLOW + "Voltando ao menu anterior..." + ANSI_RESET);
            }

            return opcao;

        } catch (InputMismatchException e) {
            sc.nextLine(); // limpa o buffer para evitar erro contínuo
            throw new InvalidMenuInputException("Por favor, insira um número inteiro válido...");
        }
    }

    public static int mostrarMenuSelect() throws InvalidMenuInputException {
        Scanner sc = new Scanner(System.in);

        System.out.println(ANSI_CYAN +
                "╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        🔍 MENU DE OPERAÇÕES SELECT                         ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ [1] 🔍 Selecionar (Buscar) Protagonista                                    ║");
        System.out.println("║ [2] 📋 Listar todos os NPCs                                                ║");
        System.out.println("║ [3] 👥 Listar todos os Usuários                                            ║");
        System.out.println("║ [4] 🎴 Buscar Arcana de NPC                                                ║");
        System.out.println("║ [5] 🎭 Selecionar (Buscar) Personas de Protagonista                        ║");
        System.out.println("║ [0] ⬅ Voltar ao menu anterior                                             ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════╝" + ANSI_RESET);

        System.out.print(ANSI_CYAN + "👉 Escolha uma opção: " + ANSI_RESET);

        try {
            opcao = sc.nextInt();
            if (opcao < 0 || opcao > 5) {
                throw new InvalidMenuInputException("Valor inválido! Tente novamente...");
            }
            sc.nextLine(); // limpa o buffer

            if (opcao == 0) {
                System.out.println(ANSI_YELLOW + "Voltando ao menu anterior..." + ANSI_RESET);
            }

            return opcao;

        } catch (InputMismatchException e) {
            sc.nextLine(); // limpa o buffer para evitar erro contínuo
            throw new InvalidMenuInputException("Por favor, insira um número inteiro válido...");
        }
    }
}
