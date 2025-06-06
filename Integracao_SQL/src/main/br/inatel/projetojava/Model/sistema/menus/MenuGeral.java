package main.br.inatel.projetojava.Model.sistema.menus;

import main.br.inatel.projetojava.DAO.*;
import main.br.inatel.projetojava.Model.exceptions.InvalidMenuInputException;
import main.br.inatel.projetojava.Model.itens.lojas.FarmaciaAohige;
import main.br.inatel.projetojava.Model.itens.geral.Itens;
import main.br.inatel.projetojava.Model.itens.lojas.LojadeArmas;
import main.br.inatel.projetojava.Model.itens.equipaveis.Arma;
import main.br.inatel.projetojava.Model.itens.auxiliares.Consumiveis;
import main.br.inatel.projetojava.Model.itens.equipaveis.Equipamento;
import main.br.inatel.projetojava.Model.personagens.abstratos.SerHumano;
import main.br.inatel.projetojava.Model.personagens.abstratos.UsuarioPersona;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Protagonista;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Usuarios;
import main.br.inatel.projetojava.Model.personagens.NPC;
import main.br.inatel.projetojava.Model.personas.Habilidades;
import main.br.inatel.projetojava.Model.personas.TiposPersona;
import main.br.inatel.projetojava.Model.personas.seres.Personas;
import main.br.inatel.projetojava.Model.personas.seres.Shadow;
import main.br.inatel.projetojava.Model.relacional.ProtagonistaHasPersona;
import main.br.inatel.projetojava.Model.sistema.Cidade;
import main.br.inatel.projetojava.Model.sistema.avaliacao.SistemaAvaliacao;
import main.br.inatel.projetojava.Model.threads.AudioManager;

import java.util.*;

import static main.br.inatel.projetojava.Model.itens.lojas.LojaUtil.visitarLoja;
import static main.br.inatel.projetojava.Model.personagens.combate.CombateManager.iniciarCombate;
import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;
import static main.br.inatel.projetojava.Model.sistema.menus.MenuBuscas.mostrar_menu_buscas;
import static main.br.inatel.projetojava.Model.sistema.menus.MenuCidade.mostrarMenuCidade;
import static main.br.inatel.projetojava.Model.sistema.menus.MenuDoJogo.mostrar_menu_principal;
import static main.br.inatel.projetojava.Model.sistema.menus.MenuSQL.mostrarMenuSQL;
import static main.br.inatel.projetojava.Model.sistema.menus.MethodUtil.*;

public class MenuGeral {
    public static void mostrarMenuGeral(){
        // ------------------------------------ Menus: ------------------------------------ //
        Random random = new Random();
        AudioManager.getInstance().setGameStateMusic(AudioManager.GameState.MAIN_MENU);

        ProtagonistaDAO protagonistaDAO = new ProtagonistaDAO();
        Protagonista protagonista = new Protagonista("Makoto Yuki", 17, "Masculino", 20, "The Fool",100.00, 50, 10000.00, 4);
        protagonistaDAO.insertProtagonista(protagonista);
        protagonistaDAO.selectProtagonista();
        int idProtagonista = protagonistaDAO.insertProtagonista(protagonista);
        System.out.println(idProtagonista);

        // ---------------------------------------- Personas do Makoto --------------------------------

        PersonasDAO personasDAO = new PersonasDAO();
        protagonista.getPersonas().add(new Personas("Alice", 20, "Death", List.of(TiposPersona.getTipoProtagonistaPorIndice(0)), "Bless", "Dark", random.nextDouble(50) + 30)); // forte em Dark, fraca contra Bless
        protagonista.getPersonas().add(new Personas("Eligor", 25, "Tower", List.of(TiposPersona.getTipoProtagonistaPorIndice(1)), "Ice", "Fire", random.nextDouble(50) + 30)); // forte contra fogo, fraco contra gelo
        protagonista.getPersonas().add(new Personas("Arsène", 1, "Fool", List.of(TiposPersona.getTipoProtagonistaPorIndice(2)), "Ice", "Dark", random.nextDouble(50) + 30)); // forte em Dark, fraco contra gelo
        protagonista.getPersonas().add(new Personas("Jack-o'-Lantern", 2, "Magician", List.of(TiposPersona.getTipoProtagonistaPorIndice(3)), "Gun", "Fire", random.nextDouble(50) + 30)); // forte em Fire, fraco contra Gun
        protagonista.getPersonas().add(new Personas("Pixie", 3, "Lovers", List.of(TiposPersona.getTipoProtagonistaPorIndice(4)), "Gun", "Electricity", random.nextDouble(50) + 30)); // forte em Electric, fraca contra Gun
        protagonista.getPersonas().add(new Personas("Incubus", 5, "Devil", List.of(TiposPersona.getTipoProtagonistaPorIndice(5)), "Bless", "Dark", random.nextDouble(50) + 30)); // forte em Dark, fraco contra luz
        protagonista.getPersonas().add(new Personas("Succubus", 8, "Moon", List.of(TiposPersona.getTipoProtagonistaPorIndice(6)), "Bless", "Dark", random.nextDouble(50) + 30)); // forte em Dark, fraca contra luz
        protagonista.getPersonas().add(new Personas("Silky", 4, "Priestess", List.of(TiposPersona.getTipoProtagonistaPorIndice(7)), "Fire", "Ice", random.nextDouble(50) + 30)); // forte em Ice, fraca contra fogo
        protagonista.getPersonas().add(new Personas("Orobas", 17, "Hierophant", List.of(TiposPersona.getTipoProtagonistaPorIndice(8)), "Ice", "Fire", random.nextDouble(50) + 30)); // forte em Fire, fraco contra gelo
        protagonista.getPersonas().add(new Personas("Bicorn", 10, "Hermit", List.of(TiposPersona.getTipoProtagonistaPorIndice(9)), "Light", "Physical", random.nextDouble(50) + 30)); // forte em Physical, fraco contra Light
        ProtagonistaPersonaDAO protagonistaPersonaDAO = new ProtagonistaPersonaDAO();
        for (Personas persona : protagonista.getPersonas()) {

            personasDAO.insertPersona(persona); // garantia de existência no banco de dados
            ProtagonistaHasPersona relacao = new ProtagonistaHasPersona(idProtagonista, persona.getIdPersona());
            protagonistaPersonaDAO.insertProtagonistaPersona(relacao);
        }

        protagonista.setPersona_atual(protagonista.getPersonas().getFirst());

        // ---------------------------------------- Heróis(SEES): ----------------------------------------

        UsuariosDAO usuariosDAO = new UsuariosDAO();
        Map<String, Usuarios> user = new HashMap<>(); // Busca fácil por nome(além de facilitar saber qual é)

        user.put("Yukari", new Usuarios("Yukari", 16, "Feminino", 10, "Lovers", 100.0, 80.0, "Healer", false));
        user.put("Mitsuru", new Usuarios("Mitsuru", 18, "Feminino", 20, "Empress", 120.0, 100.0, "Ice Caster", false));
        user.put("Junpei", new Usuarios("Junpei Iori", 17, "Masculino", 30, "The Fool", 150.0, 70.0, "Slash Attacker", false));
        user.put("Akihiko", new Usuarios("Akihiko Sanada", 18, "Masculino", 40, "The Fool", 160.0, 85.0, "Electric Striker / Boxer", false));
        user.put("Fuuka", new Usuarios("Fuuka Yamagishi", 16, "Feminino", 45, "Priestess", 90.0, 120.0, "Support / Navigator", false));
        user.put("Aigis", new Usuarios("Aigis", 17, "Feminino", 25, "Aeon", 180.0, 90.0, "Gunner / Tank", false));
        user.put("Koromaru", new Usuarios("Koromaru", 10, "Masculino", 33, "The Fool", 110.0, 95.0, "Dark/Fire User", false));
        user.put("Ken", new Usuarios("Ken Amada", 10, "Masculino", 60, "The Fool", 100.0, 110.0, "Light/Lance User", false));
        user.put("Shinjiro", new Usuarios("Shinjiro Aragaki", 18, "Masculino", 55, "The Fool", 200.0, 65.0, "Brute Physical Attacker", false));

        // Inserção de usuários SEES no banco de dados:
        String[] usuariosSEES = {"Yukari", "Mitsuru", "Junpei", "Akihiko", "Fuuka",
                "Aigis", "Koromaru", "Ken", "Shinjiro"};
        for (String nomeUsuario : usuariosSEES) {
            usuariosDAO.insertUsuario(user.get(nomeUsuario));
        }

        // ---------------------------------------- Vilões (Strega): ----------------------------------------------------

        user.put("Takaya", new Usuarios("Takaya Sakaki", 20, "Masculino", 23, "Fortune", 160.0, 140.0, "Dark Caster / Líder da Strega", true));
        user.put("Jin", new Usuarios("Jin Shirato", 19, "Masculino", 10, "Hermit", 120.0, 130.0, "Support Hacker / Tech", true));
        user.put("Chidori", new Usuarios("Chidori Yoshino", 17, "Feminino", 99, "Hanged Man", 130.0, 150.0, "Fire Caster / Emo Artista", true));


        // Inserção de usuários STREGA no banco de dados:

        String[] usuariosStrega = {"Takaya", "Jin", "Chidori"};
        for (String nomeUsuario : usuariosStrega) {
            usuariosDAO.insertUsuario(user.get(nomeUsuario));
        }

// ---------------------------------------- Personas: ---------------------------------------------------

//                                        Personas SEES:
        user.get("Yukari").addPersona(new Personas("Isis", 45, "Lovers", List.of(TiposPersona.getTipoUsuarioPorIndice(0)), "Electricity", "Wind", random.nextDouble(50) + 30, 0));
        user.get("Mitsuru").addPersona(new Personas("Artemisia", 48, "Empress", List.of(TiposPersona.getTipoUsuarioPorIndice(1)), "Fire", "Ice", random.nextDouble(50) + 30, 1));
        user.get("Junpei").addPersona(new Personas("Trismegistus", 43, "Magician", List.of(TiposPersona.getTipoUsuarioPorIndice(2)), "Wind", "Fire", random.nextDouble(50) + 30, 2));
        user.get("Akihiko").addPersona(new Personas("Caesar", 47, "Emperor", List.of(TiposPersona.getTipoUsuarioPorIndice(3)), "Pierce", "Electricity", random.nextDouble(50) + 30, 3));
        user.get("Fuuka").addPersona(new Personas("Juno", 40, "Priestess", List.of(TiposPersona.getTipoUsuarioPorIndice(4)), "Physical", "Support", 0, 4)); // suporte, não combate direto
        user.get("Aigis").addPersona(new Personas("Athena", 50, "Chariot", List.of(TiposPersona.getTipoUsuarioPorIndice(5)), "Electricity", "Strike", random.nextDouble(50) + 30, 5));
        user.get("Koromaru").addPersona(new Personas("Cerberus", 42, "Strength", List.of(TiposPersona.getTipoUsuarioPorIndice(6)), "Ice", "Dark", random.nextDouble(50) + 30, 6));
        user.get("Ken").addPersona(new Personas("Kala-Nemi", 41, "Justice", List.of(TiposPersona.getTipoUsuarioPorIndice(7)), "Darkness", "Light", random.nextDouble(50) + 30, 7));
        user.get("Shinjiro").addPersona(new Personas("Castor", 46, "Hierophant", List.of(TiposPersona.getTipoUsuarioPorIndice(8)), "Light", "Physical", random.nextDouble(50) + 30, 8));

        UsuarioHasPersonaDAO usuarioHasPersonaDAO = new UsuarioHasPersonaDAO();

        PersonasDAO personasDAOuser = new PersonasDAO();

        for (String nomeUsuario : usuariosSEES) {
            personasDAOuser.insertPersona(user.get(nomeUsuario).getPersonas());
        }

        usuarioHasPersonaDAO.insertUsuarioHasPersona(user.get("Yukari").getId(), user.get("Yukari").getPersonas().getIdPersona());
        usuarioHasPersonaDAO.insertUsuarioHasPersona(user.get("Mitsuru").getId(), user.get("Mitsuru").getPersonas().getIdPersona());
        usuarioHasPersonaDAO.insertUsuarioHasPersona(user.get("Junpei").getId(), user.get("Junpei").getPersonas().getIdPersona());
        usuarioHasPersonaDAO.insertUsuarioHasPersona(user.get("Akihiko").getId(), user.get("Akihiko").getPersonas().getIdPersona());
        usuarioHasPersonaDAO.insertUsuarioHasPersona(user.get("Fuuka").getId(), user.get("Fuuka").getPersonas().getIdPersona());
        usuarioHasPersonaDAO.insertUsuarioHasPersona(user.get("Aigis").getId(), user.get("Aigis").getPersonas().getIdPersona());
        usuarioHasPersonaDAO.insertUsuarioHasPersona(user.get("Koromaru").getId(), user.get("Koromaru").getPersonas().getIdPersona());
        usuarioHasPersonaDAO.insertUsuarioHasPersona(user.get("Ken").getId(), user.get("Ken").getPersonas().getIdPersona());
        usuarioHasPersonaDAO.insertUsuarioHasPersona(user.get("Shinjiro").getId(), user.get("Shinjiro").getPersonas().getIdPersona());

//                                            Strega:
        user.get("Takaya").addPersona(new Personas("Hypnos", 52, "Death", List.of(TiposPersona.getTipoUsuarioPorIndice(9)), "Light", "Dark", random.nextDouble(50) + 30, 9));
        user.get("Jin").addPersona(new Personas("Moros", 50, "Hermit", List.of(TiposPersona.getTipoUsuarioPorIndice(10)), "Bless", "Almighty", random.nextDouble(50) + 30, 10));
        user.get("Chidori").addPersona(new Personas("Medea", 44, "Magician", List.of(TiposPersona.getTipoUsuarioPorIndice(11)), "Ice", "Fire", random.nextDouble(50) + 30, 11));
        for (String nomeUsuario : usuariosStrega) {
            personasDAOuser.insertPersona(user.get(nomeUsuario).getPersonas());
        }

        usuarioHasPersonaDAO.insertUsuarioHasPersona(user.get("Takaya").getId(), user.get("Takaya").getPersonas().getIdPersona());
        usuarioHasPersonaDAO.insertUsuarioHasPersona(user.get("Jin").getId(), user.get("Jin").getPersonas().getIdPersona());
        usuarioHasPersonaDAO.insertUsuarioHasPersona(user.get("Chidori").getId(), user.get("Chidori").getPersonas().getIdPersona());

// ---------------------------------------- NPCs: ----------------------------------------

        Map<String, NPC> npcs = new HashMap<>(); // Facilidade de busca

        npcs.put("Bunkichi e Mitsuko", new NPC("Bunkichi e Mitsuko", 70, "Masculino/Feminino", "Bibliotecários aposentados", "Hierophant"));
        npcs.put("Kenji", new NPC("Kenji Tomochika", 16, "Masculino", "Aluno", "Magician"));
        npcs.put("Kazushi", new NPC("Kazushi Miyamoto", 17, "Masculino", "Atleta do time de corrida", "Chariot"));
        npcs.put("Odagiri", new NPC("Hidetoshi Odagiri", 17, "Masculino", "Membro do conselho estudantil", "Emperor"));
        npcs.put("Yuko", new NPC("Yuko Nishiwaki", 17, "Feminino", "Gerente do time esportivo", "Strength"));
        npcs.put("Chihiro", new NPC("Chihiro Fushimi", 16, "Feminino", "Tesoureira do conselho estudantil", "Justice"));
        npcs.put("Maya", new NPC("Maya", 27, "Feminino", "Professora", "Hermit"));
        npcs.put("Suemitsu", new NPC("Nozomi Suemitsu", 15, "Masculino", "Gourmet King", "Moon"));
        npcs.put("Hiraga", new NPC("Keisuke Hiraga", 17, "Masculino", "Presidente do clube de artes", "Fortune"));
        npcs.put("Akinari", new NPC("Akinari Kamiki", 17, "Masculino", "Poeta", "Sun"));
        npcs.put("Mutatsu", new NPC("Mutatsu", 55, "Masculino", "Monge", "Tower"));
        npcs.put("Maiko", new NPC("Maiko", 8, "Feminino", "Estudante do Jardim", "Hanged Man"));
        npcs.put("Bebé", new NPC("Bebé", 17, "Masculino", "Estudante francês de intercâmbio", "Temperance"));
        npcs.put("Tanaka", new NPC("Tanaka", 36, "Masculino", "Empresário da Tanaka's Amazing Commodities", "Devil"));
        npcs.put("Pharos", new NPC("Pharos", 8, "indefinido", "Manifestaçao da Morte", "Death"));
        npcs.put("Hayase", new NPC("Hayase", 17, "Masculino", "Atleta competitivo", "Star"));

        NPCDAO npcdao = new NPCDAO();
        String[] npcNames = {
                "Bunkichi e Mitsuko", "Kenji", "Kazushi", "Odagiri", "Yuko", "Chihiro", "Maya",
                "Suemitsu", "Hiraga", "Akinari", "Mutatsu", "Maiko", "Bebé", "Tanaka"
        };

        for (String npcName : npcNames) {
            npcdao.insertNPC(npcs.get(npcName));
        }

// ----------------------------------------- Shadows(inimigos): -------------------------------------

        List<Shadow> shadows = new ArrayList<>(); // Poucas inserções e nome indiferente(uso do ArrayList)
        shadows.add(new Shadow("Dancer of Sorrow", 160, 18, "Moon", List.of("Magic"), "Fire", "Ice", random.nextDouble(50) + 30));
        shadows.add(new Shadow("Brutal Ogre", 210, 22, "Strength", List.of("Physical"), "Wind", "Fire", random.nextDouble(50) + 30));
        shadows.add(new Shadow("Twisted Teacher", 130, 14, "Hierophant", List.of("Mental"), "Electricity", "Dark", random.nextDouble(50) + 30));
        shadows.add(new Shadow("Dark Cupid", 100, 11, "Lovers", List.of("Ailment"), "Bless", "Dark", random.nextDouble(50) + 30));
        shadows.add(new Shadow("Searing Beast", 190, 20, "Chariot", List.of("Physical", "Magic"), "Ice", "Fire", random.nextDouble(50) + 30));

        ShadowDAO shadowdao = new ShadowDAO();
        shadowdao.insertShadow(shadows.get(0));
        shadowdao.insertShadow(shadows.get(1));
        shadowdao.insertShadow(shadows.get(2));
        shadowdao.insertShadow(shadows.get(3));
        shadowdao.insertShadow(shadows.get(4));

        //                                  Habilidades das personas do protagonista:
        // ---------------------------------------- Personas habilidades: ----------------------------------------

        protagonista.getPersonas().getFirst().addHabilidade(new Habilidades("Megidolaon", "Almighty", "Causa dano massivo a todos os inimigos", random.nextInt(10) + 20));
        protagonista.getPersonas().getFirst().addHabilidade(new Habilidades("Mudo", "Dark", "Chance de causar morte instantânea a um inimigo", random.nextInt(10) + 20));
        protagonista.getPersonas().getFirst().addHabilidade(new Habilidades("Die For Me!", "Dark", "Alta chance de causar morte instantânea a todos os inimigos", random.nextInt(10) + 20));

        protagonista.getPersonas().get(1).addHabilidade(new Habilidades("Agidyne", "Fire", "Causa dano de fogo pesado a um inimigo", random.nextInt(10) + 20));
        protagonista.getPersonas().get(1).addHabilidade(new Habilidades("Power Charge", "Support", "Aumenta o próximo dano físico causado", 0));
        protagonista.getPersonas().get(1).addHabilidade(new Habilidades("Maragion", "Fire", "Causa dano de fogo médio a todos os inimigos", random.nextInt(10) + 20));

        protagonista.getPersonas().get(2).addHabilidade(new Habilidades("Eiha", "Dark", "Causa dano sombrio leve a um inimigo", random.nextInt(10) + 20));
        protagonista.getPersonas().get(2).addHabilidade(new Habilidades("Lunge", "Physical", "Causa dano físico leve a um inimigo", random.nextInt(10) + 20));
        protagonista.getPersonas().get(2).addHabilidade(new Habilidades("Cleave", "Physical", "Causa dano físico leve a um inimigo", random.nextInt(10) + 20));

        protagonista.getPersonas().get(3).addHabilidade(new Habilidades("Agi", "Fire", "Causa dano de fogo leve a um inimigo", random.nextInt(10) + 20));
        protagonista.getPersonas().get(3).addHabilidade(new Habilidades("Rakunda", "Debuff", "Reduz a defesa de um inimigo", 0));
        protagonista.getPersonas().get(3).addHabilidade(new Habilidades("Tarukaja", "Buff", "Aumenta o ataque de um aliado por 3 turnos", 0));

        protagonista.getPersonas().get(4).addHabilidade(new Habilidades("Zio", "Electric", "Causa dano elétrico leve a um inimigo", random.nextInt(10) + 20));
        protagonista.getPersonas().get(4).addHabilidade(new Habilidades("Dia", "Healing", "Restaura uma pequena quantidade de HP a um aliado", 0));
        protagonista.getPersonas().get(4).addHabilidade(new Habilidades("Patra", "Healing", "Cura um aliado de status negativos", 0));

        protagonista.getPersonas().get(5).addHabilidade(new Habilidades("Dream Needle", "Ailment", "Causa dano físico leve com chance de causar sono", random.nextInt(10) + 20));
        protagonista.getPersonas().get(5).addHabilidade(new Habilidades("Life Drain", "Dark", "Drena uma pequena quantidade de HP de um inimigo", random.nextInt(10) + 20));
        protagonista.getPersonas().get(5).addHabilidade(new Habilidades("Mudo", "Dark", "Chance de causar morte instantânea a um inimigo", random.nextInt(10) + 20));

        protagonista.getPersonas().get(6).addHabilidade(new Habilidades("Tentarafoo", "Ailment", "Chance de causar confusão a todos os inimigos", 0));
        protagonista.getPersonas().get(6).addHabilidade(new Habilidades("Evil Touch", "Ailment", "Chance de causar medo a um inimigo", 0));
        protagonista.getPersonas().get(6).addHabilidade(new Habilidades("Spirit Drain", "Support", "Drena uma pequena quantidade de SP de um inimigo", 0));

        protagonista.getPersonas().get(7).addHabilidade(new Habilidades("Bufu", "Ice", "Causa dano de gelo leve a um inimigo", random.nextInt(10) + 20));
        protagonista.getPersonas().get(7).addHabilidade(new Habilidades("Media", "Healing", "Restaura uma pequena quantidade de HP a todos os aliados", 0));
        protagonista.getPersonas().get(7).addHabilidade(new Habilidades("Rakukaja", "Buff", "Aumenta a defesa de um aliado por 1 turno", 0));

        protagonista.getPersonas().getLast().addHabilidade(new Habilidades("Maragi", "Fire", "Causa dano de fogo leve a todos os inimigos", random.nextInt(10) + 20));
        protagonista.getPersonas().getLast().addHabilidade(new Habilidades("Dekunda", "Support", "Remove penalidades de status de todos os aliados", 0));
        protagonista.getPersonas().getLast().addHabilidade(new Habilidades("Sukukaja", "Buff", "Aumenta a precisão e evasão de um aliado por 1 turno", 0));

        HabilidadesDAO habilidadesdao = new HabilidadesDAO();

        for (Personas persona : protagonista.getPersonas()) {
            for (Habilidades habilidade : persona.getHabilidades()) {
                habilidadesdao.insertHabilidade(habilidade);
            }
        }

        // Habilidades dos usuários (SEES e Strega):
        // ---------------------------------------- Personas habilidadeds: ----------------------------------------

        user.get("Yukari").getPersonas().addHabilidade(new Habilidades("Agilao", "Fire", "Causa dano de fogo médio a um inimigo", random.nextInt(10) + 20));
        user.get("Yukari").getPersonas().addHabilidade(new Habilidades("Rebellion", "Support", "Aumenta a taxa de acerto crítico de um aliado por 3 turnos", 0));
        user.get("Yukari").getPersonas().addHabilidade(new Habilidades("Maragi", "Fire", "Causa dano de fogo leve a todos os inimigos", random.nextInt(10) + 20));
        user.get("Yukari").getPersonas().addHabilidade(new Habilidades("Fatal End", "Physical", "Causa dano físico pesado a um inimigo", random.nextInt(10) + 20));

        user.get("Junpei").getPersonas().addHabilidade(new Habilidades("Zio", "Electric", "Causa dano elétrico leve a um inimigo", random.nextInt(10) + 20));
        user.get("Junpei").getPersonas().addHabilidade(new Habilidades("Maziodyne", "Electric", "Causa dano elétrico pesado a todos os inimigos com chance de causar choque", random.nextInt(10) + 20));
        user.get("Junpei").getPersonas().addHabilidade(new Habilidades("Sonic Punch", "Physical", "Causa dano físico leve a um inimigo com chance de causar aflição", random.nextInt(10) + 20));
        user.get("Junpei").getPersonas().addHabilidade(new Habilidades("Fist Master", "Passive", "Dobra o dano causado por armas de punho", 0));

        user.get("Mitsuru").getPersonas().addHabilidade(new Habilidades("Bufula", "Ice", "Causa dano de gelo médio a um inimigo com chance de congelar", random.nextInt(10) + 20));
        user.get("Mitsuru").getPersonas().addHabilidade(new Habilidades("Tentarafoo", "Ailment", "Chance de causar confusão a todos os inimigos", random.nextInt(10) + 20));
        user.get("Mitsuru").getPersonas().addHabilidade(new Habilidades("Ice Boost", "Passive", "Aumenta o dano de habilidades de gelo em 25%", 0));
        user.get("Mitsuru").getPersonas().addHabilidade(new Habilidades("Mabufula", "Ice", "Causa dano de gelo médio a todos os inimigos com chance de congelar", random.nextInt(10) + 20));

        user.get("Akihiko").getPersonas().addHabilidade(new Habilidades("Kill Rush", "Physical", "Causa dano físico leve a um inimigo múltiplas vezes", random.nextInt(10) + 20));
        user.get("Akihiko").getPersonas().addHabilidade(new Habilidades("Tarukaja", "Support", "Aumenta o ataque de um aliado por 3 turnos", 0));
        user.get("Akihiko").getPersonas().addHabilidade(new Habilidades("Swift Strike", "Physical", "Causa dano físico leve a todos os inimigos múltiplas vezes", random.nextInt(10) + 20));
        user.get("Akihiko").getPersonas().addHabilidade(new Habilidades("Makarakarn", "Support", "Reflete o próximo ataque mágico recebido por um aliado", random.nextInt(10) + 20));

        user.get("Ken").getPersonas().addHabilidade(new Habilidades("Hama", "Light", "Chance de causar morte instantânea a um inimigo", random.nextInt(10) + 20));
        user.get("Ken").getPersonas().addHabilidade(new Habilidades("Zan-ei", "Light", "Causa dano de luz leve a um inimigo", random.nextInt(10) + 20));
        user.get("Ken").getPersonas().addHabilidade(new Habilidades("Media", "Healing", "Restaura uma pequena quantidade de HP a todos os aliados", 0));
        user.get("Ken").getPersonas().addHabilidade(new Habilidades("Recarm", "Healing", "Revive um aliado com metade do HP", 0));

        user.get("Koromaru").getPersonas().addHabilidade(new Habilidades("Agi", "Fire", "Causa dano de fogo leve a um inimigo", random.nextInt(10) + 20));
        user.get("Koromaru").getPersonas().addHabilidade(new Habilidades("Mudoon", "Dark", "Alta chance de causar morte instantânea a um inimigo", random.nextInt(10) + 20));
        user.get("Koromaru").getPersonas().addHabilidade(new Habilidades("Fire Boost", "Passive", "Aumenta o dano de habilidades de fogo em 25%", 0));
        user.get("Koromaru").getPersonas().addHabilidade(new Habilidades("Hell Fang", "Physical", "Causa dano físico médio a um inimigo", random.nextInt(10) + 20));

        user.get("Aigis").getPersonas().addHabilidade(new Habilidades("Fatal End", "Physical", "Causa dano físico pesado a um inimigo", random.nextInt(10) + 20));
        user.get("Aigis").getPersonas().addHabilidade(new Habilidades("Int Fangs", "Physical", "Causa dano físico leve a um inimigo duas vezes", random.nextInt(10) + 20));
        user.get("Aigis").getPersonas().addHabilidade(new Habilidades("Power Charge", "Support", "Aumenta o próximo dano físico causado por um aliado", 0));
        user.get("Aigis").getPersonas().addHabilidade(new Habilidades("Gigantic Fist", "Physical", "Causa dano físico muito pesado a um inimigo", random.nextInt(10) + 20));

        user.get("Shinjiro").getPersonas().addHabilidade(new Habilidades("Fatal End", "Physical", "Causa dano físico pesado a um inimigo", random.nextInt(10) + 20));
        user.get("Shinjiro").getPersonas().addHabilidade(new Habilidades("Kill Rush", "Physical", "Causa dano físico leve a um inimigo múltiplas vezes", random.nextInt(10) + 20));
        user.get("Shinjiro").getPersonas().addHabilidade(new Habilidades("Counterstrike", "Passive", "Chance de 15% de contra-atacar com dano físico médio", 0));

        // T A K A Y A
        user.get("Takaya").getPersonas().addHabilidade(new Habilidades("Mamudoon", "Dark", "Alta chance de causar morte instantânea a todos os inimigos", random.nextInt(10) + 15));
        user.get("Takaya").getPersonas().addHabilidade(new Habilidades("Eigaon", "Dark", "Causa grande dano de escuridão a um inimigo", random.nextInt(10) + 15));
        user.get("Takaya").getPersonas().addHabilidade(new Habilidades("Megidola", "Almighty", "Causa dano mágico moderado a todos os inimigos", random.nextInt(10) + 15));
        user.get("Takaya").getPersonas().addHabilidade(new Habilidades("Mudo", "Dark", "Causa dano leve com chance de morte instantânea a um inimigo", random.nextInt(10) + 15));

        // J I N
        user.get("Jin").getPersonas().addHabilidade(new Habilidades("Bufula", "Ice", "Causa dano de gelo moderado a um inimigo", random.nextInt(10) + 15));
        user.get("Jin").getPersonas().addHabilidade(new Habilidades("Mabufula", "Ice", "Causa dano de gelo médio a todos os inimigos", random.nextInt(10) + 15));
        user.get("Jin").getPersonas().addHabilidade(new Habilidades("Blizzaga", "Ice", "Causa grande dano de gelo a um inimigo", random.nextInt(10) + 15));
        user.get("Jin").getPersonas().addHabilidade(new Habilidades("Diamond Dust", "Ice", "Ataque de gelo devastador contra todos os inimigos", random.nextInt(10) + 15));

        // C H I D O R I
        user.get("Chidori").getPersonas().addHabilidade(new Habilidades("Agilao", "Fire", "Causa dano de fogo moderado a um inimigo", random.nextInt(10) + 15));
        user.get("Chidori").getPersonas().addHabilidade(new Habilidades("Maragion", "Fire", "Causa dano de fogo médio a todos os inimigos", random.nextInt(10) + 15));
        user.get("Chidori").getPersonas().addHabilidade(new Habilidades("Inferno", "Fire", "Causa dano massivo de fogo a todos os inimigos", random.nextInt(10) + 15));
        user.get("Chidori").getPersonas().addHabilidade(new Habilidades("Fire Amp", "Fire", "Causa explosão flamejante com dano alto", random.nextInt(10) + 15));


        HabilidadesDAO habilidadesdaouser = new HabilidadesDAO();

        String[] usuarios = {"Yukari", "Junpei", "Mitsuru", "Akihiko", "Ken", "Koromaru",
                "Aigis", "Shinjiro", "Takaya", "Jin", "Chidori"};

        for (String nomeUsuario : usuarios) {
            List<Habilidades> habilidades = user.get(nomeUsuario).getPersonas().getHabilidades();
            for (Habilidades habilidade : habilidades) {
                habilidadesdaouser.insertHabilidade(habilidade);
            }
        }
        //                                         Adicionar Itens:

        // HashMap - facilidade na busca e compra:
        Map<String, Itens> item = new HashMap<>();
        EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
        ArmaDAO armaDAO = new ArmaDAO();
        ConsumiveisDAO consumiveisDAO = new ConsumiveisDAO();

        // ---------------------------------------- Equipamentos: ----------------------------------------

        item.put("God Hand", new Equipamento("God Hand", "Luva", 220.0, 2, "Crit Chance +15%", "unissex"));
        item.put("Kongou Hakama", new Equipamento("Kongou Hakama", "Armadura", 300.0, 3, "Resist Físico", "unissex"));
        item.put("Regent", new Equipamento("Regent", "Acessório", 150.0, 4, "Mag +10%", "unissex"));
        item.put("Black Headband", new Equipamento("Black Headband", "Cabeça", 120.0, 5, "Esquiva +10%", "unissex"));
        item.put("Omnipotent Orb", new Equipamento("Omnipotent Orb", "Especial", 5000.0, 6, "Anula Fraquezas", "unissex"));
        item.put("Sabbath Gloves", new Equipamento("Sabbath Gloves", "Luva", 200.0, 7, "Atk +10% / HP -5%", "unissex"));
        item.put("Dark Empress Dress", new Equipamento("Dark Empress Dress", "Armadura", 280.0, 8, "SP Regen +5/tur", "unissex"));
        item.put("Eternal Slippers", new Equipamento("Eternal Slippers", "Botas", 170.0, 9, "Velocidade +15%", "unissex"));
        item.put("Seven Sisters Badge", new Equipamento("Seven Sisters Badge", "Acessório", 130.0, 10, "Status Negativos -50%", "unissex"));

        equipamentoDAO.insertEquipamento((Equipamento) item.get("God Hand"));
        equipamentoDAO.insertEquipamento((Equipamento) item.get("Kongou Hakama"));
        equipamentoDAO.insertEquipamento((Equipamento) item.get("Regent"));
        equipamentoDAO.insertEquipamento((Equipamento) item.get("Black Headband"));
        equipamentoDAO.insertEquipamento((Equipamento) item.get("Omnipotent Orb"));
        equipamentoDAO.insertEquipamento((Equipamento) item.get("Sabbath Gloves"));
        equipamentoDAO.insertEquipamento((Equipamento) item.get("Dark Empress Dress"));
        equipamentoDAO.insertEquipamento((Equipamento) item.get("Eternal Slippers"));
        equipamentoDAO.insertEquipamento((Equipamento) item.get("Seven Sisters Badge"));

        // ---------------------------------------- Armas: ------------------------------------------
        item.put("Paradise Lost", new Arma("Paradise Lost", "Espada", 250.0, "Raro", 65.0));
        item.put("Espada Curta", new Arma("Espada Curta", "Espada", 1500.0, "Normal", 48.0));
        item.put("Luvas de Combate", new Arma("Luvas de Combate", "Soco", 1800.0, "Normal", 52.0));
        item.put("Naginata", new Arma("Naginata", "Lança", 2000.0, "Normal", 56.0));
        item.put("Arco do Vento", new Arma("Arco do Vento", "Arco", 2200.0, "Raro", 60.0));

        armaDAO.insertArma((Arma) item.get("Paradise Lost"));
        armaDAO.insertArma((Arma) item.get("Espada Curta"));
        armaDAO.insertArma((Arma) item.get("Luvas de Combate"));
        armaDAO.insertArma((Arma) item.get("Naginata"));
        armaDAO.insertArma((Arma) item.get("Arco do Vento"));

        // ------------------------------------ Consumíveis -------------------------------------------------
        item.put("Revival Bead", new Consumiveis("Revival Bead", "Revivir", 600, "Revive um aliado com 50% de HP"));
        item.put("Bead Chain", new Consumiveis("Bead Chain", "Cura", 1000, "Restaura totalmente o HP de todos os aliados"));
        item.put("Soul Drop", new Consumiveis("Soul Drop", "SP", 150, "Recupera 10 SP de um aliado"));
        item.put("Snuff Soul", new Consumiveis("Snuff Soul", "SP", 400, "Recupera 50 SP de um aliado"));
        item.put("Medical Powder", new Consumiveis("Medical Powder", "Cura", 100, "Restaura 50 HP de um aliado"));

        consumiveisDAO.insertConsumivel((Consumiveis) item.get("Revival Bead"));
        consumiveisDAO.insertConsumivel((Consumiveis) item.get("Bead Chain"));
        consumiveisDAO.insertConsumivel((Consumiveis) item.get("Soul Drop"));
        consumiveisDAO.insertConsumivel((Consumiveis) item.get("Snuff Soul"));
        consumiveisDAO.insertConsumivel((Consumiveis) item.get("Medical Powder"));

        // -------------------------------------- Loja de Armas (APENAS Armas e Equipamentos): -------------------------------------

        Map<Itens, Integer> estoque = new HashMap<>();
        LojadeItensDAO lojadeItensDAO = new LojadeItensDAO();

        int j = 0;
        // Adiciona APENAS Equipamentos e Armas ao estoque
        for (Itens i : item.values()) {
            if (i instanceof Equipamento || i instanceof Arma) {
                estoque.put(i, 1);
                lojadeItensDAO.insertItem(i, j, 1);
                j++;
            }
        }

        LojadeArmas lojaArmas = new LojadeArmas("Untouchable", estoque);

        // -------------------------------------- Farmácia (APENAS Consumíveis): -------------------------------------

        Map<Itens, Integer> armazem = new HashMap<>();

        // Adiciona APENAS Consumíveis ao armazém
        for (Itens i : item.values()) {
            if (i instanceof Consumiveis) {
                armazem.put(i, 1);
                lojadeItensDAO.insertItem(i, j, 1);
                j++;
            }
        }

        FarmaciaAohige farmacia = new FarmaciaAohige("Farmácia Aohige", armazem);


        // --------------------------------- Implementação da cidade ---------------------------------
        Cidade tatsumiPort = new Cidade("Tatsumi Port Island");

        // Adicionando aos locais
        tatsumiPort.adicionarPersonagemAoLocal("Dormitório", user.get("Yukari"));
        tatsumiPort.adicionarPersonagemAoLocal("Dormitório", user.get("Junpei"));
        tatsumiPort.adicionarPersonagemAoLocal("Escola", user.get("Mitsuru"));
        tatsumiPort.adicionarPersonagemAoLocal("Rua", user.get("Takaya"));
        tatsumiPort.adicionarPersonagemAoLocal("Rua", user.get("Chidori"));
        tatsumiPort.adicionarPersonagemAoLocal("Shopping", user.get("Akihiko"));
        tatsumiPort.adicionarPersonagemAoLocal("Shopping", user.get("Aigis"));
        tatsumiPort.adicionarPersonagemAoLocal("Loja de Itens", user.get("Fuuka"));

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
                                            System.out.println(ANSI_BLUE + "1. Protagonista");
                                            System.out.println("2. Aliado");
                                            System.out.println("0. Sair" + ANSI_RESET);

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
                                        // Fazer a interação com o usuário localmente
                                    /*ArrayList<SerHumano> pessoas = tatsumiPort.getPersonagensNoLocal(localAtual);
                                    if (pessoas.isEmpty()) {
                                        System.out.println(ANSI_YELLOW + "Ninguém por aqui..." + ANSI_RESET);
                                        break;
                                    }
                                    System.out.println(ANSI_GRAY + "Com quem deseja interagir?" + ANSI_RESET);
                                    for (int i = 0; i < pessoas.size(); i++) {
                                        System.out.println(ANSI_BLUE + (i + 1) + ". " + pessoas.get(i).getNome() + ANSI_RESET);
                                    }

                                    try {
                                        System.out.println(ANSI_GRAY);
                                        int escolha = sc.nextInt();
                                        sc.nextLine(); // Limpa buffer
                                        System.out.println(ANSI_RESET);

                                        if (escolha > 0 && escolha <= pessoas.size()) {
                                            System.out.println(ANSI_GREEN + "interagindo" + ANSI_RESET);
                                            //protagonista.interagir(pessoas.get(escolha - 1));
                                        } else {
                                            System.out.println(ANSI_RED + "Escolha inválida!" + ANSI_RESET);
                                        }
                                    } catch (InputMismatchException e) {
                                        System.out.println(ANSI_RED + "Erro: Digite apenas números!" + ANSI_RESET);
                                        sc.nextLine(); // Limpa buffer
                                    }*/
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
                                        Set<String> aliadosSelecionados = new HashSet<>(); // Armazenar os já escolhidos

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

                                            System.out.print("ID Ativador atual: " + protagonistaParaAtualizar.getId() + " | Novo ID Ativador: "); // todo era pra ser get id ativador aq
                                            String novoIdAtivadorStr = sqlScanner.nextLine();
                                            if (!novoIdAtivadorStr.trim().isEmpty()) {
                                                // Assumindo que existe um method para setar o ativador
                                                protagonistaParaAtualizar.setId(Integer.parseInt(novoIdAtivadorStr)); // todo era pra ser set id ativador aq
                                            }

                                            protagonistaDAO.updateProtagonista(protagonistaParaAtualizar);
                                            System.out.println("Protagonista atualizado com sucesso!");
                                        } catch (InputMismatchException e) {
                                            System.out.println("Erro ao atualizar protagonista(Insira um valor inteiro para o ID).");
                                        }
                                    }

                                    case 3 -> {
                                        // Deletar Todos os Protagonistas
                                        System.out.println("\n=== DELETAR TODOS OS PROTAGONISTAS ===");
                                        System.out.print("ATENÇÃO: Esta ação irá deletar TODOS os protagonistas do banco de dados. Confirma? (s/N): ");
                                        String confirmacao = sqlScanner.nextLine();

                                        if (confirmacao.equalsIgnoreCase("s") || confirmacao.equalsIgnoreCase("sim")) {
                                            protagonistaDAO.deleteProtagonista();
                                            System.out.println("Todos os protagonistas foram deletados com sucesso!");
                                        } else {
                                            System.out.println("Operação cancelada.");
                                        }

                                    }

                                    case 4 -> {
                                        // Listar Protagonistas
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

                                    case 5 -> {
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
                                    case 6 -> {
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

                                    case 7 -> {
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

                                    case 8 -> {
                                        // Listar NPCs
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
                                    case 9 -> {
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

                                    case 10 -> {
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

                                    case 11 -> {
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

                                    case 12 -> {
                                        // Listar Usuarios
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
                                    default -> System.out.println(ANSI_RED + "Opção inválida!" + ANSI_RESET);
                                }
                            } catch (InvalidMenuInputException e) {
                                System.out.println(ANSI_RED + "Erro: " + e.getMessage() + ANSI_RESET);
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