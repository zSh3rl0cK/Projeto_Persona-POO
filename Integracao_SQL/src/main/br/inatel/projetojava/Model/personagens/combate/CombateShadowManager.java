package main.br.inatel.projetojava.Model.personagens.combate;

import main.br.inatel.projetojava.Model.exceptions.InvalidMenuInputException;
import main.br.inatel.projetojava.Model.personagens.abstratos.UsuarioPersona;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Protagonista;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Usuarios;
import main.br.inatel.projetojava.Model.personas.seres.Shadow;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class CombateShadowManager {
    /*private static final Random random = new Random();

    public static void iniciarCombateShadow(ArrayList<UsuarioPersona> grupo1, List<Shadow> shadows) {
        int turno = 1;
        boolean turnoDoGrupo1 = true;
        boolean combate = true;

        Protagonista protagonista = encontrarProtagonista(grupo1);

        if (protagonista == null) {
            System.out.println(ANSI_RED + "Erro: O grupo dos aliados precisa conter um protagonista!" + ANSI_RESET);
            return;
        }

        System.out.println(ANSI_BLUE + "\n--- Combate contra Shadows iniciado! ---" + ANSI_RESET);
        mostrarStatusBatalha(grupo1, shadows);

        while (combate && !todasShadowsDerrotadas(shadows) && protagonista.getHp() > 0) {
            try {
                System.out.println(ANSI_BLUE + "\n--- Turno " + turno + " ---" + ANSI_RESET);

                if (turnoDoGrupo1) {
                    System.out.println(ANSI_BLUE + "Turno dos aliados:" + ANSI_RESET);
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

                        Shadow alvo = escolherAlvoShadow(shadows);
                        if (alvo == null) break;

                        combate = executarTurnoContraShadow(membro, alvo, turno);
                        if (!combate) break;
                    }
                } else {
                    System.out.println(ANSI_BLUE + "Turno das Shadows:" + ANSI_RESET);
                    for (Shadow shadow : shadows) {
                        if (shadow.getHp() <= 0) continue;

                        // Shadow escolhe alvo automaticamente
                        UsuarioPersona alvo = escolherAlvoAutomatico(grupo1);
                        if (alvo == null) break;

                        combate = executarTurnoShadowAutomatico(shadow, alvo);

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
        }

        // Resultado do combate
        if (protagonista.getHp() <= 0) {
            System.out.println(ANSI_RED + "\n" + protagonista.getNome() + " caiu em batalha. O grupo recua." + ANSI_RESET);
            System.out.println(ANSI_RED + "GAME OVER - As Shadows emergiram vitoriosas..." + ANSI_RESET);
        } else if (todasShadowsDerrotadas(shadows)) {
            System.out.println(ANSI_GREEN + "\nParabéns! Todas as Shadows foram derrotadas!");
            System.out.println("Você purificou a área e ganhou experiência valiosa!" + ANSI_RESET);

            // Bonus por derrotar Shadows
            int expGanha = calcularExperienciaShadows(shadows);
            System.out.println(ANSI_CYAN + "Experiência ganha: " + expGanha + " pontos!" + ANSI_RESET);
        }

        System.out.println(ANSI_BLUE + "--- Combate Encerrado ---" + ANSI_RESET);
    }

    private static boolean executarTurnoContraShadow(UsuarioPersona atacante, Shadow defensor, int turno) {
        System.out.println(ANSI_BLUE + "\nÉ o turno de " + atacante.getNome() + ANSI_RESET);
        System.out.println(ANSI_CYAN + "Alvo: " + defensor.getNome() + " (HP: " + defensor.getHp() + ")" + ANSI_RESET);

        if (atacante instanceof Protagonista protagonista) {
            return atacante.agirShadow(turno, protagonista.getPersona_atual(), defensor);
        }
        else if (atacante instanceof Usuarios usuario) {
            return atacante.agirShadow(turno, usuario.getPersonas(), defensor);
        }
        return true;
    }

    private static boolean executarTurnoShadowAutomatico(Shadow atacante, UsuarioPersona defensor) {
        System.out.println(ANSI_RED + "\n" + atacante.getNome() + " se prepara para atacar!" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "Alvo: " + defensor.getNome() + " (HP: " + defensor.getHp() + ")" + ANSI_RESET);

        // Shadow decide o tipo de ataque baseado em suas habilidades
        boolean usarHabilidadeEspecial = random.nextDouble() < 0.6; // 60% chance de usar habilidade especial

        if (usarHabilidadeEspecial && !atacante.getHabilidades().isEmpty()) {
            String habilidade = String.valueOf(atacante.getHabilidades().get(random.nextInt(atacante.getHabilidades().size())));
            System.out.println(ANSI_RED + atacante.getNome() + " usa " + habilidade + " contra " + defensor.getNome() + "!" + ANSI_RESET);

            // Calcular dano baseado no tipo de habilidade
            int dano = calcularDanoShadow(atacante, habilidade);
            aplicarDanoAoAlvo(defensor, dano);

        } else {
            System.out.println(ANSI_RED + atacante.getNome() + " executa um ataque físico contra " + defensor.getNome() + "!" + ANSI_RESET);

            int dano = calcularDanoFisicoShadow(atacante);
            aplicarDanoAoAlvo(defensor, dano);
        }

        return true;
    }

    private static int calcularDanoShadow(Shadow shadow, String tipoHabilidade) {
        int danoBase = shadow.getDano();
        double multiplicador = 1.0;

        // Ajustar multiplicador baseado no tipo de habilidade
        switch (tipoHabilidade.toLowerCase()) {
            case "magic" -> multiplicador = 1.3;
            case "physical" -> multiplicador = 1.1;
            case "mental" -> multiplicador = 0.9;
            case "ailment" -> multiplicador = 0.8;
        }

        int dano = (int) (danoBase * multiplicador + random.nextInt(10));
        return Math.max(dano, 1); // Dano mínimo de 1
    }

    private static int calcularDanoFisicoShadow(Shadow shadow) {
        return (shadow.getDano() + random.nextInt(15));
    }

    private static void aplicarDanoAoAlvo(UsuarioPersona alvo, int dano) {
        int hpAntes = (int) alvo.getHp();
        alvo.setHp(Math.max(0, alvo.getHp() - dano));
        int hpDepois = (int) alvo.getHp();

        System.out.println(ANSI_YELLOW + alvo.getNome() + " recebeu " + dano + " de dano!");
        System.out.println("HP: " + hpAntes + " → " + hpDepois + ANSI_RESET);

        if (hpDepois <= 0) {
            System.out.println(ANSI_RED + alvo.getNome() + " foi derrotado!" + ANSI_RESET);
        }
    }

    private static Shadow escolherAlvoShadow(List<Shadow> shadows) {
        System.out.println(ANSI_CYAN + "\nEscolha a Shadow alvo:" + ANSI_RESET);
        ArrayList<Shadow> alvosDisponiveis = new ArrayList<>();

        for (Shadow shadow : shadows) {
            if (shadow.getHp() > 0) {
                alvosDisponiveis.add(shadow);
            }
        }

        if (alvosDisponiveis.isEmpty()) {
            System.out.println(ANSI_RED + "Nenhuma Shadow disponível." + ANSI_RESET);
            return null;
        }

        for (int i = 0; i < alvosDisponiveis.size(); i++) {
            Shadow shadow = alvosDisponiveis.get(i);
            System.out.println((i + 1) + " - " + shadow.getNome() + " (HP: " + shadow.getHp() +
                    ", Arcana: " + shadow.getArcana() + ")");
        }

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        Shadow alvoEscolhido = null;

        while (alvoEscolhido == null) {
            System.out.print(ANSI_CYAN + "Digite o número ou nome da Shadow: " + ANSI_RESET);
            String entrada = scanner.nextLine().trim();

            try {
                int indice = Integer.parseInt(entrada) - 1;
                if (indice >= 0 && indice < alvosDisponiveis.size()) {
                    alvoEscolhido = alvosDisponiveis.get(indice);
                } else {
                    System.out.println(ANSI_RED + "Índice inválido. Tente novamente." + ANSI_RESET);
                }
            } catch (NumberFormatException e) {
                for (Shadow shadow : alvosDisponiveis) {
                    if (shadow.getNome().equalsIgnoreCase(entrada)) {
                        alvoEscolhido = shadow;
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

    private static UsuarioPersona escolherAlvoAutomatico(ArrayList<UsuarioPersona> grupo) {
        ArrayList<UsuarioPersona> alvosDisponiveis = new ArrayList<>();

        for (UsuarioPersona p : grupo) {
            if (p.getHp() > 0) {
                alvosDisponiveis.add(p);
            }
        }

        if (alvosDisponiveis.isEmpty()) {
            return null;
        }

        // Shadow escolhe alvo aleatório entre os disponíveis
        UsuarioPersona alvoEscolhido = alvosDisponiveis.get(random.nextInt(alvosDisponiveis.size()));
        System.out.println(ANSI_RED + "Shadow mira em " + alvoEscolhido.getNome() + "!" + ANSI_RESET);

        return alvoEscolhido;
    }

    private static boolean todasShadowsDerrotadas(List<Shadow> shadows) {
        for (Shadow shadow : shadows) {
            if (shadow.getHp() > 0)
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

    private static void mostrarStatusBatalha(ArrayList<UsuarioPersona> aliados, List<Shadow> shadows) {
        System.out.println(ANSI_GREEN + "\n=== ALIADOS ===" + ANSI_RESET);
        for (UsuarioPersona aliado : aliados) {
            System.out.println(aliado.getNome() + " - HP: " + aliado.getHp());
        }

        System.out.println(ANSI_RED + "\n=== SHADOWS ===" + ANSI_RESET);
        for (Shadow shadow : shadows) {
            System.out.println(shadow.getNome() + " - HP: " + shadow.getHp() +
                    " (Arcana: " + shadow.getArcana() +
                    ", Fraqueza: " + shadow.getFraqueza() +
                    ", Resistência: " + shadow.getResistencia() + ")");
        }
        System.out.println();
    }

    private static int calcularExperienciaShadows(List<Shadow> shadows) {
        int expTotal = 0;
        for (Shadow shadow : shadows) {
            // Experiência baseada no HP máximo e ataque da Shadow
            expTotal += (int) (shadow.getHp() * 0.5 + shadow.getDano() * 2);
        }
        return expTotal;
    }
    */
}