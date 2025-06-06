package main.br.inatel.projetojava.Model.personagens.combate;

import main.br.inatel.projetojava.Model.exceptions.InvalidMenuInputException;
import main.br.inatel.projetojava.Model.personagens.abstratos.UsuarioPersona;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Protagonista;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Usuarios;
import java.util.ArrayList;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class CombateManager {

    public static void iniciarCombate(ArrayList<UsuarioPersona> grupo1, ArrayList<UsuarioPersona> grupo2) {
        int turno = 1;
        boolean turnoDoGrupo1 = true;
        boolean combate = true;

        Protagonista protagonista = encontrarProtagonista(grupo1);

        if (protagonista == null) {
            System.out.println(ANSI_RED + "Erro: O grupo 1 precisa conter um protagonista!" + ANSI_RESET);
            return;
        }

        System.out.println(ANSI_BLUE + "\n--- Combate iniciado! ---" + ANSI_RESET);

        while (combate && !todosDerrotados(grupo2) && protagonista.getHp() > 0) {
            try {
                System.out.println(ANSI_BLUE + "\n--- Turno " + turno + " ---" + ANSI_RESET);

                if (turnoDoGrupo1) {
                    System.out.println(ANSI_BLUE + "Turno do Grupo 1:" + ANSI_RESET);
                    for (UsuarioPersona membro : grupo1) {
                        if (membro.getHp() <= 0) {
                            System.out.println(ANSI_YELLOW + membro.getNome() + " está incapacitado e não pode agir." + ANSI_RESET);
                            continue;
                        }

                        // Caso o protagonista tenha morrido entre turnos
                        if (protagonista.getHp() <= 0) {
                            System.out.println(ANSI_YELLOW + protagonista.getNome() + " foi derrotado! Combate encerrado." + ANSI_RESET);
                            combate = false;
                            break;
                        }

                        UsuarioPersona alvo = escolherAlvo(grupo2);
                        if (alvo == null) break;

                        combate = executarTurnoIndividual(membro, alvo, turno);
                        if (!combate) break;
                    }
                } else {
                    System.out.println(ANSI_BLUE + "Turno do Grupo 2:" + ANSI_RESET);
                    for (UsuarioPersona membro : grupo2) {
                        if (membro.getHp() <= 0) continue;

                        UsuarioPersona alvo = escolherAlvo(grupo1);
                        if (alvo == null) break;

                        combate = executarTurnoIndividual(membro, alvo, turno);

                        // Caso o protagonista morra nesse turno
                        if (protagonista.getHp() <= 0) {
                            System.out.println(ANSI_YELLOW + protagonista.getNome() + " foi derrotado! Combate encerrado." + ANSI_RESET);
                            combate = false;
                            break;
                        }

                        if (!combate) break;
                    }
                }

                turnoDoGrupo1 = !turnoDoGrupo1;
                turno++;
            }
            catch (InvalidMenuInputException e) {
                System.out.println(ANSI_RED + "Erro: " + e.getMessage() + ANSI_RESET);
            }

            if (protagonista.getHp() <= 0) {
                System.out.println(ANSI_YELLOW + protagonista.getNome() + " caiu em batalha. O grupo recua." + ANSI_RESET);
            } else if (todosDerrotados(grupo2)) {
                System.out.println(ANSI_GREEN + "Grupo 2 foi derrotado!" + ANSI_RESET);
            }

            System.out.println(ANSI_BLUE + "--- Combate Encerrado ---" + ANSI_RESET);
        }
    }

    private static boolean executarTurnoIndividual(UsuarioPersona atacante, UsuarioPersona defensor, int turno) {
        System.out.println(ANSI_BLUE + "\nÉ o turno de " + atacante.getNome() + ANSI_RESET);

        if (atacante instanceof Protagonista protagonista) {
            return atacante.agir(turno, protagonista.getPersona_atual(), defensor);
        }
        else if (atacante instanceof Usuarios usuario) {
            return atacante.agir(turno, usuario.getPersonas(), defensor);
        }
        return true;
    }

    private static UsuarioPersona escolherAlvo(ArrayList<UsuarioPersona> grupo) {
        System.out.println(ANSI_CYAN + "\nEscolha o alvo:" + ANSI_RESET);
        ArrayList<UsuarioPersona> alvosDisponiveis = new ArrayList<>();

        for (UsuarioPersona p : grupo) {
            if (p.getHp() > 0) {
                alvosDisponiveis.add(p);
            }
        }

        if (alvosDisponiveis.isEmpty()) {
            System.out.println(ANSI_RED + "Nenhum alvo disponível." + ANSI_RESET);
            return null;
        }

        for (int i = 0; i < alvosDisponiveis.size(); i++) {
            UsuarioPersona p = alvosDisponiveis.get(i);
            System.out.println((i + 1) + " - " + p.getNome() + " (HP: " + p.getHp() + ")");
        }

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        UsuarioPersona alvoEscolhido = null;

        while (alvoEscolhido == null) {
            System.out.print(ANSI_CYAN + "Digite o número ou nome do alvo: " + ANSI_RESET);
            String entrada = scanner.nextLine().trim();

            try {
                int indice = Integer.parseInt(entrada) - 1;
                if (indice >= 0 && indice < alvosDisponiveis.size()) {
                    alvoEscolhido = alvosDisponiveis.get(indice);
                } else {
                    System.out.println(ANSI_RED + "Índice inválido. Tente novamente." + ANSI_RESET);
                }
            } catch (NumberFormatException e) {
                for (UsuarioPersona p : alvosDisponiveis) {
                    if (p.getNome().equalsIgnoreCase(entrada)) {
                        alvoEscolhido = p;
                        break;
                    }
                }
                if (alvoEscolhido == null) {
                    System.out.println(ANSI_RED + "Nome inválido. Tente novamente." + ANSI_RESET);
                }
            }
        }

        return alvoEscolhido;
    }


    private static boolean todosDerrotados(ArrayList<UsuarioPersona> grupo) {
        for (UsuarioPersona p : grupo) {
            if (p.getHp() > 0)
                return false;
        }
        return true;
    }

    private static Protagonista encontrarProtagonista(ArrayList<UsuarioPersona> grupo) {
        for (UsuarioPersona p : grupo) {
            if (p instanceof Protagonista protagonista) {
                return protagonista;
            }
        }
        return null;
    }
}