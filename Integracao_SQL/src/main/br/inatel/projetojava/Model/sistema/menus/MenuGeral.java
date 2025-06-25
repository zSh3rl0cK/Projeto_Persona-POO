package main.br.inatel.projetojava.Model.sistema.menus;

import main.br.inatel.projetojava.DAO.*;
import main.br.inatel.projetojava.Model.exceptions.InvalidMenuInputException;
import main.br.inatel.projetojava.Model.itens.lojas.FarmaciaAohige;
import main.br.inatel.projetojava.Model.itens.geral.Itens;
import main.br.inatel.projetojava.Model.itens.lojas.LojadeArmas;
import main.br.inatel.projetojava.Model.itens.equipaveis.Arma;
import main.br.inatel.projetojava.Model.itens.equipaveis.Equipamento;
import main.br.inatel.projetojava.Model.personagens.abstratos.SerHumano;
import main.br.inatel.projetojava.Model.personagens.abstratos.UsuarioPersona;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Protagonista;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Usuarios;
import main.br.inatel.projetojava.Model.personagens.NPC;
import main.br.inatel.projetojava.Model.personas.seres.Personas;
import main.br.inatel.projetojava.Model.personas.seres.Shadow;
import main.br.inatel.projetojava.Model.sistema.Cidade;
import main.br.inatel.projetojava.Model.sistema.avaliacao.SistemaAvaliacao;
import main.br.inatel.projetojava.Model.sistema.inicializacao.DadosIniciais;
import main.br.inatel.projetojava.Model.sistema.inicializacao.InicializadorDoJogo;
import main.br.inatel.projetojava.Model.threads.AudioManager;

import java.util.*;

import static main.br.inatel.projetojava.Model.itens.lojas.LojaUtil.visitarLoja;
import static main.br.inatel.projetojava.Model.personagens.combate.CombateManager.CombateShadows;
import static main.br.inatel.projetojava.Model.personagens.combate.CombateManager.iniciarCombate;
import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;
import static main.br.inatel.projetojava.Model.sistema.menus.MenuBuscas.mostrar_menu_buscas;
import static main.br.inatel.projetojava.Model.sistema.menus.MenuCidade.mostrarMenuCidade;
import static main.br.inatel.projetojava.Model.sistema.menus.MenuDoJogo.mostrar_menu_principal;
import static main.br.inatel.projetojava.Model.sistema.menus.MenuSQL.*;
import static main.br.inatel.projetojava.Model.sistema.menus.MethodUtil.*;

public class MenuGeral {
    public static void mostrarMenuGeral(){

        DadosIniciais dados = InicializadorDoJogo.carregarTudo();
        // ------------------------------------ Menus: ------------------------------------ //
        Random random = new Random();
        AudioManager.getInstance().setGameStateMusic(AudioManager.GameState.MAIN_MENU);

        // Acesso aos dados
        Protagonista protagonista = dados.getProtagonista();
        Map<String, Usuarios> user = dados.getUsuarios();
        Map<String, NPC> npcs = dados.getNpcs();
        List<Shadow> shadows = dados.getShadows();
        Map<String, Itens> item = dados.getItens();
        LojadeArmas lojaArmas = dados.getLojaArmas();
        FarmaciaAohige farmacia = dados.getFarmacia();
        Cidade tatsumiPort = dados.getTatsumiPort();

        String localAtual = "Dormitório";

        int opcao;
        int sair_jogo;

        Scanner sc = new Scanner(System.in);

        while(true) {
            try {
                sair_jogo = mostrar_menu_principal(sc);
                AudioManager.getInstance().playMenuSelectSFX();

                if (sair_jogo == 0) {
                    AudioManager.getInstance().stopMusic();
                    AudioManager.getInstance().shutdown();
                    AudioManager.getInstance().playMenuBackSFX();
                    SistemaAvaliacao avaliacao = new SistemaAvaliacao();
                    avaliacao.solicitarAvaliacao();
                    break;
                }

                switch (sair_jogo) {
                    case 1 -> {
                        while (true) {
                            AudioManager.getInstance().setGameStateMusic(AudioManager.GameState.INVENTORY);
                            try {
                                opcao = mostrar_menu_buscas();
                                if (opcao == 0) {
                                    AudioManager.getInstance().playMenuBackSFX();
                                    AudioManager.getInstance().stopMusic();
                                    break;
                                }


                                switch (opcao) {
                                    case 1 -> {
                                        System.out.println(ANSI_CYAN + "\n----- Dados do Protagonista -----" + ANSI_RESET);
                                        System.out.println();
                                        protagonista.mostraInfoPersonagem();

                                        // Visualizar detalhes de personas
                                        if (confirmarAcao(sc, "Deseja ver detalhes de alguma persona?")) {
                                            boolean sairVisualizacao = false;
                                            while (!sairVisualizacao) {
                                                System.out.println(ANSI_BLUE + "Lista de Personas:" + ANSI_RESET);
                                                for (int i = 0; i < protagonista.getPersonas().size(); i++) {
                                                    System.out.println(ANSI_BLUE + (i + 1) + ". " + protagonista.getPersonas().get(i).getNome() + ANSI_RESET);
                                                }

                                                try {
                                                    System.out.print(ANSI_GRAY + "Digite o número da persona (ou 0 para cancelar): " + ANSI_RESET);
                                                    int numPersona = sc.nextInt();
                                                    sc.nextLine(); // Limpar buffer

                                                    if (numPersona == 0) {
                                                        sairVisualizacao = true;
                                                    } else if (numPersona > 0 && numPersona <= protagonista.getPersonas().size()) {
                                                        protagonista.getPersonas().get(numPersona - 1).mostrarStatusPersona();
                                                        sairVisualizacao = !confirmarAcao(sc, "Deseja ver outra persona?");
                                                    } else {
                                                        System.out.println(ANSI_RED + "Número inválido! Tente novamente." + ANSI_RESET);
                                                    }
                                                } catch (InputMismatchException e) {
                                                    System.out.println(ANSI_RED + "Erro: Digite apenas números!" + ANSI_RESET);
                                                    sc.nextLine(); // Limpar buffer
                                                }
                                            }
                                        }

                                        // Evoluir Persona
                                        if (confirmarAcao(sc, "Deseja evoluir uma Persona?")) {
                                            boolean sairEvolucao = false;
                                            while (!sairEvolucao) {
                                                System.out.println(ANSI_GRAY + "Qual o nome da Persona que deseja evoluir? (ou digite 'cancelar')" + ANSI_RESET);
                                                String nomePersona = sc.nextLine();

                                                if (nomePersona.equalsIgnoreCase("cancelar")) {
                                                    sairEvolucao = true;
                                                } else {
                                                    boolean encontrada = false;
                                                    for (Personas p : protagonista.getPersonas()) {
                                                        if (p.getNome().equalsIgnoreCase(nomePersona)) {
                                                            protagonista.evoluirPersona(p);
                                                            System.out.println(ANSI_GREEN + p.getNome() + " evoluiu para o nivel " + p.getNivel() + "!" + ANSI_RESET);
                                                            encontrada = true;
                                                            break;
                                                        }
                                                    }

                                                    if (!encontrada) {
                                                        System.out.println(ANSI_RED + "Persona não encontrada! Tente novamente." + ANSI_RESET);
                                                    }

                                                    sairEvolucao = !confirmarAcao(sc, "Deseja evoluir outra Persona?");
                                                }
                                            }
                                        }
                                    }
                                    case 2 -> {
                                        boolean continuarConsultando = true;
                                        while (continuarConsultando) {
                                            System.out.println(ANSI_CYAN + "\n----- Usuários Cadastrados -----" + ANSI_RESET);
                                            listarUsuarios(user); // Listar usuários

                                            System.out.print(ANSI_GRAY + "Digite o nome do usuário que deseja consultar: " + ANSI_RESET);
                                            String nomeUsuario = sc.nextLine();

                                            try {
                                                consultarUsuario(user, nomeUsuario); // Consultar usuário
                                            } catch (IllegalArgumentException e) {
                                                System.out.println(e.getMessage());
                                            }

                                            continuarConsultando = confirmarAcao(sc, "Deseja consultar outro usuário?");
                                        }
                                    }
                                    case 3 -> {
                                        boolean continuarConsultando = true;
                                        while (continuarConsultando) {
                                            System.out.println(ANSI_CYAN + "\n----- NPCs Disponíveis -----" + ANSI_RESET);
                                            listarNPCs(npcs);

                                            System.out.print(ANSI_GRAY + "Digite o nome do NPC que deseja consultar: " + ANSI_RESET);
                                            String nomeNPC = sc.nextLine();

                                            try {
                                                if (npcs.containsKey(nomeNPC)) {
                                                    npcs.get(nomeNPC).mostraInfoPersonagem();
                                                } else {
                                                    throw new IllegalArgumentException(ANSI_RED + "NPC não encontrado! Verifique o nome e tente novamente." + ANSI_RESET);
                                                }
                                            } catch (IllegalArgumentException e) {
                                                System.out.println(ANSI_RED + "Erro: " + e.getMessage() + ANSI_RESET);
                                            }

                                            continuarConsultando = confirmarAcao(sc, "Deseja consultar outro NPC?");
                                        }
                                    }

                                    case 4 -> {
                                        // Visitar Loja de Armas
                                        visitarLoja(sc, lojaArmas, protagonista, item, "Loja de Armas");

                                        // Visitar Farmácia
                                        if (confirmarAcao(sc, "Deseja visitar a Farmácia Aohige?")) {
                                            visitarLoja(sc, farmacia, protagonista, item, "Farmácia Aohige");
                                        }
                                    }

                                    case 5 -> {
                                        System.out.println(ANSI_CYAN + "\n----- Dar/Equipar Itens -----" + ANSI_RESET);

                                        System.out.println(ANSI_BLUE + "Itens disponíveis no inventário do protagonista:" + ANSI_RESET);
                                        protagonista.getInventario().mostrarInventarioPersonagem();

                                        System.out.println(ANSI_BLUE + "\nUsuários disponíveis:" + ANSI_RESET);
                                        listarUsuarios(user);

                                        if(confirmarAcao(sc, "Deseja equipar um item no protagonista ou dá-lo a um aliado?")){
                                            try{
                                                opcao = selecionarDestinoItem(sc);

                                                switch (opcao) {
                                                    case 1 -> {
                                                        System.out.print(ANSI_GRAY + "Digite o nome do item a ser dado/equipado: ");
                                                        String nomeItem = sc.nextLine();
                                                        System.out.println(ANSI_RESET);

                                                        if (item.containsKey(nomeItem)) {
                                                            if (item.get(nomeItem) instanceof Arma arma) {
                                                                arma.equiparItem(protagonista);
                                                            } else if (item.get(nomeItem) instanceof Equipamento equipamento) {
                                                                equipamento.equiparItem(protagonista);
                                                            } else {
                                                                System.out.println("Item não equipável");
                                                            }
                                                            System.out.println(ANSI_GREEN + "Item equipado com sucesso!" + ANSI_RESET);
                                                        } else {
                                                            System.out.println(ANSI_RED + "Item não encontrado!" + ANSI_RESET);
                                                        }
                                                    }
                                                    case 2 -> {
                                                        System.out.print(ANSI_GRAY + "Digite o nome do item a ser dado/equipado: ");
                                                        String nomeItem = sc.nextLine();
                                                        System.out.println("Escreva o nome do aliado que deseja dar este item: ");
                                                        String nomeAliado = sc.nextLine();
                                                        System.out.println(ANSI_RESET);

                                                        if (user.containsKey(nomeAliado) && item.containsKey(nomeItem)) {
                                                            protagonista.darItemUsuario(user.get(nomeAliado), item.get(nomeItem));
                                                            if (item.get(nomeItem) instanceof Arma arma) {
                                                                arma.equiparItem(user.get(nomeAliado));
                                                            } else if (item.get(nomeItem) instanceof Equipamento equipamento) {
                                                                equipamento.equiparItem(user.get(nomeAliado));
                                                            } else {
                                                                System.out.println("Item não equipável");
                                                            }
                                                            System.out.println(ANSI_GREEN + "Item equipado com sucesso pelo usuário " + nomeAliado + "!" + ANSI_RESET);
                                                        } else {
                                                            System.out.println(ANSI_RED + "Usuário ou item não encontrado!" + ANSI_RESET);
                                                        }
                                                    }
                                                    case 0 ->
                                                            System.out.println(ANSI_YELLOW + "Saindo..." + ANSI_RESET);
                                                    default ->
                                                            System.out.println(ANSI_RED + "Opção inválida!" + ANSI_RESET);
                                                }
                                            } catch (InputMismatchException e) {
                                                System.out.println(ANSI_RED + "Erro: Digite apenas números!" + ANSI_RESET);
                                                sc.nextLine(); // Limpa buffer
                                            }
                                        }
                                    }
                                    case 6 -> {
                                        System.out.println(ANSI_CYAN + "\n----- Dados de Inimigos -----" + ANSI_RESET);

                                        while (true) {
                                            System.out.println(ANSI_BLUE + "1. Vilões (Humanos)");
                                            System.out.println("2. Shadows" + ANSI_RESET);
                                            System.out.print(ANSI_GRAY + "Escolha (ou digite 'sair' para voltar): ");

                                            String entrada = sc.nextLine();
                                            System.out.println(ANSI_RESET);

                                            if (entrada.equalsIgnoreCase("sair")) {
                                                break;
                                            }

                                            try {
                                                int tipoInimigo = Integer.parseInt(entrada);

                                                if (tipoInimigo == 1) {
                                                    System.out.println(ANSI_CYAN + "\n----- Vilões -----" + ANSI_RESET);
                                                    user.get("Takaya").mostraInfoPersonagem();
                                                    System.out.println();
                                                    user.get("Jin").mostraInfoPersonagem();
                                                    System.out.println();
                                                    user.get("Chidori").mostraInfoPersonagem();
                                                    System.out.println();
                                                    break;
                                                } else if (tipoInimigo == 2) {
                                                    System.out.println(ANSI_CYAN + "\n----- Shadows -----" + ANSI_RESET);
                                                    for (Shadow s : shadows) {
                                                        System.out.println(s.toString());
                                                    }
                                                    break;
                                                } else {
                                                    System.out.println(ANSI_RED + "Opção inválida! Digite 1, 2 ou 'sair'." + ANSI_RESET);
                                                }

                                            } catch (NumberFormatException e) {
                                                System.out.println(ANSI_RED + "Erro: Digite um número válido ou 'sair'." + ANSI_RESET);
                                            }
                                        }
                                    }
                                    case 7 -> {
                                        System.out.println(ANSI_CYAN + "\n----- Inventário do Protagonista -----" + ANSI_RESET);
                                        protagonista.getInventario().mostrarInventarioPersonagem();
                                    }
                                    case 8 -> {
                                        System.out.println(ANSI_CYAN + "\n----- Inventário de Usuário -----" + ANSI_RESET);

                                        while (true) {
                                            listarUsuarios(user);

                                            System.out.println(ANSI_GRAY + "\nDigite o nome do usuário que deseja ver o inventário (ou digite 'sair' para voltar): ");
                                            String nomeUsuario = sc.nextLine();
                                            System.out.println(ANSI_RESET);

                                            if (nomeUsuario.equalsIgnoreCase("sair")) {
                                                break;
                                            }
                                            if (user.containsKey(nomeUsuario)) {
                                                user.get(nomeUsuario).getInventario().mostrarInventarioPersonagem();
                                                break;
                                            } else {
                                                System.out.println(ANSI_RED + "Usuário não encontrado! Tente novamente." + ANSI_RESET);
                                            }
                                        }
                                    }

                                    default -> System.out.println(ANSI_RED + "Opção inválida!" + ANSI_RESET);
                                }
                            } catch (InvalidMenuInputException e) {
                                System.out.println(ANSI_RED + "Erro: " + e.getMessage() + ANSI_RESET);
                            }
                        }
                    }
                    case 2 -> {
                        while (true) {
                            AudioManager.getInstance().setGameStateMusic(AudioManager.GameState.CITY);
                            try {
                                opcao = mostrarMenuCidade(localAtual);
                                if (opcao == 0) {
                                    AudioManager.getInstance().stopMusic();
                                    AudioManager.getInstance().playMenuBackSFX();
                                    break;
                                }

                                switch (opcao) {
                                    case 1 -> {
                                        System.out.println(ANSI_CYAN + "\nLocais disponíveis: " + tatsumiPort.getLocais().keySet() + ANSI_RESET);
                                        System.out.print(ANSI_GRAY + "Digite o nome do local: ");
                                        String destino = sc.nextLine();
                                        System.out.println(ANSI_RESET);

                                        if (tatsumiPort.getLocais().containsKey(destino)) {
                                            localAtual = destino;
                                            System.out.println(ANSI_GREEN + "Você foi para " + localAtual + " com sucesso!" + ANSI_RESET);
                                        } else {
                                            System.out.println(ANSI_RED + "Local inválido!" + ANSI_RESET);
                                        }
                                    }
                                    case 2 -> {
                                        System.out.println(ANSI_CYAN + "\nPersonagens no local " + localAtual + ":" + ANSI_RESET);
                                        tatsumiPort.getPersonagensNoLocal(localAtual).forEach(p ->
                                                System.out.println(ANSI_GRAY + "- " + p.getNome()));
                                        System.out.println(ANSI_RESET);
                                    }
                                    case 3 -> {
                                        System.out.println(ANSI_CYAN + "\n----- Interação -----" + ANSI_RESET);

                                        while (true) {
                                            AudioManager.getInstance().setGameStateMusic(AudioManager.GameState.INTERACTION);
                                            System.out.println(ANSI_GRAY + "Com quem deseja interagir?" + ANSI_RESET);
                                            System.out.println(ANSI_PURPLE + "1. Interagir com um aliado SEES");
                                            System.out.println("2. Interagir com um NPC");
                                            System.out.println("3. Interagir com um inimigo da STREGA");
                                            System.out.println("Digite 'sair' para voltar ao menu principal" + ANSI_RESET);

                                            String resposta = sc.nextLine();

                                            if (resposta.equalsIgnoreCase("sair")) {
                                                break;
                                            }

                                            try {
                                                int escolha = Integer.parseInt(resposta);

                                                if (escolha == 1) {
                                                    while (true) {
                                                        System.out.println(ANSI_PURPLE + "\nAliados disponíveis:" + ANSI_RESET);
                                                        listarAliados(user);
                                                        System.out.println(ANSI_GRAY + "Digite o nome do aliado para interagir ou 'voltar':");
                                                        String nomeAliado = sc.nextLine();
                                                        System.out.print(ANSI_RESET);

                                                        if (nomeAliado.equalsIgnoreCase("voltar")) {
                                                            break;
                                                        }

                                                        if (user.containsKey(nomeAliado) && !user.get(nomeAliado).isVilao()) {
                                                            user.get(nomeAliado).interacao(protagonista);
                                                            System.out.println(ANSI_GREEN + "Interagiu com um aliado SEES com sucesso!" + ANSI_RESET);
                                                            break;
                                                        } else {
                                                            System.out.println(ANSI_RED + "Usuário não encontrado ou não é da SEES. Tente novamente." + ANSI_RESET);
                                                        }
                                                    }

                                                } else if (escolha == 2) {
                                                    while (true) {
                                                        listarNPCs(npcs);
                                                        System.out.println(ANSI_GRAY + "Digite o nome do NPC para interagir ou 'voltar':");
                                                        String nomeNPC = sc.nextLine();
                                                        System.out.print(ANSI_RESET);

                                                        if (nomeNPC.equalsIgnoreCase("voltar")) {
                                                            break;
                                                        }

                                                        if (npcs.containsKey(nomeNPC)) {
                                                            npcs.get(nomeNPC).interacao(protagonista);
                                                            System.out.println(ANSI_GREEN + "Interagiu com um NPC com sucesso!" + ANSI_RESET);
                                                            break;
                                                        } else {
                                                            System.out.println(ANSI_RED + "NPC não encontrado. Tente novamente." + ANSI_RESET);
                                                        }
                                                    }

                                                } else if (escolha == 3) {
                                                    while (true) {
                                                        int count = 1;
                                                        System.out.println(ANSI_PURPLE + "\nVilões disponíveis:" + ANSI_RESET);
                                                        for (String nomeVilao : user.keySet()) {
                                                            if (user.get(nomeVilao).isVilao()) {
                                                                System.out.println(ANSI_BLUE + count + ". " + nomeVilao + ANSI_RESET);
                                                                count++;
                                                            }
                                                        }
                                                        System.out.println(ANSI_GRAY + "Digite o nome do vilão da STREGA para interagir ou 'voltar':");
                                                        String nomeVilao = sc.nextLine();
                                                        System.out.print(ANSI_RESET);

                                                        if (nomeVilao.equalsIgnoreCase("voltar")) {
                                                            break;
                                                        }

                                                        if (user.containsKey(nomeVilao) && user.get(nomeVilao).isVilao()) {
                                                            user.get(nomeVilao).interacao(protagonista);
                                                            System.out.println(ANSI_GREEN + "Interagiu com um vilão da STREGA com sucesso!" + ANSI_RESET);
                                                            break;
                                                        } else {
                                                            System.out.println(ANSI_RED + "Vilão não encontrado. Tente novamente." + ANSI_RESET);
                                                        }
                                                    }
                                                } else {
                                                    System.out.println(ANSI_RED + "Opção inválida. Digite 1, 2, 3 ou 'sair'." + ANSI_RESET);
                                                }

                                            } catch (NumberFormatException e) {
                                                System.out.println(ANSI_RED + "Erro: Digite apenas números válidos ou 'sair'." + ANSI_RESET);
                                            }

                                        }
                                    }
                                    case 4 -> {
                                        AudioManager.getInstance().setGameStateMusic(AudioManager.GameState.BATTLE);

                                        System.out.println(ANSI_CYAN + "\nDeseja enfrentar qual tipo de inimigo?" + ANSI_RESET);
                                        System.out.println("1. STREGA");
                                        System.out.println("2. Shadow");
                                        System.out.println("0. Sair");
                                        String escolha = sc.nextLine();

                                        if(escolha.equals("1") || escolha.equalsIgnoreCase("STREGA")) {
                                            // Código existente para batalha contra STREGA
                                            ArrayList<SerHumano> pessoas = tatsumiPort.getPersonagensNoLocal(localAtual);
                                            ArrayList<UsuarioPersona> viloes = new ArrayList<>();

                                            for (SerHumano pessoa : pessoas) {
                                                if (pessoa instanceof Usuarios u) {
                                                    if (u.isVilao()) {
                                                        viloes.add(u);
                                                    }
                                                }
                                            }

                                            ArrayList<UsuarioPersona> timeProtagonistas = new ArrayList<>();
                                            timeProtagonistas.add(protagonista);
                                            System.out.println(ANSI_CYAN + "\n----- Batalha -----" + ANSI_RESET);
                                            System.out.println();
                                            System.out.println(ANSI_BLUE + "Monte a sua equipe!" + ANSI_RESET);
                                            listarAliados(user);
                                            Set<String> aliadosSelecionados = new HashSet<>();

                                            for (int i = 1; i <= 3; i++) {
                                                String posicao = switch (i) {
                                                    case 1 -> "Primeiro";
                                                    case 2 -> "Segundo";
                                                    case 3 -> "Terceiro";
                                                    default -> "";
                                                };

                                                while (true) {
                                                    System.out.println(posicao + " Aliado: ");
                                                    String nomeAliado = sc.nextLine();

                                                    if (user.containsKey(nomeAliado) && !aliadosSelecionados.contains(nomeAliado)) {
                                                        timeProtagonistas.add(user.get(nomeAliado));
                                                        aliadosSelecionados.add(nomeAliado);
                                                        break;
                                                    } else {
                                                        System.out.println(ANSI_RED + "Aliado não encontrado ou já selecionado! Tente novamente." + ANSI_RESET);
                                                    }
                                                }
                                            }

                                            if (!viloes.isEmpty()) {
                                                iniciarCombate(timeProtagonistas, viloes);
                                            } else {
                                                System.out.println(ANSI_RED + "Não há vilões na área para combater" + ANSI_RESET);
                                            }
                                        }
                                        else if(escolha.equals("2") || escolha.equalsIgnoreCase("Shadow")) {
                                            // Nova lógica para batalha contra Shadows
                                            ArrayList<UsuarioPersona> timeProtagonistas = new ArrayList<>();
                                            timeProtagonistas.add(protagonista);

                                            System.out.println(ANSI_CYAN + "\n----- Batalha Contra Shadows -----" + ANSI_RESET);
                                            System.out.println(ANSI_BLUE + "Monte a sua equipe!" + ANSI_RESET);
                                            listarAliados(user);
                                            Set<String> aliadosSelecionados = new HashSet<>();

                                            for (int i = 1; i <= 3; i++) {
                                                String posicao = switch (i) {
                                                    case 1 -> "Primeiro";
                                                    case 2 -> "Segundo";
                                                    case 3 -> "Terceiro";
                                                    default -> "";
                                                };

                                                while (true) {
                                                    System.out.println(posicao + " Aliado: ");
                                                    String nomeAliado = sc.nextLine();

                                                    if (user.containsKey(nomeAliado) && !aliadosSelecionados.contains(nomeAliado)) {
                                                        timeProtagonistas.add(user.get(nomeAliado));
                                                        aliadosSelecionados.add(nomeAliado);
                                                        break;
                                                    } else {
                                                        System.out.println(ANSI_RED + "Aliado não encontrado ou já selecionado! Tente novamente." + ANSI_RESET);
                                                    }
                                                }
                                            }

                                            // Selecionar Shadows aleatórias para batalha
                                            List<Shadow> shadowsDisponiveis = new ArrayList<>();
                                            shadowsDisponiveis.add(new Shadow("Dancer of Sorrow", 160, 18, "Moon", List.of("Magic"), "Fire", "Ice", random.nextInt(50) + 30));
                                            shadowsDisponiveis.add(new Shadow("Brutal Ogre", 210, 22, "Strength", List.of("Physical"), "Wind", "Fire", random.nextInt(50) + 30));
                                            shadowsDisponiveis.add(new Shadow("Twisted Teacher", 130, 14, "Hierophant", List.of("Mental"), "Electricity", "Dark", random.nextInt(50) + 30));
                                            shadowsDisponiveis.add(new Shadow("Dark Cupid", 100, 11, "Lovers", List.of("Ailment"), "Bless", "Dark", random.nextInt(50) + 30));
                                            shadowsDisponiveis.add(new Shadow("Searing Beast", 190, 20, "Chariot", List.of("Physical", "Magic"), "Ice", "Fire", random.nextInt(50) + 30));

                                            // Selecionar 1-3 Shadows aleatórias para a batalha
                                            int qtdShadows = random.nextInt(3) + 1; // 1 a 3 Shadows
                                            ArrayList<Shadow> shadowsParaBatalha = new ArrayList<>();

                                            for (int i = 0; i < qtdShadows; i++) {
                                                int index = random.nextInt(shadowsDisponiveis.size());
                                                shadowsParaBatalha.add(shadowsDisponiveis.get(index));
                                                shadowsDisponiveis.remove(index);
                                            }

                                            System.out.println("\nInimigos encontrados:");
                                            for (Shadow shadow : shadowsParaBatalha) {
                                                System.out.println(ANSI_RED + shadow.getNome() + ANSI_RESET + " - " + shadow.getArcana());
                                            }

                                            CombateShadows(timeProtagonistas, shadowsParaBatalha);
                                        }
                                        else if(escolha.equals("0")) {
                                            System.out.println("Retornando ao menu principal...");
                                        }
                                        else {
                                            System.out.println(ANSI_RED + "Opção inválida!" + ANSI_RESET);
                                        }
                                    }
                                    case 5 -> {
                                        // Visitar Loja de Armas
                                        visitarLoja(sc, lojaArmas, protagonista, item, "Loja de Armas");

                                        // Visitar Farmácia
                                        if (confirmarAcao(sc, "Deseja visitar a Farmácia Aohige?")) {
                                            visitarLoja(sc, farmacia, protagonista, item, "Farmácia Aohige");
                                        }
                                    }
                                    default -> System.out.println(ANSI_RED + "Opção inválida!" + ANSI_RESET);
                                }
                            } catch (InvalidMenuInputException e) {
                                System.out.println(ANSI_RED + "Erro: " + e.getMessage() + ANSI_RESET);
                            }
                        }
                    }

                    case 3 -> {
                        Scanner sqlScanner = new Scanner(System.in);
                        ProtagonistaDAO protagonistaDAO = new ProtagonistaDAO();
                        NPCDAO npcdao = new NPCDAO();
                        UsuariosDAO usuariosDAO = new UsuariosDAO();

                        while (true) {
                            AudioManager.getInstance().setGameStateMusic(AudioManager.GameState.SQL);
                            try {
                                opcao = mostrarMenuSQL();
                                if (opcao == 0) {
                                    AudioManager.getInstance().stopMusic();
                                    AudioManager.getInstance().playMenuBackSFX();
                                    break;
                                }
                                switch (opcao) {
                                    case 1 -> {
                                        // MENU DE INSERTS
                                        while (true) {
                                            int opcaoInsert = mostrarMenuInsert(); // Você precisará criar este método
                                            if (opcaoInsert == 0) break;

                                            switch (opcaoInsert) {
                                                case 1 -> {
                                                    // Inserir Protagonista
                                                    System.out.println("\n=== INSERIR PROTAGONISTA ===");
                                                    System.out.print("Nome: ");
                                                    String nome = sqlScanner.nextLine();
                                                    int idade, nivel, idAtivador;
                                                    double hp, sp, saldo;

                                                    // Validação da Idade
                                                    while (true) {
                                                        try {
                                                            System.out.print("Idade: ");
                                                            idade = sqlScanner.nextInt();
                                                            if (idade < 0 || idade > 100) {
                                                                System.out.println("Idade inválida. Deve estar entre 0 e 100.");
                                                                sqlScanner.nextLine(); // Limpa o buffer
                                                            } else {
                                                                sqlScanner.nextLine(); // Limpa o buffer após entrada válida
                                                                break; // Sai do loop quando a idade é válida
                                                            }
                                                        } catch (InputMismatchException e) {
                                                            System.out.println("Erro ao inserir(Insira um valor inteiro para a idade).");
                                                            sqlScanner.nextLine(); // Limpa o buffer para remover a entrada inválida
                                                        }
                                                    }

                                                    System.out.print("Gênero: ");
                                                    String genero = sqlScanner.nextLine();

                                                    // Validação do Nível
                                                    while (true) {
                                                        try {
                                                            System.out.print("Nível: ");
                                                            nivel = sqlScanner.nextInt();
                                                            if (nivel < 1) {
                                                                System.out.println("Nível inválido. Deve ser maior que 0.");
                                                                sqlScanner.nextLine(); // Limpa o buffer
                                                            } else {
                                                                sqlScanner.nextLine(); // Limpa o buffer
                                                                break;
                                                            }
                                                        } catch (InputMismatchException e) {
                                                            System.out.println("Erro ao inserir(Insira um valor inteiro para o nível).");
                                                            sqlScanner.nextLine(); // Limpa o buffer
                                                        }
                                                    }

                                                    System.out.print("Arcana: ");
                                                    String arcana = sqlScanner.nextLine();

                                                    // Validação do HP
                                                    while (true) {
                                                        try {
                                                            System.out.print("HP: ");
                                                            hp = sqlScanner.nextDouble();
                                                            if (hp < 0) {
                                                                System.out.println("HP inválido. Deve ser maior ou igual a 0.");
                                                                sqlScanner.nextLine(); // Limpa o buffer
                                                            } else {
                                                                sqlScanner.nextLine(); // Limpa o buffer
                                                                break;
                                                            }
                                                        } catch (InputMismatchException e) {
                                                            System.out.println("Erro ao inserir(Insira um valor double para o HP).");
                                                            sqlScanner.nextLine(); // Limpa o buffer
                                                        }
                                                    }

                                                    // Validação do SP
                                                    while (true) {
                                                        try {
                                                            System.out.print("SP: ");
                                                            sp = sqlScanner.nextDouble();
                                                            if (sp < 0) {
                                                                System.out.println("SP inválido. Deve ser maior ou igual a 0.");
                                                                sqlScanner.nextLine(); // Limpa o buffer
                                                            } else {
                                                                sqlScanner.nextLine(); // Limpa o buffer
                                                                break;
                                                            }
                                                        } catch (InputMismatchException e) {
                                                            System.out.println("Erro ao inserir(Insira um valor double para o SP).");
                                                            sqlScanner.nextLine(); // Limpa o buffer
                                                        }
                                                    }

                                                    // Validação do saldo
                                                    while (true) {
                                                        try {
                                                            System.out.print("Saldo: ");
                                                            saldo = sqlScanner.nextDouble();
                                                            if (saldo < 0) {
                                                                System.out.println("Saldo inválido. Deve ser maior ou igual a 0.");
                                                                sqlScanner.nextLine(); // Limpa o buffer
                                                            } else {
                                                                sqlScanner.nextLine(); // Limpa o buffer
                                                                break;
                                                            }
                                                        } catch (InputMismatchException e) {
                                                            System.out.println("Erro ao inserir(Insira um valor double para o Saldo).");
                                                            sqlScanner.nextLine(); // Limpa o buffer
                                                        }
                                                    }

                                                    // Validação do ID_Ativador
                                                    while (true) {
                                                        try {
                                                            System.out.print("IdAtivador: ");
                                                            idAtivador = sqlScanner.nextInt();
                                                            if (idAtivador < 0) {
                                                                System.out.println("ID inválido. Deve ser maior ou igual a 0.");
                                                                sqlScanner.nextLine(); // Limpa o buffer
                                                            } else {
                                                                sqlScanner.nextLine(); // Limpa o buffer
                                                                break;
                                                            }
                                                        } catch (InputMismatchException e) {
                                                            System.out.println("Erro ao inserir(Insira um valor double para o ID).");
                                                            sqlScanner.nextLine(); // Limpa o buffer
                                                        }
                                                    }

                                                    // Criar objeto Protagonista (assumindo que existe um construtor adequado)
                                                    Protagonista novoProtagonista = new Protagonista(nome, idade, genero, nivel, arcana, hp, sp, saldo, idAtivador);

                                                    int idInserido = protagonistaDAO.insertProtagonista(novoProtagonista);

                                                    if (idInserido != -1) {
                                                        System.out.println("Protagonista inserido com sucesso! ID: " + idInserido);
                                                    } else {
                                                        System.out.println("Erro ao inserir protagonista.");
                                                    }
                                                }

                                                case 2 -> {
                                                    // Inserir NPC
                                                    System.out.println("\n=== INSERIR NPC ===");

                                                    System.out.print("Nome: ");
                                                    String nomeNPC = sqlScanner.nextLine();
                                                    int idadeNPC;

                                                    // Tratamento da idade
                                                    while (true) {
                                                        try {
                                                            System.out.print("Idade: ");
                                                            idadeNPC = sqlScanner.nextInt();
                                                            if (idadeNPC < 0 || idadeNPC > 100) {
                                                                System.out.println("Idade inválida. Deve estar entre 0 e 100.");
                                                                sqlScanner.nextLine(); // Limpa o buffer
                                                            } else {
                                                                sqlScanner.nextLine(); // Limpa o buffer após entrada válida
                                                                break; // Sai do loop quando a idade é válida
                                                            }
                                                        } catch (InputMismatchException e) {
                                                            System.out.println("Erro ao inserir(Insira um valor inteiro para a idade).");
                                                            sqlScanner.nextLine(); // Limpa o buffer para remover a entrada inválida
                                                        }
                                                    }

                                                    System.out.print("Gênero: ");
                                                    String generoNPC = sqlScanner.nextLine();

                                                    System.out.print("Ocupação: ");
                                                    String ocupacaoNPC = sqlScanner.nextLine();

                                                    System.out.print("Arcana: ");
                                                    String arcanaNPC = sqlScanner.nextLine();

                                                    NPC novoNPC = new NPC(nomeNPC, idadeNPC, generoNPC, ocupacaoNPC, arcanaNPC);

                                                    boolean sucessoInsercaoNPC = npcdao.insertNPC(novoNPC);

                                                    if (sucessoInsercaoNPC) {
                                                        System.out.println("NPC inserido com sucesso!");
                                                    } else {
                                                        System.out.println("Erro ao inserir NPC.");
                                                    }
                                                }

                                                case 3 -> {
                                                    // Inserir Usuario
                                                    int nivelUsuario;
                                                    double hpUsuario;
                                                    double spUsuario;
                                                    boolean vilaoUsuario;
                                                    int idadeUsuario;

                                                    System.out.println("\n=== INSERIR USUÁRIO ===");
                                                    System.out.print("Nome: ");
                                                    String nomeUsuario = sqlScanner.nextLine();

                                                    // Validação da Idade
                                                    while (true) {
                                                        try {
                                                            System.out.print("Idade: ");
                                                            idadeUsuario = sqlScanner.nextInt();
                                                            if (idadeUsuario < 0 || idadeUsuario > 100) {
                                                                System.out.println("Idade inválida. Deve estar entre 0 e 100.");
                                                                sqlScanner.nextLine(); // Limpa o buffer
                                                            } else {
                                                                sqlScanner.nextLine(); // Limpa o buffer após entrada válida
                                                                break; // Sai do loop quando a idade é válida
                                                            }
                                                        } catch (InputMismatchException e) {
                                                            System.out.println("Erro ao inserir(Insira um valor inteiro para a idade).");
                                                            sqlScanner.nextLine(); // Limpa o buffer para remover a entrada inválida
                                                        }
                                                    }

                                                    System.out.print("Gênero: ");
                                                    String generoUsuario = sqlScanner.nextLine();

                                                    // Validação do Nível
                                                    while (true) {
                                                        try {
                                                            System.out.print("Nível: ");
                                                            nivelUsuario = sqlScanner.nextInt();
                                                            if (nivelUsuario < 1) {
                                                                System.out.println("Nível inválido. Deve ser maior que 0.");
                                                                sqlScanner.nextLine(); // Limpa o buffer
                                                            } else {
                                                                sqlScanner.nextLine(); // Limpa o buffer
                                                                break;
                                                            }
                                                        } catch (InputMismatchException e) {
                                                            System.out.println("Erro ao inserir(Insira um valor inteiro para o nível).");
                                                            sqlScanner.nextLine(); // Limpa o buffer
                                                        }
                                                    }

                                                    System.out.print("Arcana: ");
                                                    String arcanaUsuario = sqlScanner.nextLine();

                                                    // Validação do HP
                                                    while (true) {
                                                        try {
                                                            System.out.print("HP: ");
                                                            hpUsuario = sqlScanner.nextDouble();
                                                            if (hpUsuario < 0) {
                                                                System.out.println("HP inválido. Deve ser maior ou igual a 0.");
                                                                sqlScanner.nextLine(); // Limpa o buffer
                                                            } else {
                                                                sqlScanner.nextLine(); // Limpa o buffer
                                                                break;
                                                            }
                                                        } catch (InputMismatchException e) {
                                                            System.out.println("Erro ao inserir(Insira um valor double para o HP).");
                                                            sqlScanner.nextLine(); // Limpa o buffer
                                                        }
                                                    }

                                                    // Validação do SP
                                                    while (true) {
                                                        try {
                                                            System.out.print("SP: ");
                                                            spUsuario = sqlScanner.nextDouble();
                                                            if (spUsuario < 0) {
                                                                System.out.println("SP inválido. Deve ser maior ou igual a 0.");
                                                                sqlScanner.nextLine(); // Limpa o buffer
                                                            } else {
                                                                sqlScanner.nextLine(); // Limpa o buffer
                                                                break;
                                                            }
                                                        } catch (InputMismatchException e) {
                                                            System.out.println("Erro ao inserir(Insira um valor double para o SP).");
                                                            sqlScanner.nextLine(); // Limpa o buffer
                                                        }
                                                    }

                                                    System.out.print("Papel: ");
                                                    String papelUsuario = sqlScanner.nextLine();

                                                    // Validação do Vilão
                                                    while (true) {
                                                        try {
                                                            System.out.print("É vilão? (true/false): ");
                                                            vilaoUsuario = sqlScanner.nextBoolean();
                                                            sqlScanner.nextLine(); // Limpa o buffer
                                                            break;
                                                        } catch (InputMismatchException e) {
                                                            System.out.println("Erro ao inserir(Insira um valor boolean para o vilão - true ou false).");
                                                            sqlScanner.nextLine(); // Limpa o buffer
                                                        }
                                                    }

                                                    Usuarios novoUsuario = new Usuarios(nomeUsuario, idadeUsuario, generoUsuario, nivelUsuario,
                                                            arcanaUsuario, hpUsuario, spUsuario, papelUsuario, vilaoUsuario);

                                                    boolean sucessoInsercao = usuariosDAO.insertUsuario(novoUsuario);

                                                    if (sucessoInsercao) {
                                                        System.out.println("Usuário inserido com sucesso!");
                                                    } else {
                                                        System.out.println("Erro ao inserir usuário.");
                                                    }
                                                }

                                                default ->
                                                        System.out.println(ANSI_RED + "Opção inválida!" + ANSI_RESET);
                                            }
                                        }
                                    }

                                    case 2 -> {
                                        // MENU DE UPDATES
                                        while (true) {
                                            int opcaoUpdate = mostrarMenuUpdate(); // Você precisará criar este método
                                            if (opcaoUpdate == 0) break;

                                            switch (opcaoUpdate) {
                                                case 1 -> {
                                                    // Atualizar Protagonista
                                                    System.out.println("\n=== ATUALIZAR PROTAGONISTA ===");

                                                    // Primeiro listar os protagonistas existentes
                                                    List<Protagonista> protagonistasExistentes = protagonistaDAO.selectProtagonista();
                                                    if (protagonistasExistentes.isEmpty()) {
                                                        System.out.println("Nenhum protagonista encontrado para atualizar.");
                                                        break;
                                                    }

                                                    System.out.println("Protagonistas disponíveis:");
                                                    for (Protagonista p : protagonistasExistentes) {
                                                        System.out.println("ID: " + p.getId() + " - Nome: " + p.getNome());
                                                    }

                                                    try {
                                                        System.out.print("Digite o ID do protagonista que deseja atualizar: ");
                                                        int idParaAtualizar = sqlScanner.nextInt();
                                                        sqlScanner.nextLine();

                                                        // Buscar o protagonista pelo ID
                                                        Protagonista protagonistaParaAtualizar = null;
                                                        for (Protagonista p : protagonistasExistentes) {
                                                            if (p.getId() == idParaAtualizar) {
                                                                protagonistaParaAtualizar = p;
                                                                break;
                                                            }
                                                        }

                                                        if (protagonistaParaAtualizar == null) {
                                                            System.out.println("Protagonista com ID " + idParaAtualizar + " não encontrado.");
                                                            break;
                                                        }

                                                        // Coletar novos dados
                                                        System.out.println("Digite os novos dados (pressione Enter para manter o valor atual):");

                                                        System.out.print("Nome atual: " + protagonistaParaAtualizar.getNome() + " | Novo nome: ");
                                                        String novoNome = sqlScanner.nextLine();
                                                        if (!novoNome.trim().isEmpty()) {
                                                            protagonistaParaAtualizar.setNome(novoNome);
                                                        }

                                                        System.out.print("Idade atual: " + protagonistaParaAtualizar.getIdade() + " | Nova idade: ");
                                                        String novaIdadeStr = sqlScanner.nextLine();
                                                        if (!novaIdadeStr.trim().isEmpty()) {
                                                            protagonistaParaAtualizar.setIdade(Integer.parseInt(novaIdadeStr));
                                                        }

                                                        System.out.print("Gênero atual: " + protagonistaParaAtualizar.getGenero() + " | Novo gênero: ");
                                                        String novoGenero = sqlScanner.nextLine();
                                                        if (!novoGenero.trim().isEmpty()) {
                                                            protagonistaParaAtualizar.setGenero(novoGenero);
                                                        }

                                                        System.out.print("Nível atual: " + protagonistaParaAtualizar.getNivel() + " | Novo nível: ");
                                                        String novoNivelStr = sqlScanner.nextLine();
                                                        if (!novoNivelStr.trim().isEmpty()) {
                                                            protagonistaParaAtualizar.setNivel(Integer.parseInt(novoNivelStr));
                                                        }

                                                        System.out.print("Arcana atual: " + protagonistaParaAtualizar.getArcana() + " | Nova arcana: ");
                                                        String novaArcana = sqlScanner.nextLine();
                                                        if (!novaArcana.trim().isEmpty()) {
                                                            protagonistaParaAtualizar.setArcana(novaArcana);
                                                        }

                                                        System.out.print("HP atual: " + protagonistaParaAtualizar.getHp() + " | Novo HP: ");
                                                        String novoHpStr = sqlScanner.nextLine();
                                                        if (!novoHpStr.trim().isEmpty()) {
                                                            protagonistaParaAtualizar.setHp(Double.parseDouble(novoHpStr));
                                                        }

                                                        System.out.print("SP atual: " + protagonistaParaAtualizar.getSp() + " | Novo SP: ");
                                                        String novoSpStr = sqlScanner.nextLine();
                                                        if (!novoSpStr.trim().isEmpty()) {
                                                            protagonistaParaAtualizar.setSp(Double.parseDouble(novoSpStr));
                                                        }

                                                        System.out.print("Saldo atual: " + protagonistaParaAtualizar.getSaldo() + " | Novo saldo: ");
                                                        String novoSaldoStr = sqlScanner.nextLine();
                                                        if (!novoSaldoStr.trim().isEmpty()) {
                                                            protagonistaParaAtualizar.setSaldo(Double.parseDouble(novoSaldoStr));
                                                        }

                                                        System.out.print("ID Ativador atual: " + protagonistaParaAtualizar.getId() + " | Novo ID Ativador: ");
                                                        String novoIdAtivadorStr = sqlScanner.nextLine();
                                                        if (!novoIdAtivadorStr.trim().isEmpty()) {
                                                            // Assumindo que existe um method para setar o ativador
                                                            protagonistaParaAtualizar.setId(Integer.parseInt(novoIdAtivadorStr)); //arrumado
                                                        }

                                                        protagonistaDAO.updateProtagonista(protagonistaParaAtualizar);
                                                        System.out.println("Protagonista atualizado com sucesso!");
                                                    } catch (InputMismatchException e) {
                                                        System.out.println("Erro ao atualizar protagonista(Insira um valor inteiro para o ID).");
                                                    }
                                                }

                                                case 2 -> {
                                                    // Atualizar NPC
                                                    System.out.println("\n=== ATUALIZAR NPC ===");

                                                    // Primeiro listar os NPCs existentes
                                                    List<NPC> npcsExistentes = npcdao.selectNPC();
                                                    if (npcsExistentes.isEmpty()) {
                                                        System.out.println("Nenhum NPC encontrado para atualizar.");
                                                        break;
                                                    }

                                                    System.out.println("NPCs disponíveis:");
                                                    for (NPC n : npcsExistentes) {
                                                        System.out.println("Nome: " + n.getNome() + " | Ocupação: " + n.getOcupacao() + " | Arcana: " + n.getArcana());
                                                    }

                                                    System.out.print("Digite o nome do NPC que deseja atualizar: ");
                                                    String nomeNPCParaAtualizar = sqlScanner.nextLine();

                                                    // Buscar o NPC pelo nome
                                                    NPC npcParaAtualizar = null;
                                                    for (NPC n : npcsExistentes) {
                                                        if (n.getNome().equalsIgnoreCase(nomeNPCParaAtualizar)) {
                                                            npcParaAtualizar = n;
                                                            break;
                                                        }
                                                    }

                                                    if (npcParaAtualizar == null) {
                                                        System.out.println("NPC com nome '" + nomeNPCParaAtualizar + "' não encontrado.");
                                                        break;
                                                    }

                                                    // Coletar novos dados
                                                    System.out.println("Digite os novos dados (pressione Enter para manter o valor atual):");

                                                    System.out.print("Idade atual: " + npcParaAtualizar.getIdade() + " | Nova idade: ");
                                                    String novaIdadeNPCStr = sqlScanner.nextLine();
                                                    if (!novaIdadeNPCStr.trim().isEmpty()) {
                                                        npcParaAtualizar.setIdade(Integer.parseInt(novaIdadeNPCStr));
                                                    }

                                                    System.out.print("Gênero atual: " + npcParaAtualizar.getGenero() + " | Novo gênero: ");
                                                    String novoGeneroNPC = sqlScanner.nextLine();
                                                    if (!novoGeneroNPC.trim().isEmpty()) {
                                                        npcParaAtualizar.setGenero(novoGeneroNPC);
                                                    }

                                                    System.out.print("Ocupação atual: " + npcParaAtualizar.getOcupacao() + " | Nova ocupação: ");
                                                    String novaOcupacaoNPC = sqlScanner.nextLine();
                                                    if (!novaOcupacaoNPC.trim().isEmpty()) {
                                                        npcParaAtualizar.setOcupacao(novaOcupacaoNPC);
                                                    }

                                                    System.out.print("Arcana atual: " + npcParaAtualizar.getArcana() + " | Nova arcana: ");
                                                    String novaArcanaNPC = sqlScanner.nextLine();
                                                    if (!novaArcanaNPC.trim().isEmpty()) {
                                                        npcParaAtualizar.setArcana(novaArcanaNPC);
                                                    }

                                                    boolean sucessoAtualizacaoNPC = npcdao.updateNPC(npcParaAtualizar);

                                                    if (sucessoAtualizacaoNPC) {
                                                        System.out.println("NPC atualizado com sucesso!");
                                                    } else {
                                                        System.out.println("Erro ao atualizar NPC.");
                                                    }
                                                }

                                                case 3 -> {
                                                    // Atualizar Usuario
                                                    System.out.println("\n=== ATUALIZAR USUÁRIO ===");

                                                    // Primeiro listar os usuários existentes
                                                    List<Usuarios> usuariosExistentes = usuariosDAO.selectUsuarios();
                                                    if (usuariosExistentes.isEmpty()) {
                                                        System.out.println("Nenhum usuário encontrado para atualizar.");
                                                        break;
                                                    }

                                                    System.out.println("Usuários disponíveis:");
                                                    for (Usuarios u : usuariosExistentes) {
                                                        System.out.println("Nome: " + u.getNome() + " | Papel: " + u.getPapel() + " | Vilão: " + u.isVilao());
                                                    }

                                                    System.out.print("Digite o nome do usuário que deseja atualizar: ");
                                                    String nomeParaAtualizar = sqlScanner.nextLine();

                                                    // Buscar o usuário pelo nome
                                                    Usuarios usuarioParaAtualizar = null;
                                                    for (Usuarios u : usuariosExistentes) {
                                                        if (u.getNome().equalsIgnoreCase(nomeParaAtualizar)) {
                                                            usuarioParaAtualizar = u;
                                                            break;
                                                        }
                                                    }

                                                    if (usuarioParaAtualizar == null) {
                                                        System.out.println("Usuário com nome '" + nomeParaAtualizar + "' não encontrado.");
                                                        break;
                                                    }

                                                    // Coletar novos dados
                                                    System.out.println("Digite os novos dados (pressione Enter para manter o valor atual):");

                                                    System.out.print("Idade atual: " + usuarioParaAtualizar.getIdade() + " | Nova idade: ");
                                                    String novaIdadeUsuarioStr = sqlScanner.nextLine();
                                                    if (!novaIdadeUsuarioStr.trim().isEmpty()) {
                                                        usuarioParaAtualizar.setIdade(Integer.parseInt(novaIdadeUsuarioStr));
                                                    }

                                                    System.out.print("Gênero atual: " + usuarioParaAtualizar.getGenero() + " | Novo gênero: ");
                                                    String novoGeneroUsuario = sqlScanner.nextLine();
                                                    if (!novoGeneroUsuario.trim().isEmpty()) {
                                                        usuarioParaAtualizar.setGenero(novoGeneroUsuario);
                                                    }

                                                    System.out.print("Nível atual: " + usuarioParaAtualizar.getNivel() + " | Novo nível: ");
                                                    String novoNivelUsuarioStr = sqlScanner.nextLine();
                                                    if (!novoNivelUsuarioStr.trim().isEmpty()) {
                                                        usuarioParaAtualizar.setNivel(Integer.parseInt(novoNivelUsuarioStr));
                                                    }

                                                    System.out.print("Arcana atual: " + usuarioParaAtualizar.getArcana() + " | Nova arcana: ");
                                                    String novaArcanaUsuario = sqlScanner.nextLine();
                                                    if (!novaArcanaUsuario.trim().isEmpty()) {
                                                        usuarioParaAtualizar.setArcana(novaArcanaUsuario);
                                                    }

                                                    System.out.print("HP atual: " + usuarioParaAtualizar.getHp() + " | Novo HP: ");
                                                    String novoHpUsuarioStr = sqlScanner.nextLine();
                                                    if (!novoHpUsuarioStr.trim().isEmpty()) {
                                                        usuarioParaAtualizar.setHp(Double.parseDouble(novoHpUsuarioStr));
                                                    }

                                                    System.out.print("SP atual: " + usuarioParaAtualizar.getSp() + " | Novo SP: ");
                                                    String novoSpUsuarioStr = sqlScanner.nextLine();
                                                    if (!novoSpUsuarioStr.trim().isEmpty()) {
                                                        usuarioParaAtualizar.setSp(Double.parseDouble(novoSpUsuarioStr));
                                                    }

                                                    System.out.print("Papel atual: " + usuarioParaAtualizar.getPapel() + " | Novo papel: ");
                                                    String novoPapelUsuario = sqlScanner.nextLine();
                                                    if (!novoPapelUsuario.trim().isEmpty()) {
                                                        usuarioParaAtualizar.setPapel(novoPapelUsuario);
                                                    }

                                                    System.out.print("Vilão atual: " + usuarioParaAtualizar.isVilao() + " | É vilão? (true/false ou Enter para manter): ");
                                                    String novoVilaoStr = sqlScanner.nextLine();
                                                    if (!novoVilaoStr.trim().isEmpty()) {
                                                        usuarioParaAtualizar.setVilao(Boolean.parseBoolean(novoVilaoStr));
                                                    }

                                                    boolean sucessoAtualizacao = usuariosDAO.updateUsuario(usuarioParaAtualizar);

                                                    if (sucessoAtualizacao) {
                                                        System.out.println("Usuário atualizado com sucesso!");
                                                    } else {
                                                        System.out.println("Erro ao atualizar usuário.");
                                                    }
                                                }

                                                default ->
                                                        System.out.println(ANSI_RED + "Opção inválida!" + ANSI_RESET);
                                            }
                                        }
                                    }

                                    case 3 -> {
                                        // MENU DE DELETES
                                        while (true) {
                                            int opcaoDelete = mostrarMenuDelete();
                                            if(opcaoDelete == 0)
                                                break;
                                            switch (opcaoDelete) {
                                                case 1 -> {
                                                    // Deletar Protagonista por ID
                                                    System.out.println("\n=== DELETAR PROTAGONISTA ===");
                                                    System.out.print("Digite o ID do protagonista que deseja deletar: ");

                                                    try {
                                                        int id = Integer.parseInt(sqlScanner.nextLine());

                                                        System.out.print("ATENÇÃO: Esta ação irá deletar o protagonista com ID " + id + ". Confirma? (s/N): ");
                                                        String confirmacao = sqlScanner.nextLine();

                                                        if (confirmacao.equalsIgnoreCase("s") || confirmacao.equalsIgnoreCase("sim")) {
                                                            boolean deletado = protagonistaDAO.deleteProtagonista(id);
                                                            if (deletado) {
                                                                System.out.println("Protagonista com ID " + id + " foi deletado com sucesso!");
                                                            } else {
                                                                System.out.println("Nenhum protagonista encontrado com o ID " + id + ".");
                                                            }
                                                        } else {
                                                            System.out.println("Operação cancelada.");
                                                        }
                                                    } catch (NumberFormatException e) {
                                                        System.out.println("Erro: Por favor, digite um número válido para o ID.");
                                                    }
                                                }
                                                case 2 -> {
                                                    // Deletar NPC
                                                    System.out.println("\n=== DELETAR NPC ===");

                                                    // Primeiro listar os NPCs existentes
                                                    List<NPC> npcsExistentes = npcdao.selectNPC();
                                                    if (npcsExistentes.isEmpty()) {
                                                        System.out.println("Nenhum NPC encontrado para deletar.");
                                                        break;
                                                    }

                                                    System.out.println("NPCs disponíveis:");
                                                    for (NPC n : npcsExistentes) {
                                                        System.out.println("Nome: " + n.getNome() + " | Ocupação: " + n.getOcupacao() + " | Arcana: " + n.getArcana());
                                                    }

                                                    System.out.print("Digite o nome do NPC que deseja deletar: ");
                                                    String nomeNPCParaDeletar = sqlScanner.nextLine();

                                                    // Verificar se o NPC existe
                                                    boolean npcExiste = false;
                                                    for (NPC n : npcsExistentes) {
                                                        if (n.getNome().equalsIgnoreCase(nomeNPCParaDeletar)) {
                                                            npcExiste = true;
                                                            break;
                                                        }
                                                    }

                                                    if (!npcExiste) {
                                                        System.out.println("NPC com nome '" + nomeNPCParaDeletar + "' não encontrado.");
                                                        break;
                                                    }

                                                    System.out.print("ATENÇÃO: Esta ação irá deletar permanentemente o NPC '" + nomeNPCParaDeletar + "'. Confirma? (s/N): ");
                                                    String confirmacaoDeleteNPC = sqlScanner.nextLine();

                                                    if (confirmacaoDeleteNPC.equalsIgnoreCase("s") || confirmacaoDeleteNPC.equalsIgnoreCase("sim")) {
                                                        boolean sucessoDelecaoNPC = npcdao.deleteNPC(nomeNPCParaDeletar);

                                                        if (sucessoDelecaoNPC) {
                                                            System.out.println("NPC '" + nomeNPCParaDeletar + "' deletado com sucesso!");
                                                        } else {
                                                            System.out.println("Erro ao deletar NPC.");
                                                        }
                                                    } else {
                                                        System.out.println("Operação cancelada.");
                                                    }
                                                }
                                                case 3 -> {
                                                    // Deletar Usuario
                                                    System.out.println("\n=== DELETAR USUÁRIO ===");

                                                    // Primeiro listar os usuários existentes
                                                    List<Usuarios> usuariosExistentes = usuariosDAO.selectUsuarios();
                                                    if (usuariosExistentes.isEmpty()) {
                                                        System.out.println("Nenhum usuário encontrado para deletar.");
                                                        break;
                                                    }

                                                    System.out.println("Usuários disponíveis:");
                                                    for (Usuarios u : usuariosExistentes) {
                                                        System.out.println("Nome: " + u.getNome() + " | Papel: " + u.getPapel() + " | Vilão: " + u.isVilao());
                                                    }

                                                    System.out.print("Digite o nome do usuário que deseja deletar: ");
                                                    String nomeParaDeletar = sqlScanner.nextLine();

                                                    // Verificar se o usuário existe
                                                    boolean usuarioExiste = false;
                                                    for (Usuarios u : usuariosExistentes) {
                                                        if (u.getNome().equalsIgnoreCase(nomeParaDeletar)) {
                                                            usuarioExiste = true;
                                                            break;
                                                        }
                                                    }

                                                    if (!usuarioExiste) {
                                                        System.out.println("Usuário com nome '" + nomeParaDeletar + "' não encontrado.");
                                                        break;
                                                    }

                                                    System.out.print(ANSI_RED + "ATENÇÃO: Esta ação irá deletar permanentemente o usuário '" + nomeParaDeletar + "'. Confirma? (s/N): " + ANSI_RESET);
                                                    String confirmacaoDelete = sqlScanner.nextLine();

                                                    if (confirmacaoDelete.equalsIgnoreCase("s") || confirmacaoDelete.equalsIgnoreCase("sim")) {
                                                        boolean sucessoDelecao = usuariosDAO.deleteUsuario(nomeParaDeletar);

                                                        if (sucessoDelecao) {
                                                            System.out.println("Usuário '" + nomeParaDeletar + "' deletado com sucesso!");
                                                        } else {
                                                            System.out.println("Erro ao deletar usuário.");
                                                        }
                                                    } else {
                                                        System.out.println("Operação cancelada.");
                                                    }
                                                }
                                                default -> System.out.println(ANSI_RED + "Opção inválida!" + ANSI_RESET);
                                            }
                                        }
                                    }

                                    case 4 -> {
                                        int opcaoSelect = mostrarMenuSelect();
                                        if (opcaoSelect == 0) {
                                            break;
                                        }

                                        switch (opcaoSelect) {
                                            case 1 -> {
                                                // Listar Protagonistas (tabela 1 - Protagonista)
                                                System.out.println("\n=== LISTA DE PROTAGONISTAS ===");
                                                List<Protagonista> protagonistas = protagonistaDAO.selectProtagonista();

                                                if (protagonistas.isEmpty()) {
                                                    System.out.println("Nenhum protagonista encontrado.");
                                                } else {
                                                    System.out.println("Protagonistas cadastrados:");
                                                    System.out.println("------------------------------------------------");
                                                    for (Protagonista p : protagonistas) {
                                                        System.out.println("ID: " + p.getId());
                                                        System.out.println("Nome: " + p.getNome());
                                                        System.out.println("Idade: " + p.getIdade());
                                                        System.out.println("Gênero: " + p.getGenero());
                                                        System.out.println("Nível: " + p.getNivel());
                                                        System.out.println("Arcana: " + p.getArcana());
                                                        System.out.println("HP: " + p.getHp());
                                                        System.out.println("SP: " + p.getSp());
                                                        System.out.println("Saldo: " + p.getSaldo());
                                                        System.out.println("ID Ativador: " + p.getAtivador().getIdAtivador());
                                                        System.out.println("------------------------------------------------");
                                                    }
                                                }
                                            }

                                            case 2 -> {
                                                // Listar NPCs (tabela 2 - NPC)
                                                System.out.println("\n=== LISTA DE NPCs ===");
                                                List<NPC> newNpcs = npcdao.selectNPC();

                                                if (newNpcs.isEmpty()) {
                                                    System.out.println("Nenhum NPC encontrado.");
                                                } else {
                                                    System.out.println("NPCs cadastrados:");
                                                    System.out.println("==========================================");
                                                    for (NPC n : newNpcs) {
                                                        System.out.println("Nome: " + n.getNome());
                                                        System.out.println("Idade: " + n.getIdade());
                                                        System.out.println("Gênero: " + n.getGenero());
                                                        System.out.println("Ocupação: " + n.getOcupacao());
                                                        System.out.println("Arcana: " + n.getArcana());
                                                        System.out.println("==========================================");
                                                    }
                                                }
                                            }

                                            // Um dado de uma tabela:

                                            case 3 -> {

                                                // Listar Usuarios (tabela 3 - Usuarios)
                                                System.out.println("\n=== LISTA DE USUÁRIOS ===");
                                                List<Usuarios> usuario = usuariosDAO.selectUsuarios();

                                                if (usuario.isEmpty()) {
                                                    System.out.println("Nenhum usuário encontrado.");
                                                } else {
                                                    System.out.println("Usuários cadastrados:");
                                                    System.out.println("================================================");
                                                    for (Usuarios u : usuario) {
                                                        System.out.println("ID: " + u.getId());
                                                        System.out.println("Nome: " + u.getNome());
                                                        System.out.println("Idade: " + u.getIdade());
                                                        System.out.println("Gênero: " + u.getGenero());
                                                        System.out.println("Nível: " + u.getNivel());
                                                        System.out.println("Arcana: " + u.getArcana());
                                                        System.out.println("HP: " + u.getHp());
                                                        System.out.println("SP: " + u.getSp());
                                                        System.out.println("Papel: " + u.getPapel());
                                                        System.out.println("Vilão: " + (u.isVilao() ? "Sim" : "Não"));
                                                        System.out.println("================================================");
                                                    }
                                                }
                                            }

                                            case 4 -> {
                                                // Buscar arcana de um NPC específico (dado específico de uma tabela)
                                                Scanner scanner = new Scanner(System.in);
                                                System.out.println("\n=== BUSCAR ARCANA DE NPC ===");
                                                System.out.print("Digite o nome do NPC: ");
                                                String nomeNPC = scanner.nextLine();

                                                String arcana = npcdao.selectArcana(nomeNPC);

                                                if (arcana != null) {
                                                    System.out.println("==========================================");
                                                    System.out.println("NPC: " + nomeNPC);
                                                    System.out.println("Arcana: " + arcana);
                                                    System.out.println("==========================================");
                                                } else {
                                                    System.out.println("NPC '" + nomeNPC + "' não encontrado.");
                                                }
                                            }

                                            case 5 -> {
                                                // Listar Personas de Protagonista(tabela 4 - ProtagonistaHasPersona)
                                                System.out.println("\n=== LISTA DE PERSONAS DE PROTAGONISTA ===");
                                                // Chama o metodo que faz o SELECT com JOIN
                                                List<String> protagonistaPersonas = new ProtagonistaHasPersonaDAO().SelectProtagonistaPersona();
                                                if (protagonistaPersonas.isEmpty()) {
                                                    System.out.println("Nenhuma relação Protagonista-Persona encontrada.");
                                                } else {
                                                    System.out.println("Relações Protagonista-Persona encontradas:");
                                                    System.out.println("========================================================");

                                                    // Itera sobre a lista e exibe cada resultado
                                                    for (String relacao : protagonistaPersonas) {
                                                        System.out.println(relacao);
                                                    }
                                                    System.out.println("========================================================");
                                                }

                                            }

                                            case 6 -> {
                                                // Listar Personas de Usuario (tabela 5 - UsuarioHasPersona)
                                                System.out.println("\n=== LISTA DE PERSONAS DE USUARIO ===");
                                                // Chama o método que faz o SELECT com JOIN
                                                List<String> usuarioPersonas = new UsuarioHasPersonaDAO().selectUsuarioPersona();
                                                if (usuarioPersonas.isEmpty()) {
                                                    System.out.println("Nenhuma relação Usuario-Persona encontrada.");
                                                } else {
                                                    System.out.println("Relações Usuario-Persona encontradas:");
                                                    System.out.println("========================================================");

                                                    // Itera sobre a lista e exibe cada resultado
                                                    for (String relacao : usuarioPersonas) {
                                                        System.out.println(relacao);
                                                    }
                                                    System.out.println("========================================================");
                                                }
                                            }
                                            default -> System.out.println(ANSI_RED + "Opção inválida!" + ANSI_RESET);
                                        }
                                    }
                                }
                            } catch (InvalidMenuInputException e) {
                                throw new InvalidMenuInputException(e.getMessage());
                            }
                        }
                    }
                    default -> System.out.println(ANSI_RED + "Número inválido ou caractere inserido!" + ANSI_RESET);
                }
            }catch (InvalidMenuInputException e) {
                System.out.println(ANSI_RED + "Erro: " + e.getMessage() + ANSI_RESET);
                sc.nextLine(); // Limpa o buffer de entrada
            }
        }
    }
}
