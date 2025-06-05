package main.br.inatel.projetojava.Model.sistema.menus;

import main.br.inatel.projetojava.DAO.*;
import main.br.inatel.projetojava.Model.exceptions.InvalidMenuInputException;
import main.br.inatel.projetojava.Model.itens.geral.Itens;
import main.br.inatel.projetojava.Model.itens.geral.LojadeItens;
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
import main.br.inatel.projetojava.Model.relacional.ProtagonistaPersona;
import main.br.inatel.projetojava.Model.sistema.Cidade;
import main.br.inatel.projetojava.Model.sistema.avaliacao.SistemaAvaliacao;
import main.br.inatel.projetojava.Model.threads.AudioManager;

import java.util.*;

import static main.br.inatel.projetojava.Model.personagens.combate.CombateManager.iniciarCombate;
import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;
import static main.br.inatel.projetojava.Model.sistema.menus.MenuBuscas.mostrar_menu_buscas;
import static main.br.inatel.projetojava.Model.sistema.menus.MenuCidade.mostrarMenuCidade;
import static main.br.inatel.projetojava.Model.sistema.menus.MenuDoJogo.mostrar_menu_principal;
import static main.br.inatel.projetojava.Model.sistema.menus.MenuSQL.mostrarMenuSQL;

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
        protagonista.personas.add(new Personas("Alice", 20, "Death", List.of(TiposPersona.getTipoProtagonistaPorIndice(0)), "Bless", "Dark", random.nextDouble(50) + 30)); // forte em Dark, fraca contra Bless
        protagonista.personas.add(new Personas("Eligor", 25, "Tower", List.of(TiposPersona.getTipoProtagonistaPorIndice(1)), "Ice", "Fire", random.nextDouble(50) + 30)); // forte contra fogo, fraco contra gelo
        protagonista.personas.add(new Personas("Arsène", 1, "Fool", List.of(TiposPersona.getTipoProtagonistaPorIndice(2)), "Ice", "Dark", random.nextDouble(50) + 30)); // forte em Dark, fraco contra gelo
        protagonista.personas.add(new Personas("Jack-o'-Lantern", 2, "Magician", List.of(TiposPersona.getTipoProtagonistaPorIndice(3)), "Gun", "Fire", random.nextDouble(50) + 30)); // forte em Fire, fraco contra Gun
        protagonista.personas.add(new Personas("Pixie", 3, "Lovers", List.of(TiposPersona.getTipoProtagonistaPorIndice(4)), "Gun", "Electricity", random.nextDouble(50) + 30)); // forte em Electric, fraca contra Gun
        protagonista.personas.add(new Personas("Incubus", 5, "Devil", List.of(TiposPersona.getTipoProtagonistaPorIndice(5)), "Bless", "Dark", random.nextDouble(50) + 30)); // forte em Dark, fraco contra luz
        protagonista.personas.add(new Personas("Succubus", 8, "Moon", List.of(TiposPersona.getTipoProtagonistaPorIndice(6)), "Bless", "Dark", random.nextDouble(50) + 30)); // forte em Dark, fraca contra luz
        protagonista.personas.add(new Personas("Silky", 4, "Priestess", List.of(TiposPersona.getTipoProtagonistaPorIndice(7)), "Fire", "Ice", random.nextDouble(50) + 30)); // forte em Ice, fraca contra fogo
        protagonista.personas.add(new Personas("Orobas", 17, "Hierophant", List.of(TiposPersona.getTipoProtagonistaPorIndice(8)), "Ice", "Fire", random.nextDouble(50) + 30)); // forte em Fire, fraco contra gelo
        protagonista.personas.add(new Personas("Bicorn", 10, "Hermit", List.of(TiposPersona.getTipoProtagonistaPorIndice(9)), "Light", "Physical", random.nextDouble(50) + 30)); // forte em Physical, fraco contra Light
        ProtagonistaPersonaDAO protagonistaPersonaDAO = new ProtagonistaPersonaDAO();
        for (Personas persona : protagonista.personas) {

            personasDAO.insertPersona(persona); // garantia de existência no banco de dados
            ProtagonistaPersona relacao = new ProtagonistaPersona(idProtagonista, persona.getIdPersona());
            protagonistaPersonaDAO.insertProtagonistaPersona(relacao);
        }

        protagonista.persona_atual = protagonista.personas.getFirst(); // Alice

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
        usuariosDAO.insertUsuario(user.get("Yukari"));
        usuariosDAO.insertUsuario(user.get("Mitsuru"));
        usuariosDAO.insertUsuario(user.get("Junpei"));
        usuariosDAO.insertUsuario(user.get("Akihiko"));
        usuariosDAO.insertUsuario(user.get("Fuuka"));
        usuariosDAO.insertUsuario(user.get("Aigis"));
        usuariosDAO.insertUsuario(user.get("Koromaru"));
        usuariosDAO.insertUsuario(user.get("Ken"));
        usuariosDAO.insertUsuario(user.get("Shinjiro"));

        // ---------------------------------------- Vilões (Strega): ----------------------------------------------------

        user.put("Takaya", new Usuarios("Takaya Sakaki", 20, "Masculino", 23, "Fortune", 160.0, 140.0, "Dark Caster / Líder da Strega", true));
        user.put("Jin", new Usuarios("Jin Shirato", 19, "Masculino", 10, "Hermit", 120.0, 130.0, "Support Hacker / Tech", true));
        user.put("Chidori", new Usuarios("Chidori Yoshino", 17, "Feminino", 99, "Hanged Man", 130.0, 150.0, "Fire Caster / Emo Artista", true));

        // Inserção de usuários STREGA no banco de dados:
        usuariosDAO.insertUsuario(user.get("Takaya"));
        usuariosDAO.insertUsuario(user.get("Jin"));
        usuariosDAO.insertUsuario(user.get("Chidori"));


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

        personasDAOuser.insertPersona(user.get("Yukari").getPersonas());
        personasDAOuser.insertPersona(user.get("Mitsuru").getPersonas());
        personasDAOuser.insertPersona(user.get("Junpei").getPersonas());
        personasDAOuser.insertPersona(user.get("Akihiko").getPersonas());
        personasDAOuser.insertPersona(user.get("Fuuka").getPersonas());
        personasDAOuser.insertPersona(user.get("Aigis").getPersonas());
        personasDAOuser.insertPersona(user.get("Koromaru").getPersonas());
        personasDAOuser.insertPersona(user.get("Ken").getPersonas());
        personasDAOuser.insertPersona(user.get("Shinjiro").getPersonas());

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
        personasDAOuser.insertPersona(user.get("Takaya").getPersonas());
        personasDAOuser.insertPersona(user.get("Jin").getPersonas());
        personasDAOuser.insertPersona(user.get("Chidori").getPersonas());

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

        NPCDAO npcdao = new NPCDAO();
        npcdao.insertNPC(npcs.get("Bunkichi e Mitsuko"));
        npcdao.insertNPC(npcs.get("Kenji"));
        npcdao.insertNPC(npcs.get("Kazushi"));
        npcdao.insertNPC(npcs.get("Odagiri"));
        npcdao.insertNPC(npcs.get("Yuko"));
        npcdao.insertNPC(npcs.get("Chihiro"));
        npcdao.insertNPC(npcs.get("Maya"));
        npcdao.insertNPC(npcs.get("Suemitsu"));
        npcdao.insertNPC(npcs.get("Hiraga"));
        npcdao.insertNPC(npcs.get("Akinari"));
        npcdao.insertNPC(npcs.get("Mutatsu"));
        npcdao.insertNPC(npcs.get("Maiko"));
        npcdao.insertNPC(npcs.get("Bebé"));
        npcdao.insertNPC(npcs.get("Tanaka"));

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

        protagonista.personas.getFirst().addHabilidade(new Habilidades("Megidolaon", "Almighty", "Causa dano massivo a todos os inimigos", 6));
        protagonista.personas.getFirst().addHabilidade(new Habilidades("Mudo", "Dark", "Chance de causar morte instantânea a um inimigo", 3));
        protagonista.personas.getFirst().addHabilidade(new Habilidades("Die For Me!", "Dark", "Alta chance de causar morte instantânea a todos os inimigos", 7));

        protagonista.personas.get(1).addHabilidade(new Habilidades("Agidyne", "Fire", "Causa dano de fogo pesado a um inimigo", 5));
        protagonista.personas.get(1).addHabilidade(new Habilidades("Power Charge", "Support", "Aumenta o próximo dano físico causado", 0));
        protagonista.personas.get(1).addHabilidade(new Habilidades("Maragion", "Fire", "Causa dano de fogo médio a todos os inimigos", 4));

        protagonista.personas.get(2).addHabilidade(new Habilidades("Eiha", "Dark", "Causa dano sombrio leve a um inimigo", 2));
        protagonista.personas.get(2).addHabilidade(new Habilidades("Lunge", "Physical", "Causa dano físico leve a um inimigo", 3));
        protagonista.personas.get(2).addHabilidade(new Habilidades("Cleave", "Physical", "Causa dano físico leve a um inimigo", 3));

        protagonista.personas.get(3).addHabilidade(new Habilidades("Agi", "Fire", "Causa dano de fogo leve a um inimigo", 2));
        protagonista.personas.get(3).addHabilidade(new Habilidades("Rakunda", "Debuff", "Reduz a defesa de um inimigo", 0));
        protagonista.personas.get(3).addHabilidade(new Habilidades("Tarukaja", "Buff", "Aumenta o ataque de um aliado por 3 turnos", 0));

        protagonista.personas.get(4).addHabilidade(new Habilidades("Zio", "Electric", "Causa dano elétrico leve a um inimigo", 2));
        protagonista.personas.get(4).addHabilidade(new Habilidades("Dia", "Healing", "Restaura uma pequena quantidade de HP a um aliado", 0));
        protagonista.personas.get(4).addHabilidade(new Habilidades("Patra", "Healing", "Cura um aliado de status negativos", 0));

        protagonista.personas.get(5).addHabilidade(new Habilidades("Dream Needle", "Ailment", "Causa dano físico leve com chance de causar sono", 2));
        protagonista.personas.get(5).addHabilidade(new Habilidades("Life Drain", "Dark", "Drena uma pequena quantidade de HP de um inimigo", 3));
        protagonista.personas.get(5).addHabilidade(new Habilidades("Mudo", "Dark", "Chance de causar morte instantânea a um inimigo", 3));

        protagonista.personas.get(6).addHabilidade(new Habilidades("Tentarafoo", "Ailment", "Chance de causar confusão a todos os inimigos", 0));
        protagonista.personas.get(6).addHabilidade(new Habilidades("Evil Touch", "Ailment", "Chance de causar medo a um inimigo", 0));
        protagonista.personas.get(6).addHabilidade(new Habilidades("Spirit Drain", "Support", "Drena uma pequena quantidade de SP de um inimigo", 1));

        protagonista.personas.get(7).addHabilidade(new Habilidades("Bufu", "Ice", "Causa dano de gelo leve a um inimigo", 2));
        protagonista.personas.get(7).addHabilidade(new Habilidades("Media", "Healing", "Restaura uma pequena quantidade de HP a todos os aliados", 0));
        protagonista.personas.get(7).addHabilidade(new Habilidades("Rakukaja", "Buff", "Aumenta a defesa de um aliado por 3 turnos", 0));

        protagonista.personas.getLast().addHabilidade(new Habilidades("Maragi", "Fire", "Causa dano de fogo leve a todos os inimigos", 3));
        protagonista.personas.getLast().addHabilidade(new Habilidades("Dekunda", "Support", "Remove penalidades de status de todos os aliados", 0));
        protagonista.personas.getLast().addHabilidade(new Habilidades("Sukukaja", "Buff", "Aumenta a precisão e evasão de um aliado por 3 turnos", 0));

        HabilidadesDAO habilidadesdao = new HabilidadesDAO();

        habilidadesdao.insertHabilidade(protagonista.personas.getFirst().getHabilidades().get(0));
        habilidadesdao.insertHabilidade(protagonista.personas.getFirst().getHabilidades().get(1));
        habilidadesdao.insertHabilidade(protagonista.personas.getFirst().getHabilidades().get(2));

        habilidadesdao.insertHabilidade(protagonista.personas.get(1).getHabilidades().get(0));
        habilidadesdao.insertHabilidade(protagonista.personas.get(1).getHabilidades().get(1));
        habilidadesdao.insertHabilidade(protagonista.personas.get(1).getHabilidades().get(2));

        habilidadesdao.insertHabilidade(protagonista.personas.get(2).getHabilidades().get(0));
        habilidadesdao.insertHabilidade(protagonista.personas.get(2).getHabilidades().get(1));
        habilidadesdao.insertHabilidade(protagonista.personas.get(2).getHabilidades().get(2));

        habilidadesdao.insertHabilidade(protagonista.personas.get(3).getHabilidades().get(0));
        habilidadesdao.insertHabilidade(protagonista.personas.get(3).getHabilidades().get(1));
        habilidadesdao.insertHabilidade(protagonista.personas.get(3).getHabilidades().get(2));

        habilidadesdao.insertHabilidade(protagonista.personas.get(4).getHabilidades().get(0));
        habilidadesdao.insertHabilidade(protagonista.personas.get(4).getHabilidades().get(1));
        habilidadesdao.insertHabilidade(protagonista.personas.get(4).getHabilidades().get(2));

        habilidadesdao.insertHabilidade(protagonista.personas.get(5).getHabilidades().get(0));
        habilidadesdao.insertHabilidade(protagonista.personas.get(5).getHabilidades().get(1));
        habilidadesdao.insertHabilidade(protagonista.personas.get(5).getHabilidades().get(2));

        habilidadesdao.insertHabilidade(protagonista.personas.get(6).getHabilidades().get(0));
        habilidadesdao.insertHabilidade(protagonista.personas.get(6).getHabilidades().get(1));
        habilidadesdao.insertHabilidade(protagonista.personas.get(6).getHabilidades().get(2));

        habilidadesdao.insertHabilidade(protagonista.personas.get(7).getHabilidades().get(0));
        habilidadesdao.insertHabilidade(protagonista.personas.get(7).getHabilidades().get(1));
        habilidadesdao.insertHabilidade(protagonista.personas.get(7).getHabilidades().get(2));

        habilidadesdao.insertHabilidade(protagonista.personas.getLast().getHabilidades().get(0));
        habilidadesdao.insertHabilidade(protagonista.personas.getLast().getHabilidades().get(1));
        habilidadesdao.insertHabilidade(protagonista.personas.getLast().getHabilidades().get(2));


        // Habilidades dos usuários (SEES e Strega):
        // ---------------------------------------- Personas habilidadeds: ----------------------------------------

        user.get("Yukari").getPersonas().addHabilidade(new Habilidades("Agilao", "Fire", "Causa dano de fogo médio a um inimigo", 6));
        user.get("Yukari").getPersonas().addHabilidade(new Habilidades("Rebellion", "Support", "Aumenta a taxa de acerto crítico de um aliado por 3 turnos", 0));
        user.get("Yukari").getPersonas().addHabilidade(new Habilidades("Maragi", "Fire", "Causa dano de fogo leve a todos os inimigos", 4));
        user.get("Yukari").getPersonas().addHabilidade(new Habilidades("Fatal End", "Physical", "Causa dano físico pesado a um inimigo", 10));

        user.get("Junpei").getPersonas().addHabilidade(new Habilidades("Zio", "Electric", "Causa dano elétrico leve a um inimigo", 4));
        user.get("Junpei").getPersonas().addHabilidade(new Habilidades("Maziodyne", "Electric", "Causa dano elétrico pesado a todos os inimigos com chance de causar choque", 12));
        user.get("Junpei").getPersonas().addHabilidade(new Habilidades("Sonic Punch", "Physical", "Causa dano físico leve a um inimigo com chance de causar aflição", 6));
        user.get("Junpei").getPersonas().addHabilidade(new Habilidades("Fist Master", "Passive", "Dobra o dano causado por armas de punho", 0));

        user.get("Mitsuru").getPersonas().addHabilidade(new Habilidades("Bufula", "Ice", "Causa dano de gelo médio a um inimigo com chance de congelar", 6));
        user.get("Mitsuru").getPersonas().addHabilidade(new Habilidades("Tentarafoo", "Ailment", "Chance de causar confusão a todos os inimigos", 8));
        user.get("Mitsuru").getPersonas().addHabilidade(new Habilidades("Ice Boost", "Passive", "Aumenta o dano de habilidades de gelo em 25%", 0));
        user.get("Mitsuru").getPersonas().addHabilidade(new Habilidades("Mabufula", "Ice", "Causa dano de gelo médio a todos os inimigos com chance de congelar", 12));

        user.get("Akihiko").getPersonas().addHabilidade(new Habilidades("Kill Rush", "Physical", "Causa dano físico leve a um inimigo múltiplas vezes", 12));
        user.get("Akihiko").getPersonas().addHabilidade(new Habilidades("Tarukaja", "Support", "Aumenta o ataque de um aliado por 3 turnos", 0));
        user.get("Akihiko").getPersonas().addHabilidade(new Habilidades("Swift Strike", "Physical", "Causa dano físico leve a todos os inimigos múltiplas vezes", 16));
        user.get("Akihiko").getPersonas().addHabilidade(new Habilidades("Makarakarn", "Support", "Reflete o próximo ataque mágico recebido por um aliado", 24));

        user.get("Ken").getPersonas().addHabilidade(new Habilidades("Hama", "Light", "Chance de causar morte instantânea a um inimigo", 8));
        user.get("Ken").getPersonas().addHabilidade(new Habilidades("Zan-ei", "Light", "Causa dano de luz leve a um inimigo", 6));
        user.get("Ken").getPersonas().addHabilidade(new Habilidades("Media", "Healing", "Restaura uma pequena quantidade de HP a todos os aliados", 0));
        user.get("Ken").getPersonas().addHabilidade(new Habilidades("Recarm", "Healing", "Revive um aliado com metade do HP", 0));

        user.get("Koromaru").getPersonas().addHabilidade(new Habilidades("Agi", "Fire", "Causa dano de fogo leve a um inimigo", 4));
        user.get("Koromaru").getPersonas().addHabilidade(new Habilidades("Mudoon", "Dark", "Alta chance de causar morte instantânea a um inimigo", 12));
        user.get("Koromaru").getPersonas().addHabilidade(new Habilidades("Fire Boost", "Passive", "Aumenta o dano de habilidades de fogo em 25%", 0));
        user.get("Koromaru").getPersonas().addHabilidade(new Habilidades("Hell Fang", "Physical", "Causa dano físico médio a um inimigo", 8));

        user.get("Aigis").getPersonas().addHabilidade(new Habilidades("Fatal End", "Physical", "Causa dano físico pesado a um inimigo", 10));
        user.get("Aigis").getPersonas().addHabilidade(new Habilidades("Double Fangs", "Physical", "Causa dano físico leve a um inimigo duas vezes", 12));
        user.get("Aigis").getPersonas().addHabilidade(new Habilidades("Power Charge", "Support", "Aumenta o próximo dano físico causado por um aliado", 0));
        user.get("Aigis").getPersonas().addHabilidade(new Habilidades("Gigantic Fist", "Physical", "Causa dano físico muito pesado a um inimigo", 20));

        user.get("Shinjiro").getPersonas().addHabilidade(new Habilidades("Fatal End", "Physical", "Causa dano físico pesado a um inimigo", 10));
        user.get("Shinjiro").getPersonas().addHabilidade(new Habilidades("Kill Rush", "Physical", "Causa dano físico leve a um inimigo múltiplas vezes", 12));
        user.get("Shinjiro").getPersonas().addHabilidade(new Habilidades("Counterstrike", "Passive", "Chance de 15% de contra-atacar com dano físico médio", 0));

        user.get("Takaya").getPersonas().addHabilidade(new Habilidades("Mamudoon", "Dark", "Alta chance de causar morte instantânea a todos os inimigos", 16));
        user.get("Takaya").getPersonas().addHabilidade(new Habilidades("Hamaon", "Light", "Alta chance de causar morte instantânea a um inimigo", 14));
        user.get("Takaya").getPersonas().addHabilidade(new Habilidades("Mind Charge", "Support", "Aumenta o próximo dano mágico causado por um aliado", 0));

        user.get("Jin").getPersonas().addHabilidade(new Habilidades("Debilitate", "Support", "Reduz ataque, defesa e agilidade de um inimigo por 3 turnos", 0));
        user.get("Jin").getPersonas().addHabilidade(new Habilidades("Marakunda", "Support", "Reduz a defesa de todos os inimigos por 3 turnos", 0));
        user.get("Jin").getPersonas().addHabilidade(new Habilidades("Mabufula", "Ice", "Causa dano de gelo médio a todos os inimigos com chance de congelar", 12));

        user.get("Chidori").getPersonas().addHabilidade(new Habilidades("Maragion", "Fire", "Causa dano de fogo médio a todos os inimigos", 10));
        user.get("Chidori").getPersonas().addHabilidade(new Habilidades("Diarama", "Healing", "Restaura uma quantidade moderada de HP a um aliado", 0));
        user.get("Chidori").getPersonas().addHabilidade(new Habilidades("Fire Break", "Support", "Remove resistência ao fogo de todos os inimigos por 3 turnos", 0));

        HabilidadesDAO habilidadesdaouser = new HabilidadesDAO();

        habilidadesdaouser.insertHabilidade(user.get("Yukari").getPersonas().getHabilidades().getFirst());
        habilidadesdaouser.insertHabilidade(user.get("Yukari").getPersonas().getHabilidades().get(1));
        habilidadesdaouser.insertHabilidade(user.get("Yukari").getPersonas().getHabilidades().get(2));
        habilidadesdaouser.insertHabilidade(user.get("Yukari").getPersonas().getHabilidades().get(3));

        habilidadesdaouser.insertHabilidade(user.get("Junpei").getPersonas().getHabilidades().getFirst());
        habilidadesdaouser.insertHabilidade(user.get("Junpei").getPersonas().getHabilidades().get(1));
        habilidadesdaouser.insertHabilidade(user.get("Junpei").getPersonas().getHabilidades().get(2));
        habilidadesdaouser.insertHabilidade(user.get("Junpei").getPersonas().getHabilidades().get(3));

        habilidadesdaouser.insertHabilidade(user.get("Mitsuru").getPersonas().getHabilidades().getFirst());
        habilidadesdaouser.insertHabilidade(user.get("Mitsuru").getPersonas().getHabilidades().get(1));
        habilidadesdaouser.insertHabilidade(user.get("Mitsuru").getPersonas().getHabilidades().get(2));
        habilidadesdaouser.insertHabilidade(user.get("Mitsuru").getPersonas().getHabilidades().get(3));

        habilidadesdaouser.insertHabilidade(user.get("Akihiko").getPersonas().getHabilidades().getFirst());
        habilidadesdaouser.insertHabilidade(user.get("Akihiko").getPersonas().getHabilidades().get(1));
        habilidadesdaouser.insertHabilidade(user.get("Akihiko").getPersonas().getHabilidades().get(2));
        habilidadesdaouser.insertHabilidade(user.get("Akihiko").getPersonas().getHabilidades().get(3));

        habilidadesdaouser.insertHabilidade(user.get("Aigis").getPersonas().getHabilidades().getFirst());
        habilidadesdaouser.insertHabilidade(user.get("Aigis").getPersonas().getHabilidades().get(1));
        habilidadesdaouser.insertHabilidade(user.get("Aigis").getPersonas().getHabilidades().get(2));
        habilidadesdaouser.insertHabilidade(user.get("Aigis").getPersonas().getHabilidades().get(3));

        habilidadesdaouser.insertHabilidade(user.get("Koromaru").getPersonas().getHabilidades().getFirst());
        habilidadesdaouser.insertHabilidade(user.get("Koromaru").getPersonas().getHabilidades().get(1));
        habilidadesdaouser.insertHabilidade(user.get("Koromaru").getPersonas().getHabilidades().get(2));
        habilidadesdaouser.insertHabilidade(user.get("Koromaru").getPersonas().getHabilidades().get(3));

        habilidadesdaouser.insertHabilidade(user.get("Ken").getPersonas().getHabilidades().getFirst());
        habilidadesdaouser.insertHabilidade(user.get("Ken").getPersonas().getHabilidades().get(1));
        habilidadesdaouser.insertHabilidade(user.get("Ken").getPersonas().getHabilidades().get(2));
        habilidadesdaouser.insertHabilidade(user.get("Ken").getPersonas().getHabilidades().get(3));

        habilidadesdaouser.insertHabilidade(user.get("Shinjiro").getPersonas().getHabilidades().getFirst());
        habilidadesdaouser.insertHabilidade(user.get("Shinjiro").getPersonas().getHabilidades().get(1));
        habilidadesdaouser.insertHabilidade(user.get("Shinjiro").getPersonas().getHabilidades().get(2));

        habilidadesdaouser.insertHabilidade(user.get("Takaya").getPersonas().getHabilidades().getFirst());
        habilidadesdaouser.insertHabilidade(user.get("Takaya").getPersonas().getHabilidades().get(1));
        habilidadesdaouser.insertHabilidade(user.get("Takaya").getPersonas().getHabilidades().get(2));

        habilidadesdaouser.insertHabilidade(user.get("Jin").getPersonas().getHabilidades().getFirst());
        habilidadesdaouser.insertHabilidade(user.get("Jin").getPersonas().getHabilidades().get(1));
        habilidadesdaouser.insertHabilidade(user.get("Jin").getPersonas().getHabilidades().get(2));

        habilidadesdaouser.insertHabilidade(user.get("Chidori").getPersonas().getHabilidades().getFirst());
        habilidadesdaouser.insertHabilidade(user.get("Chidori").getPersonas().getHabilidades().get(1));
        habilidadesdaouser.insertHabilidade(user.get("Chidori").getPersonas().getHabilidades().get(2));

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
        item.put("Paradise Lost", new Arma("Paradise Lost", "Espada", 250.0, "Raro", 85.0));
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

        // -------------------------------------- Loja de Itens: -------------------------------------

        Map<Itens, Integer> estoque = new HashMap<>();
        LojadeItensDAO lojadeItensDAO = new LojadeItensDAO();

        int j = 0;
        for (Itens i : item.values()) {
            estoque.put(i, 1); // Integer.valueOf(1) caso dê erro de versão
            lojadeItensDAO.insertItem(i,j, 1);
            j++;
        }
        LojadeItens loja = new LojadeItens("Untouchable", estoque);


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

        /*

        cidadesDAO.adicionarLocal("Tatsumi Port Island","Dormitório");
        cidadesDAO.adicionarLocal("Tatsumi Port Island","Escola");
        cidadesDAO.adicionarLocal("Tatsumi Port Island","Rua");
        cidadesDAO.adicionarLocal("Tatsumi Port Island","Shopping");
        cidadesDAO.adicionarLocal("Tatsumi Port Island","Loja de Itens");
        cidadesDAO.adicionarPersonagemAoLocal("Dormitório", protagonista);

        /*
                            trecho do Codigo duplicado removido

        cidadesDAO.adicionarPersonagemAoLocal("Dormitório", user.get("Yukari"));
        cidadesDAO.adicionarPersonagemAoLocal("Rua", user.get("Junpei"));
        cidadesDAO.adicionarPersonagemAoLocal("Escola", user.get("Mitsuru"));
        cidadesDAO.adicionarPersonagemAoLocal("Rua", user.get("Takaya"));
        cidadesDAO.adicionarPersonagemAoLocal("Rua", user.get("Chidori"));
        cidadesDAO.adicionarPersonagemAoLocal("Shopping", user.get("Akihiko"));
        cidadesDAO.adicionarPersonagemAoLocal("Shopping", user.get("Aigis"));
        cidadesDAO.adicionarPersonagemAoLocal("Loja de Itens", user.get("Fuuka"));
        cidadesDAO.buscarTodasCidades();
        cidadesDAO.deletarCidade("Tatsumi Port Island");

        */

        String localAtual = "Dormitório";

        int opcao;
        int sair_jogo;
        Scanner sc = new Scanner(System.in);
        while(true) {
            try {
                sair_jogo = mostrar_menu_principal();
                AudioManager.getInstance().playMenuSelectSFX();
            } catch (InputMismatchException e) {
                System.out.println(ANSI_RED + "Erro: insira apenas números!" + ANSI_RESET);
                sc.nextLine(); // Limpa o buffer de entrada
                continue; // Retornar ao início do loop
            }

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

                                    while (true) {
                                        System.out.println(ANSI_GRAY + "\nDeseja ver detalhes de alguma persona? (S/N)");
                                        String resposta = sc.nextLine();
                                        System.out.println(ANSI_RESET);

                                        if (resposta.equalsIgnoreCase("Sim") || resposta.equalsIgnoreCase("S")) {
                                            System.out.println(ANSI_GRAY + "Digite o número da persona: " + ANSI_RESET);
                                            System.out.println(ANSI_BLUE + "1. Alice");
                                            System.out.println("2. Eligor");
                                            System.out.println("3. Arsène");
                                            System.out.println("4. Jack-o'-Lantern");
                                            System.out.println("5. Pixie");
                                            System.out.println("6. Incubus");
                                            System.out.println("7. Succubus");
                                            System.out.println("8. Silky");
                                            System.out.println("9. Orobas" + ANSI_RESET);

                                            try {
                                                int numPersona = sc.nextInt();
                                                sc.nextLine(); // Limpa buffer

                                                if (numPersona > 0 && numPersona <= protagonista.personas.size()) {
                                                    protagonista.personas.get(numPersona - 1).mostrarStatusPersona();
                                                } else {
                                                    System.out.println(ANSI_RED + "Número inválido!" + ANSI_RESET);
                                                }
                                            } catch (InputMismatchException e) {
                                                System.out.println(ANSI_RED + "Erro: Digite apenas números!" + ANSI_RESET);
                                                sc.nextLine(); // Limpa buffer
                                            }
                                        } else if (resposta.equalsIgnoreCase("N") || resposta.equalsIgnoreCase("NAO")) {
                                            break;
                                        } else {
                                            System.out.println(ANSI_RED + "Entrada Incorreta!" + ANSI_RESET);
                                        }
                                    }

                                    while (true) {
                                        System.out.println(ANSI_GRAY + "Deseja evoluir uma Persona? (S/N)");
                                        String resposta2 = sc.nextLine();

                                        if (resposta2.equalsIgnoreCase("Sim") || resposta2.equalsIgnoreCase("S")) {
                                            System.out.println("Qual o nome da Persona que deseja evoluir?");
                                            String nome = sc.nextLine();
                                            System.out.println(ANSI_RESET);
                                            boolean encontrada = false;

                                            for (Personas p : protagonista.personas) {
                                                if (p.getNome().equalsIgnoreCase(nome)) {
                                                    protagonista.evoluirPersona(p);
                                                    System.out.println(ANSI_GREEN + p.getNome() + " evoluiu para o nivel " + p.getNivel() + "!" + ANSI_RESET);
                                                    encontrada = true;
                                                    break;
                                                }
                                            }
                                            try {
                                                if (!encontrada) {
                                                    throw new IllegalArgumentException(ANSI_RED + "Persona não encontrada!" + ANSI_RESET);
                                                }
                                            } catch (IllegalArgumentException e) {
                                                System.out.println(e.getMessage());
                                            }
                                        } else if (resposta2.equalsIgnoreCase("N") || resposta2.equalsIgnoreCase("NAO")) {
                                            break;
                                        } else {
                                            System.out.println(ANSI_RED + "Entrada Incorreta!" + ANSI_RESET);
                                        }
                                    }
                                }
                                case 2 -> {
                                    System.out.println(ANSI_CYAN + "\n----- Usuários -----" + ANSI_RESET);
                                    System.out.println();
                                    int count = 1;
                                    for (String nomeUsuario : user.keySet()) {
                                        System.out.println(ANSI_BLUE + count + ". " + nomeUsuario);
                                        count++;
                                    }
                                    System.out.print(ANSI_RESET);
                                    System.out.println(ANSI_GRAY + "\nDigite o nome do usuário que deseja consultar: ");
                                    String nomeUsuario = sc.nextLine();
                                    System.out.println(ANSI_RESET);


                                    try {
                                        if (user.containsKey(nomeUsuario)) {
                                            user.get(nomeUsuario).mostraInfoPersonagem();
                                            if (user.get(nomeUsuario).getPersonas() != null) {
                                                user.get(nomeUsuario).getPersonas().mostrarStatusPersona();
                                            } else {
                                                System.out.println(ANSI_RED + "Este usuário não possui personas." + ANSI_RESET);
                                            }
                                        } else {
                                            throw new IllegalArgumentException(ANSI_RED + "Nome incorreto ou número inserido!" + ANSI_RESET);
                                        }
                                    }catch (IllegalArgumentException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                case 3 -> {
                                    while (true) {
                                        System.out.println(ANSI_CYAN + "\n----- NPC -----" + ANSI_RESET);

                                        int count = 1;
                                        for (String nomeNPC : npcs.keySet()) {
                                            System.out.println(ANSI_BLUE + count + ". " + nomeNPC);
                                            count++;
                                        }

                                        System.out.println(ANSI_RESET);
                                        System.out.println(ANSI_GRAY + "Digite o nome do NPC que deseja consultar: ");
                                        String nomeNPC = sc.nextLine();
                                        System.out.println(ANSI_RESET);

                                        try {
                                            if (npcs.containsKey(nomeNPC)) {
                                                npcs.get(nomeNPC).mostraInfoPersonagem();
                                            } else {
                                                throw new IllegalArgumentException(ANSI_RED + "Nome incorreto ou número inserido!" + ANSI_RESET);
                                            }
                                        } catch (IllegalArgumentException e) {
                                            System.out.println(e.getMessage());
                                        }

                                        System.out.print(ANSI_GRAY + "Deseja pesquisar outro NPC? (S/N) ");
                                        String resposta = sc.nextLine();
                                        System.out.println(ANSI_RESET);

                                        if (resposta.equalsIgnoreCase("N") || resposta.equalsIgnoreCase("NAO")) {
                                            break;
                                        } else if (!resposta.equalsIgnoreCase("Sim") && !resposta.equalsIgnoreCase("S")) {
                                            System.out.println(ANSI_RED + "Entrada Incorreta!" + ANSI_RESET);
                                        }

                                        // Se a resposta for "Sim" ou "S", o loop continua e mostra a tabela novamente
                                    }
                                }

                                case 4 -> {
                                    System.out.println(ANSI_CYAN + "\n----- Dados da Loja de Itens -----" + ANSI_RESET);
                                    loja.mostraInfoLojadeItens();

                                    while (true) {
                                        System.out.println(ANSI_GRAY + "\nDeseja comprar algum item? (S/N)");
                                        String resposta = sc.nextLine();
                                        System.out.println(ANSI_RESET);

                                        if (resposta.equalsIgnoreCase("S") || resposta.equalsIgnoreCase("Sim")) {
                                            loja.mostrarItens();
                                            System.out.println(ANSI_GRAY + "Digite o nome do item: ");
                                            String nomeItem = sc.nextLine();
                                            System.out.println(ANSI_RESET);

                                            if (item.containsKey(nomeItem)) {
                                                loja.venderItem(protagonista, item.get(nomeItem));
                                                System.out.print(ANSI_GREEN + "Item comprado com sucesso!" + ANSI_RESET);
                                            } else {
                                                System.out.println(ANSI_RED + "Item não encontrado!" + ANSI_RESET);
                                            }

                                        } else if (resposta.equalsIgnoreCase("N") || resposta.equalsIgnoreCase("Nao")) {
                                            break;

                                        } else {
                                            System.out.println(ANSI_RED + "Entrada incorreta!" + ANSI_RESET);
                                        }
                                    }
                                }

                                case 5 -> {
                                    System.out.println(ANSI_CYAN + "\n----- Dar/Equipar Itens -----" + ANSI_RESET);

                                    System.out.println(ANSI_BLUE + "Itens disponíveis no inventário do protagonista:" + ANSI_RESET);
                                    protagonista.inventario.mostrarInventarioPersonagem();

                                    System.out.println(ANSI_BLUE + "\nUsuários disponíveis:");
                                    int count = 1;
                                    for (String nomeUsuario : user.keySet()) {
                                        System.out.println(count + ". " + nomeUsuario);
                                        count++;
                                    }
                                    System.out.println(ANSI_RESET);

                                    System.out.println(ANSI_GRAY + "Deseja equipar um item no protagonista ou dá-lo a um aliado? (S/N)");
                                    String resposta = sc.nextLine();
                                    System.out.println(ANSI_RESET);
                                    if (resposta.equalsIgnoreCase("S") || resposta.equalsIgnoreCase("Sim")) {
                                        System.out.println(ANSI_BLUE + "1. Protagonista");
                                        System.out.println("2. Aliado");
                                        System.out.println("0. Sair" + ANSI_RESET);

                                        try {
                                            opcao = sc.nextInt();
                                            sc.nextLine(); // Limpa buffer

                                            switch (opcao) {
                                                case 1 -> {
                                                    System.out.print(ANSI_GRAY +"Digite o nome do item a ser dado/equipado: ");
                                                    String nomeItem = sc.nextLine();
                                                    System.out.println(ANSI_RESET);

                                                    if (item.containsKey(nomeItem)) {
                                                        if(item.get(nomeItem) instanceof Arma arma) {
                                                            arma.equiparItem(protagonista);
                                                        }
                                                        else if (item.get(nomeItem) instanceof Equipamento equipamento) {
                                                            equipamento.equiparItem(protagonista);
                                                        }
                                                        else{
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
                                                        if(item.get(nomeItem) instanceof Arma arma) {
                                                            arma.equiparItem(user.get(nomeAliado));
                                                        }
                                                        else if (item.get(nomeItem) instanceof Equipamento equipamento) {
                                                            equipamento.equiparItem(user.get(nomeAliado));
                                                        }
                                                        else{
                                                            System.out.println("Item não equipável");
                                                        }
                                                        System.out.println(ANSI_GREEN + "Item equipado com sucesso pelo usuário " + nomeAliado + "!" + ANSI_RESET);
                                                    } else {
                                                        System.out.println(ANSI_RED + "Usuário ou item não encontrado!" + ANSI_RESET);
                                                    }
                                                }
                                                case 0 -> System.out.println(ANSI_YELLOW + "Saindo..." + ANSI_RESET);
                                                default -> System.out.println(ANSI_RED + "Opção inválida!" + ANSI_RESET);
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
                                    protagonista.inventario.mostrarInventarioPersonagem();
                                }
                                case 8 -> {
                                    System.out.println(ANSI_CYAN + "\n----- Inventário de Usuário -----" + ANSI_RESET);

                                    while (true) {
                                        int count = 1;
                                        for (String nomeUsuario : user.keySet()) {
                                            System.out.println(ANSI_BLUE + count + ". " + nomeUsuario + ANSI_RESET);
                                            count++;
                                        }

                                        System.out.println(ANSI_GRAY + "\nDigite o nome do usuário que deseja ver o inventário (ou digite 'sair' para voltar): ");
                                        String nomeUsuario = sc.nextLine();
                                        System.out.println(ANSI_RESET);

                                        if (nomeUsuario.equalsIgnoreCase("sair")) {
                                            break;
                                        }
                                        if (user.containsKey(nomeUsuario)) {
                                            user.get(nomeUsuario).inventario.mostrarInventarioPersonagem();
                                            break;
                                        } else {
                                            System.out.println(ANSI_RED + "Usuário não encontrado! Tente novamente." + ANSI_RESET);
                                        }
                                    }
                                }

                                default -> System.out.println(ANSI_RED + "Opção inválida!" + ANSI_RESET);
                            }
                        }
                        catch (InvalidMenuInputException e) {
                            System.out.println(ANSI_RED + "Erro: " + e.getMessage() + ANSI_RESET);
                        }
                    }
                }
                case 2 -> {
                    while (true) {
                        AudioManager.getInstance().setGameStateMusic(AudioManager.GameState.CITY);
                        try {
                            opcao = mostrarMenuCidade(localAtual);
                            if (opcao == 0){
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
                                                    int count = 1;
                                                    System.out.println(ANSI_PURPLE + "\nAliados disponíveis:" + ANSI_RESET);
                                                    for (String nomeUsuario : user.keySet()) {
                                                        if (!user.get(nomeUsuario).isVilao()) {
                                                            System.out.println(ANSI_BLUE + count + ". " + nomeUsuario + ANSI_RESET);
                                                            count++;
                                                        }
                                                    }
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
                                                    int count = 1;
                                                    System.out.println(ANSI_PURPLE + "\nNPCs disponíveis:" + ANSI_RESET);
                                                    for (String nomeNPC : npcs.keySet()) {
                                                        System.out.println(ANSI_BLUE + count + ". " + nomeNPC + ANSI_RESET);
                                                        count++;
                                                    }
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
                                    timeProtagonistas.add(user.get("Mitsuru"));
                                    timeProtagonistas.add(user.get("Yukari"));
                                    timeProtagonistas.add(user.get("Aigis"));

                                    if (!viloes.isEmpty()) {
                                        iniciarCombate(timeProtagonistas, viloes);
                                    } else {
                                        System.out.println(ANSI_RED + "Não há vilões na área para combater" + ANSI_RESET);
                                    }
                                }
                                case 5 -> {
                                    System.out.println(ANSI_CYAN + "Loja de Itens" + ANSI_RESET);
                                    loja.mostraInfoLojadeItens();

                                    loja.mostrarItens(); // Mostra uma vez só

                                    while (true) {
                                        System.out.println(ANSI_CYAN + "Digite o nome do item: ");
                                        String nomeItem = sc.nextLine();
                                        System.out.println(ANSI_RESET);

                                        if (item.containsKey(nomeItem)) {
                                            loja.venderItem(protagonista, item.get(nomeItem));
                                        } else {
                                            System.out.println(ANSI_RED + "Item não encontrado!" + ANSI_RESET);
                                        }

                                        System.out.println(ANSI_GRAY + "Deseja comprar um novo item? (S/N)");
                                        String entrada = sc.nextLine();
                                        System.out.println(ANSI_RESET);

                                        if (entrada.equalsIgnoreCase("N") || entrada.equalsIgnoreCase("NAO")) {
                                            break;
                                        } else if (!entrada.equalsIgnoreCase("S") && !entrada.equalsIgnoreCase("SIM")) {
                                            System.out.println(ANSI_RED + "Entrada incorreta!" + ANSI_RESET);
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

                case 3 ->{
                    Scanner sqlScanner = new Scanner(System.in);

                    while (true) {
                        AudioManager.getInstance().setGameStateMusic(AudioManager.GameState.SQL);
                        try{
                            opcao = mostrarMenuSQL();
                            if(opcao == 0) {
                                AudioManager.getInstance().stopMusic();
                                AudioManager.getInstance().playMenuBackSFX();
                                break;
                            }
                            switch (opcao) {
                                case 1->{
                                    // Inserir Protagonista
                                    System.out.println("\n=== INSERIR PROTAGONISTA ===");
                                    System.out.print("Nome: ");
                                    String nome = sqlScanner.nextLine();
                                    int idade, nivel, idAtivador;
                                    double hp, sp, saldo;

                                    // Validação da Idade
                                    while(true) {
                                        try {
                                            System.out.print("Idade: ");
                                            idade = sqlScanner.nextInt();
                                            if(idade < 0 || idade > 100) {
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
                                    while(true) {
                                        try {
                                            System.out.print("Nível: ");
                                            nivel = sqlScanner.nextInt();
                                            if(nivel < 1) {
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
                                    while(true) {
                                        try {
                                            System.out.print("HP: ");
                                            hp = sqlScanner.nextDouble();
                                            if(hp < 0) {
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
                                    while(true) {
                                        try {
                                            System.out.print("SP: ");
                                            sp = sqlScanner.nextDouble();
                                            if(sp < 0) {
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
                                    while(true) {
                                        try {
                                            System.out.print("Saldo: ");
                                            saldo = sqlScanner.nextDouble();
                                            if(saldo < 0) {
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
                                    while(true) {
                                        try {
                                            System.out.print("IdAtivador: ");
                                            idAtivador = sqlScanner.nextInt();
                                            if(idAtivador < 0) {
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

                                case 2->{
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
                                    }catch(InputMismatchException e){
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
                                    while(true) {
                                        try {
                                            System.out.print("Idade: ");
                                            idadeNPC = sqlScanner.nextInt();
                                            if(idadeNPC < 0 || idadeNPC > 100) {
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
                                    while(true) {
                                        try {
                                            System.out.print("Idade: ");
                                            idadeUsuario = sqlScanner.nextInt();
                                            if(idadeUsuario < 0 || idadeUsuario > 100) {
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
                                    while(true) {
                                        try {
                                            System.out.print("Nível: ");
                                            nivelUsuario = sqlScanner.nextInt();
                                            if(nivelUsuario < 1) {
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
                                    while(true) {
                                        try {
                                            System.out.print("HP: ");
                                            hpUsuario = sqlScanner.nextDouble();
                                            if(hpUsuario < 0) {
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
                                    while(true) {
                                        try {
                                            System.out.print("SP: ");
                                            spUsuario = sqlScanner.nextDouble();
                                            if(spUsuario < 0) {
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
                                    while(true) {
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

                                    System.out.print("ATENÇÃO: Esta ação irá deletar permanentemente o usuário '" + nomeParaDeletar + "'. Confirma? (s/N): ");
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
                                    List<Usuarios> usuarios = usuariosDAO.selectUsuarios();

                                    if (usuarios.isEmpty()) {
                                        System.out.println("Nenhum usuário encontrado.");
                                    } else {
                                        System.out.println("Usuários cadastrados:");
                                        System.out.println("================================================");
                                        for (Usuarios u : usuarios) {
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
                        }catch(InvalidMenuInputException e){
                            System.out.println(ANSI_RED + "Erro: " + e.getMessage() + ANSI_RESET);
                        }
                    }
                }
                default -> System.out.println(ANSI_RED + "Número inválido ou caractere inserido!" + ANSI_RESET);
            }
        }
    }
}
