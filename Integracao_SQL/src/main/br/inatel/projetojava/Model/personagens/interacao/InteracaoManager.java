package main.br.inatel.projetojava.Model.personagens.interacao;

import main.br.inatel.projetojava.Model.personagens.abstratos.SerHumano;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Protagonista;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Usuarios;
import main.br.inatel.projetojava.Model.personagens.NPC;
import main.br.inatel.projetojava.Model.threads.AudioManager;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public abstract class InteracaoManager {

    public static void interagir(SerHumano alvo, Protagonista protagonista) {
        try {
            if (alvo instanceof Usuarios usuario) {
                if (!usuario.isVilao()) {
                    System.out.println(ANSI_YELLOW + protagonista.getNome() + " está saindo com " + usuario.getNome() + " da SEES" + ANSI_RESET);
                    String nome = alvo.getNome();

                    if (nome.equals("Koromaru") || nome.equals("Akihiko") || nome.equals("Junpei") ||
                            nome.equals("Ken") || nome.equals("Shinjiro")) {
                        System.out.println(ANSI_GREEN + "O nível da arcana 'The Fool' subiu de " + usuario.getNivelConfidant() + " para " + (usuario.getNivelConfidant() + 1) + ANSI_RESET);
                        AudioManager.getInstance().playLevelUpSFX();
                        usuario.setNivelConfidant(usuario.getNivelConfidant() + 1);
                    }
                    else if (nome.startsWith("Yukari")) { // Sobrenome quebra a lógica de switch
                        int nivel = usuario.getNivelConfidant();
                        switch (nivel) {
                            case 1:
                                System.out.println(ANSI_CYAN + "Yukari te agradece por ouvi-la sobre suas preocupações com sua mãe." + ANSI_RESET);
                                break;
                            case 2:
                                System.out.println(ANSI_CYAN + "Vocês estudam juntos na biblioteca. Yukari compartilha suas inseguranças sobre as provas." + ANSI_RESET);
                                break;
                            case 3:
                                System.out.println(ANSI_CYAN + "Yukari te chama para caminhar no parque. Ela parece mais confortável ao seu lado." + ANSI_RESET);
                                break;
                            case 4:
                                System.out.println(ANSI_CYAN + "Vocês conversam sobre o passado dela e como ela lida com a perda do pai." + ANSI_RESET);
                                break;
                            case 5:
                                System.out.println(ANSI_CYAN + "Yukari te convida para um festival. Vocês se divertem juntos e ela sorri mais do que o normal." + ANSI_RESET);
                                break;
                            case 6:
                                System.out.println(ANSI_CYAN + "Ela começa a confiar mais em você, compartilhando sonhos e medos." + ANSI_RESET);
                                break;
                            case 7:
                                System.out.println(ANSI_CYAN + "Yukari te dá um presente especial e diz que aprecia muito sua amizade." + ANSI_RESET);
                                break;
                            case 8:
                                System.out.println(ANSI_CYAN + "Vocês têm uma conversa sincera sobre sentimentos. Yukari demonstra sinais de interesse romântico." + ANSI_RESET);
                                break;
                            case 9:
                                System.out.println(ANSI_CYAN + "Yukari te convida para um encontro. Ela fica nervosa, mas feliz ao seu lado." + ANSI_RESET);
                                break;
                            case 10:
                                System.out.println(ANSI_CYAN + "Parabéns! Seu relacionamento com Yukari atingiu o nível máximo. Agora vocês podem ficar juntos!" + ANSI_RESET);
                                break;
                            default:
                                System.out.println(ANSI_CYAN + "Vocês continuam fortalecendo a amizade." + ANSI_RESET);
                                break;
                        }
                        if (nivel < 10) {
                            System.out.println(ANSI_GREEN + "O nível da arcana 'Lovers' subiu de " + nivel + " para " + (nivel + 1) + ANSI_RESET);
                            AudioManager.getInstance().playLevelUpSFX();
                            usuario.setNivelConfidant(nivel + 1);
                        } else {
                            System.out.println(ANSI_GREEN + "Seu vínculo com Yukari já está no máximo!" + ANSI_RESET);
                        }
                    }
                    else if (nome.startsWith("Mitsuru")) {
                        int nivel = usuario.getNivelConfidant();
                        switch (nivel) {
                            case 1:
                                System.out.println(ANSI_CYAN + "Mitsuru te agradece por ajudá-la a lidar com as responsabilidades do conselho estudantil." + ANSI_RESET);
                                break;
                            case 2:
                                System.out.println(ANSI_CYAN + "Vocês conversam sobre universidades estrangeiras. Mitsuru compartilha suas ambições." + ANSI_RESET);
                                break;
                            case 3:
                                System.out.println(ANSI_CYAN + "Mitsuru te oferece uma carona em sua motocicleta. Vocês conversam durante o trajeto." + ANSI_RESET);
                                break;
                            case 4:
                                System.out.println(ANSI_CYAN + "Ela revela um pouco sobre a pressão de ser herdeira da Kirijo." + ANSI_RESET);
                                break;
                            case 5:
                                System.out.println(ANSI_CYAN + "Vocês visitam um café elegante. Mitsuru parece relaxar na sua companhia." + ANSI_RESET);
                                break;
                            case 6:
                                System.out.println(ANSI_CYAN + "Mitsuru começa a confiar mais em você, compartilhando dúvidas pessoais." + ANSI_RESET);
                                break;
                            case 7:
                                System.out.println(ANSI_CYAN + "Ela te convida para um concerto de música clássica." + ANSI_RESET);
                                break;
                            case 8:
                                System.out.println(ANSI_CYAN + "Mitsuru admite que aprecia muito sua amizade e companhia." + ANSI_RESET);
                                break;
                            case 9:
                                System.out.println(ANSI_CYAN + "Vocês têm uma conversa séria sobre o futuro. Mitsuru demonstra sentimentos especiais." + ANSI_RESET);
                                break;
                            case 10:
                                System.out.println(ANSI_CYAN + "Parabéns! Seu relacionamento com Mitsuru atingiu o nível máximo. Agora vocês podem ficar juntos!" + ANSI_RESET);
                                break;
                            default:
                                System.out.println(ANSI_CYAN + "Vocês continuam fortalecendo a amizade." + ANSI_RESET);
                                break;
                        }
                        if (nivel < 10) {
                            System.out.println(ANSI_GREEN + "O nível da arcana 'Empress' subiu de " + nivel + " para " + (nivel + 1) + ANSI_RESET);
                            AudioManager.getInstance().playLevelUpSFX();
                            usuario.setNivelConfidant(nivel + 1);
                        } else {
                            System.out.println(ANSI_GREEN + "Seu vínculo com Mitsuru já está no máximo!" + ANSI_RESET);
                        }
                    }
                    else if (nome.startsWith("Fuuka")) {
                        int nivel = usuario.getNivelConfidant();
                        switch (nivel) {
                            case 1:
                                System.out.println(ANSI_CYAN + "Fuuka agradece por ajudá-la a se enturmar com os colegas." + ANSI_RESET);
                                break;
                            case 2:
                                System.out.println(ANSI_CYAN + "Vocês cozinham juntos na cozinha do dormitório. Fuuka sorri, um pouco envergonhada." + ANSI_RESET);
                                break;
                            case 3:
                                System.out.println(ANSI_CYAN + "Fuuka te mostra como ela cuida das plantas no terraço." + ANSI_RESET);
                                break;
                            case 4:
                                System.out.println(ANSI_CYAN + "Ela compartilha suas inseguranças sobre suas habilidades de suporte." + ANSI_RESET);
                                break;
                            case 5:
                                System.out.println(ANSI_CYAN + "Vocês vão juntos à biblioteca. Fuuka se sente mais confiante ao seu lado." + ANSI_RESET);
                                break;
                            case 6:
                                System.out.println(ANSI_CYAN + "Fuuka te dá um lanche caseiro como agradecimento." + ANSI_RESET);
                                break;
                            case 7:
                                System.out.println(ANSI_CYAN + "Ela te convida para um passeio pelo bairro, mostrando lugares que gosta." + ANSI_RESET);
                                break;
                            case 8:
                                System.out.println(ANSI_CYAN + "Fuuka confessa que se sente mais forte com você por perto." + ANSI_RESET);
                                break;
                            case 9:
                                System.out.println(ANSI_CYAN + "Vocês têm uma conversa emocionante sobre sonhos e futuro." + ANSI_RESET);
                                break;
                            case 10:
                                System.out.println(ANSI_CYAN + "Parabéns! Seu relacionamento com Fuuka atingiu o nível máximo. Agora vocês podem ficar juntos!" + ANSI_RESET);
                                break;
                            default:
                                System.out.println(ANSI_CYAN + "Vocês continuam fortalecendo a amizade." + ANSI_RESET);
                                break;
                        }
                        if (nivel < 10) {
                            System.out.println(ANSI_GREEN + "O nível da arcana 'Priestess' subiu de " + nivel + " para " + (nivel + 1) + ANSI_RESET);
                            AudioManager.getInstance().playLevelUpSFX();
                            usuario.setNivelConfidant(nivel + 1);
                        } else {
                            System.out.println(ANSI_GREEN + "Seu vínculo com Fuuka já está no máximo!" + ANSI_RESET);
                        }
                    }
                    else if (nome.startsWith("Aigis")) {
                        int nivel = usuario.getNivelConfidant();
                        switch (nivel) {
                            case 1:
                                System.out.println(ANSI_CYAN + "Aigis demonstra curiosidade sobre emoções humanas e faz perguntas para você." + ANSI_RESET);
                                break;
                            case 2:
                                System.out.println(ANSI_CYAN + "Vocês passeiam juntos, e Aigis observa atentamente o comportamento das pessoas." + ANSI_RESET);
                                break;
                            case 3:
                                System.out.println(ANSI_CYAN + "Aigis tenta cozinhar algo para você, mas o resultado é... interessante." + ANSI_RESET);
                                break;
                            case 4:
                                System.out.println(ANSI_CYAN + "Ela compartilha suas dúvidas sobre o significado de amizade." + ANSI_RESET);
                                break;
                            case 5:
                                System.out.println(ANSI_CYAN + "Aigis começa a entender melhor os sentimentos humanos." + ANSI_RESET);
                                break;
                            case 6:
                                System.out.println(ANSI_CYAN + "Vocês conversam sobre o passado de Aigis e sua missão." + ANSI_RESET);
                                break;
                            case 7:
                                System.out.println(ANSI_CYAN + "Aigis te protege durante uma situação inesperada, mostrando preocupação genuína." + ANSI_RESET);
                                break;
                            case 8:
                                System.out.println(ANSI_CYAN + "Ela diz que sente algo diferente quando está com você." + ANSI_RESET);
                                break;
                            case 9:
                                System.out.println(ANSI_CYAN + "Aigis expressa que deseja ficar ao seu lado, não por programação, mas por vontade própria." + ANSI_RESET);
                                break;
                            case 10:
                                System.out.println(ANSI_CYAN + "Parabéns! Seu relacionamento com Aigis atingiu o nível máximo. Agora vocês podem ficar juntos!" + ANSI_RESET);
                                break;
                            default:
                                System.out.println(ANSI_CYAN + "Vocês continuam fortalecendo a amizade." + ANSI_RESET);
                                break;
                        }
                        if (nivel < 10) {
                            System.out.println(ANSI_GREEN + "O nível do confidant subiu de " + nivel + " para " + (nivel + 1) + ANSI_RESET);
                            AudioManager.getInstance().playLevelUpSFX();
                            usuario.setNivelConfidant(nivel + 1);
                        } else {
                            System.out.println(ANSI_GREEN + "Seu vínculo com Aigis já está no máximo!" + ANSI_RESET);
                        }
                    }
                    else {
                        // Caso padrão para outros membros da SEES
                        System.out.println(ANSI_GREEN + "O nível da arcana 'Aeon' subiu de " + usuario.getNivelConfidant() + " para " + (usuario.getNivelConfidant() + 1) + ANSI_RESET);
                        AudioManager.getInstance().playLevelUpSFX();
                        usuario.setNivelConfidant(usuario.getNivelConfidant() + 1);
                    }

                } else {
                    System.out.println(ANSI_YELLOW + protagonista.getNome() + " tenta lutar com " + usuario.getNome() + " da Strega, mas vilão solta uma bomba de fumaça e some!" + ANSI_RESET);
                }
            } else if (alvo instanceof NPC npc) {
                System.out.println(ANSI_YELLOW + protagonista.getNome() + " está saindo com " + npc.getNome() + "!" + ANSI_RESET);
                System.out.println(ANSI_GREEN + "O nível da arcana '" + npc.getArcana() + "' subiu de " + npc.getNivelConfidant() + " para " + (npc.getNivelConfidant() + 1) + ANSI_RESET);
                AudioManager.getInstance().playLevelUpSFX();
                npc.setNivelConfidant(npc.getNivelConfidant() + 1);
            } else {
                throw new IllegalArgumentException(ANSI_RED + "O alvo deve ser um Usuário ou NPC!" + ANSI_RESET);
            }
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}