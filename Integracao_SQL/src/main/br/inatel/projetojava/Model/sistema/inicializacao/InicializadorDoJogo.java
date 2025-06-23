package main.br.inatel.projetojava.Model.sistema.inicializacao;

import main.br.inatel.projetojava.DAO.*;
import main.br.inatel.projetojava.Model.itens.auxiliares.Consumiveis;
import main.br.inatel.projetojava.Model.itens.equipaveis.Arma;
import main.br.inatel.projetojava.Model.itens.equipaveis.Equipamento;
import main.br.inatel.projetojava.Model.itens.geral.Itens;
import main.br.inatel.projetojava.Model.itens.lojas.FarmaciaAohige;
import main.br.inatel.projetojava.Model.itens.lojas.LojadeArmas;
import main.br.inatel.projetojava.Model.personagens.NPC;
import main.br.inatel.projetojava.Model.personagens.abstratos.UsuarioPersona;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Protagonista;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Usuarios;
import main.br.inatel.projetojava.Model.personas.Habilidades;
import main.br.inatel.projetojava.Model.personas.TiposPersona;
import main.br.inatel.projetojava.Model.personas.seres.Personas;
import main.br.inatel.projetojava.Model.personas.seres.Shadow;
import main.br.inatel.projetojava.Model.relacional.ProtagonistaHasPersona;
import main.br.inatel.projetojava.Model.sistema.Cidade;

import java.util.*;

public class InicializadorDoJogo {

    // Constantes para manutenção
    private static final String[] USUARIOS_SEES = {
            "Yukari", "Mitsuru", "Junpei", "Akihiko", "Fuuka",
            "Aigis", "Koromaru", "Ken", "Shinjiro"
    };

    private static final String[] USUARIOS_STREGA = {
            "Takaya", "Jin", "Chidori"
    };

    private static final String[] NPC_NAMES = {
            "Bunkichi e Mitsuko", "Kenji", "Kazushi", "Odagiri", "Yuko",
            "Chihiro", "Maya", "Suemitsu", "Hiraga", "Akinari",
            "Mutatsu", "Maiko", "Bebé", "Tanaka"
    };

    public static DadosIniciais carregarTudo() {
        Random random = new Random();
        try {
            ProtagonistaDAO protagonistaDAO = new ProtagonistaDAO();
            Protagonista protagonista = new Protagonista(
                    "Makoto Yuki", 17, "Masculino", 20, "The Fool",
                    100.00, 50, 10000.00, 4
            );
            protagonistaDAO.insertProtagonistaSemDuplicata(protagonista);

            Map<String, Usuarios> usuarios = new HashMap<>();
            Map<String, NPC> npcs = new HashMap<>();
            List<Shadow> shadows = new ArrayList<>();
            Map<String, Itens> itens = new HashMap<>();

            popularUsuarios(usuarios);
            popularNPCs(npcs);
            popularShadows(shadows);
            popularItens(itens);
            popularPersonas(protagonista, usuarios);

            protagonista.setPersona_atual(protagonista.getPersonas().getFirst());

            adicionarHabilidadesPersonasUsuario(usuarios, random);
            adicionarHabilidadesPersonasProtagonista(protagonista, random);

            // -------------------------------------- Loja de Armas (APENAS Armas e Equipamentos): -------------------------------------
            Map<Itens, Integer> estoque = new HashMap<>();
            LojadeItensDAO lojadeItensDAO = new LojadeItensDAO();

            int j = 0;
            // Adiciona APENAS Equipamentos e Armas ao estoque
            for (Itens i : itens.values()) {
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
            for (Itens i : itens.values()) {
                if (i instanceof Consumiveis) {
                    armazem.put(i, 1);
                    lojadeItensDAO.insertItem(i, j, 1);
                    j++;
                }
            }

            FarmaciaAohige farmacia = new FarmaciaAohige("Farmácia Aohige", armazem);

            Cidade tatsumiPort = new Cidade("Tatsumi Port Island");

            // Adicionando aos locais
            tatsumiPort.adicionarPersonagemAoLocal("Dormitório", usuarios.get("Yukari"));
            tatsumiPort.adicionarPersonagemAoLocal("Dormitório", usuarios.get("Junpei"));
            tatsumiPort.adicionarPersonagemAoLocal("Escola", usuarios.get("Mitsuru"));
            tatsumiPort.adicionarPersonagemAoLocal("Rua", usuarios.get("Takaya"));
            tatsumiPort.adicionarPersonagemAoLocal("Rua", usuarios.get("Chidori"));
            tatsumiPort.adicionarPersonagemAoLocal("Shopping", usuarios.get("Akihiko"));
            tatsumiPort.adicionarPersonagemAoLocal("Shopping", usuarios.get("Aigis"));
            tatsumiPort.adicionarPersonagemAoLocal("Loja de Itens", usuarios.get("Fuuka"));

            return new DadosIniciais(protagonista, usuarios, npcs, shadows, itens, lojaArmas, farmacia, tatsumiPort);

        } catch (Exception e) {
            System.err.println("Erro durante inicialização do jogo: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha na inicialização dos dados!", e);
        }
    }

    // ---------------------------------------- NPCs: ----------------------------------------

    public static void popularNPCs(Map<String, NPC> npcs) {


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
        npcs.put("Pharos", new NPC("Pharos", 8, "indefinido", "Manifestação da Morte", "Death"));
        npcs.put("Hayase", new NPC("Hayase", 17, "Masculino", "Atleta competitivo", "Star"));

        // Inserção no banco de dados:
        NPCDAO npcDAO = new NPCDAO();
        for (String npcName : NPC_NAMES) {
            if (npcs.containsKey(npcName)) {
                npcDAO.insertNPC(npcs.get(npcName));
            }
        }
    }

    // ---------------------------------------- Personas: ---------------------------------------------------

    public static void popularPersonas(UsuarioPersona usuario, Map<String, Usuarios> usuarios) {
        Random random = new Random();
        PersonasDAO personasDAO = new PersonasDAO();

        if (usuario instanceof Protagonista protagonista) {
            popularPersonasProtagonista(protagonista, personasDAO, random);
        }

        if (usuarios != null && !usuarios.isEmpty()) {
            popularPersonasUsuarios(usuarios, personasDAO, random);
        }
    }

    // ---------------------------------------- Personas do Makoto: --------------------------------

    private static void popularPersonasProtagonista(Protagonista protagonista, PersonasDAO personasDAO, Random random) {
        ProtagonistaDAO protagonistaDAO = new ProtagonistaDAO();
        int idProtagonista = protagonistaDAO.insertProtagonista(protagonista);

        List<Personas> personasProtagonista = Arrays.asList(
                new Personas("Alice", 20, "Death", List.of(TiposPersona.getTipoProtagonistaPorIndice(0)), "Bless", "Dark", random.nextInt(50) + 30),
                new Personas("Eligor", 25, "Tower", List.of(TiposPersona.getTipoProtagonistaPorIndice(1)), "Ice", "Fire", random.nextInt(50) + 30),
                new Personas("Arsène", 1, "Fool", List.of(TiposPersona.getTipoProtagonistaPorIndice(2)), "Ice", "Dark", random.nextInt(50) + 30),
                new Personas("Jack-o'-Lantern", 2, "Magician", List.of(TiposPersona.getTipoProtagonistaPorIndice(3)), "Gun", "Fire", random.nextInt(50) + 30),
                new Personas("Pixie", 3, "Lovers", List.of(TiposPersona.getTipoProtagonistaPorIndice(4)), "Gun", "Electricity", random.nextInt(50) + 30),
                new Personas("Incubus", 5, "Devil", List.of(TiposPersona.getTipoProtagonistaPorIndice(5)), "Bless", "Dark", random.nextInt(50) + 30),
                new Personas("Succubus", 8, "Moon", List.of(TiposPersona.getTipoProtagonistaPorIndice(6)), "Bless", "Dark", random.nextInt(50) + 30),
                new Personas("Silky", 4, "Priestess", List.of(TiposPersona.getTipoProtagonistaPorIndice(7)), "Fire", "Ice", random.nextInt(50) + 30),
                new Personas("Orobas", 17, "Hierophant", List.of(TiposPersona.getTipoProtagonistaPorIndice(8)), "Ice", "Fire", random.nextInt(50) + 30),
                new Personas("Bicorn", 10, "Hermit", List.of(TiposPersona.getTipoProtagonistaPorIndice(9)), "Light", "Physical", random.nextInt(50) + 30)
        );

        protagonista.getPersonas().addAll(personasProtagonista); // Adicionar todas as personas de uma vez

        ProtagonistaHasPersonaDAO protagonistaHasPersonaDAO = new ProtagonistaHasPersonaDAO();
        for (Personas persona : protagonista.getPersonas()) {
            personasDAO.insertPersona(persona);
            ProtagonistaHasPersona relacao = new ProtagonistaHasPersona(idProtagonista, persona.getIdPersona());
            protagonistaHasPersonaDAO.insertProtagonistaHasPersona(relacao);
        }
    }

    // ------------------------------------ Personas SEES: --------------------------------------------

    private static void popularPersonasUsuarios(Map<String, Usuarios> usuarios, PersonasDAO personasDAO, Random random) {
        UsuarioHasPersonaDAO usuarioHasPersonaDAO = new UsuarioHasPersonaDAO();

        addPersonaUser(usuarios, "Yukari", new Personas("Isis", 45, "Lovers", List.of(TiposPersona.getTipoUsuarioPorIndice(0)), "Electricity", "Wind", random.nextInt(50) + 30, 0), personasDAO, usuarioHasPersonaDAO);
        addPersonaUser(usuarios, "Mitsuru", new Personas("Artemisia", 48, "Empress", List.of(TiposPersona.getTipoUsuarioPorIndice(1)), "Fire", "Ice", random.nextInt(50) + 30, 1), personasDAO, usuarioHasPersonaDAO);
        addPersonaUser(usuarios, "Junpei", new Personas("Trismegistus", 43, "Magician", List.of(TiposPersona.getTipoUsuarioPorIndice(2)), "Wind", "Fire", random.nextInt(50) + 30, 2), personasDAO, usuarioHasPersonaDAO);
        addPersonaUser(usuarios, "Akihiko", new Personas("Caesar", 47, "Emperor", List.of(TiposPersona.getTipoUsuarioPorIndice(3)), "Pierce", "Electricity", random.nextInt(50) + 30, 3), personasDAO, usuarioHasPersonaDAO);
        addPersonaUser(usuarios, "Fuuka", new Personas("Juno", 40, "Priestess", List.of(TiposPersona.getTipoUsuarioPorIndice(4)), "Physical", "Support", 0, 4), personasDAO, usuarioHasPersonaDAO);
        addPersonaUser(usuarios, "Aigis", new Personas("Athena", 50, "Chariot", List.of(TiposPersona.getTipoUsuarioPorIndice(5)), "Electricity", "Strike", random.nextInt(50) + 30, 5), personasDAO, usuarioHasPersonaDAO);
        addPersonaUser(usuarios, "Koromaru", new Personas("Cerberus", 42, "Strength", List.of(TiposPersona.getTipoUsuarioPorIndice(6)), "Ice", "Dark", random.nextInt(50) + 30, 6), personasDAO, usuarioHasPersonaDAO);
        addPersonaUser(usuarios, "Ken", new Personas("Kala-Nemi", 41, "Justice", List.of(TiposPersona.getTipoUsuarioPorIndice(7)), "Darkness", "Light", random.nextInt(50) + 30, 7), personasDAO, usuarioHasPersonaDAO);
        addPersonaUser(usuarios, "Shinjiro", new Personas("Castor", 46, "Hierophant", List.of(TiposPersona.getTipoUsuarioPorIndice(8)), "Light", "Physical", random.nextInt(50) + 30, 8), personasDAO, usuarioHasPersonaDAO);

        // ------------------------------------ Personas STREGA: --------------------------------------------
        addPersonaUser(usuarios, "Takaya", new Personas("Hypnos", 52, "Death", List.of(TiposPersona.getTipoUsuarioPorIndice(9)), "Light", "Dark", random.nextInt(50) + 30, 9), personasDAO, usuarioHasPersonaDAO);
        addPersonaUser(usuarios, "Jin", new Personas("Moros", 50, "Hermit", List.of(TiposPersona.getTipoUsuarioPorIndice(10)), "Bless", "Almighty", random.nextInt(50) + 30, 10), personasDAO, usuarioHasPersonaDAO);
        addPersonaUser(usuarios, "Chidori", new Personas("Medea", 44, "Magician", List.of(TiposPersona.getTipoUsuarioPorIndice(11)), "Ice", "Fire", random.nextInt(50) + 30, 11), personasDAO, usuarioHasPersonaDAO);
    }

    // Adicionar as personas dos usuários:

    private static void addPersonaUser(Map<String, Usuarios> usuarios, String userName, Personas persona, PersonasDAO personasDAO, UsuarioHasPersonaDAO usuarioHasPersonaDAO) {
        if (usuarios.containsKey(userName)) {
            Usuarios user = usuarios.get(userName);
            user.addPersona(persona);
            personasDAO.insertPersona(persona);
            usuarioHasPersonaDAO.insertUsuarioHasPersona(user.getId(), persona.getIdPersona());
        }
    }

    // ------------------------------------ Usuários: --------------------------------------------

    public static void popularUsuarios(Map<String, Usuarios> usuarios) {
        UsuariosDAO usuariosDAO = new UsuariosDAO();

        // SEES Membros
        usuarios.put("Yukari", new Usuarios("Yukari", 16, "Feminino", 10, "Lovers", 100.0, 80.0, "Healer", false));
        usuarios.put("Mitsuru", new Usuarios("Mitsuru", 18, "Feminino", 20, "Empress", 120.0, 100.0, "Ice Caster", false));
        usuarios.put("Junpei", new Usuarios("Junpei Iori", 17, "Masculino", 30, "The Fool", 150.0, 70.0, "Slash Attacker", false));
        usuarios.put("Akihiko", new Usuarios("Akihiko Sanada", 18, "Masculino", 40, "The Fool", 160.0, 85.0, "Electric Striker / Boxer", false));
        usuarios.put("Fuuka", new Usuarios("Fuuka Yamagishi", 16, "Feminino", 45, "Priestess", 90.0, 120.0, "Support / Navigator", false));
        usuarios.put("Aigis", new Usuarios("Aigis", 17, "Feminino", 25, "Aeon", 180.0, 90.0, "Gunner / Tank", false));
        usuarios.put("Koromaru", new Usuarios("Koromaru", 10, "Masculino", 33, "The Fool", 110.0, 95.0, "Dark/Fire User", false));
        usuarios.put("Ken", new Usuarios("Ken Amada", 10, "Masculino", 60, "The Fool", 100.0, 110.0, "Light/Lance User", false));
        usuarios.put("Shinjiro", new Usuarios("Shinjiro Aragaki", 18, "Masculino", 55, "The Fool", 200.0, 65.0, "Brute Physical Attacker", false));

        // Inserindos SEES no BD
        for (String nomeUsuario : USUARIOS_SEES) {
            usuariosDAO.insertUsuario(usuarios.get(nomeUsuario));
        }

        // STREGA membros
        usuarios.put("Takaya", new Usuarios("Takaya Sakaki", 20, "Masculino", 23, "Fortune", 160.0, 140.0, "Dark Caster / Líder da Strega", true));
        usuarios.put("Jin", new Usuarios("Jin Shirato", 19, "Masculino", 10, "Hermit", 120.0, 130.0, "Support Hacker / Tech", true));
        usuarios.put("Chidori", new Usuarios("Chidori Yoshino", 17, "Feminino", 99, "Hanged Man", 130.0, 150.0, "Fire Caster / Emo Artista", true));

        // Inserindo STREGA no BD
        for (String nomeUsuario : USUARIOS_STREGA) {
            usuariosDAO.insertUsuario(usuarios.get(nomeUsuario));
        }
    }

    // ----------------------------------------- Shadows(inimigos): -------------------------------------

    public static void popularShadows(List<Shadow> shadows) {
        Random random = new Random();

        Shadow[] shadowArray = {
                new Shadow("Dancer of Sorrow", 160, 18, "Moon", List.of("Magic"), "Fire", "Ice", random.nextInt(50) + 30),
                new Shadow("Brutal Ogre", 210, 22, "Strength", List.of("Physical"), "Wind", "Fire", random.nextInt(50) + 30),
                new Shadow("Twisted Teacher", 130, 14, "Hierophant", List.of("Mental"), "Electricity", "Dark", random.nextInt(50) + 30),
                new Shadow("Dark Cupid", 100, 11, "Lovers", List.of("Ailment"), "Bless", "Dark", random.nextInt(50) + 30),
                new Shadow("Searing Beast", 190, 20, "Chariot", List.of("Physical", "Magic"), "Ice", "Fire", random.nextInt(50) + 30)
        };

        shadows.addAll(Arrays.asList(shadowArray));

        ShadowDAO shadowDAO = new ShadowDAO();
        for (Shadow shadow : shadows) {
            shadowDAO.insertShadow(shadow);
        }
    }

    public static void popularItens(Map<String, Itens> itens) {
        // DAOs de Itens:
        EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
        ArmaDAO armaDAO = new ArmaDAO();
        ConsumiveisDAO consumiveisDAO = new ConsumiveisDAO();

        // ---------------------------------------- Equipamentos: ----------------------------------------

        Map<String, Equipamento> equipamentos = new HashMap<>();
        equipamentos.put("God Hand", new Equipamento("God Hand", "Luva", 220.0, 2, "Crit Chance +15%", "unissex"));
        equipamentos.put("Kongou Hakama", new Equipamento("Kongou Hakama", "Armadura", 300.0, 3, "Resist Físico", "unissex"));
        equipamentos.put("Regent", new Equipamento("Regent", "Acessório", 150.0, 4, "Mag +10%", "unissex"));
        equipamentos.put("Black Headband", new Equipamento("Black Headband", "Cabeça", 120.0, 5, "Esquiva +10%", "unissex"));
        equipamentos.put("Omnipotent Orb", new Equipamento("Omnipotent Orb", "Especial", 5000.0, 6, "Anula Fraquezas", "unissex"));
        equipamentos.put("Sabbath Gloves", new Equipamento("Sabbath Gloves", "Luva", 200.0, 7, "Atk +10% / HP -5%", "unissex"));
        equipamentos.put("Dark Empress Dress", new Equipamento("Dark Empress Dress", "Armadura", 280.0, 8, "SP Regen +5/tur", "unissex"));
        equipamentos.put("Eternal Slippers", new Equipamento("Eternal Slippers", "Botas", 170.0, 9, "Velocidade +15%", "unissex"));
        equipamentos.put("Seven Sisters Badge", new Equipamento("Seven Sisters Badge", "Acessório", 130.0, 10, "Status Negativos -50%", "unissex"));

        // Adiciona equipamento num mapa e insere no banco de dados:
        for (Map.Entry<String, Equipamento> entry : equipamentos.entrySet()) {
            itens.put(entry.getKey(), entry.getValue());
            equipamentoDAO.insertEquipamento(entry.getValue());
        }

        // ---------------------------------------- Armas: ------------------------------------------

        Map<String, Arma> armas = new HashMap<>();
        armas.put("Paradise Lost", new Arma("Paradise Lost", "Espada", 250.0, "Raro", 65.0));
        armas.put("Espada Curta", new Arma("Espada Curta", "Espada", 1500.0, "Normal", 48.0));
        armas.put("Luvas de Combate", new Arma("Luvas de Combate", "Soco", 1800.0, "Normal", 52.0));
        armas.put("Naginata", new Arma("Naginata", "Lança", 2000.0, "Normal", 56.0));
        armas.put("Arco do Vento", new Arma("Arco do Vento", "Arco", 2200.0, "Raro", 60.0));

        // Adiciona arma num mapa e insere no banco de dados:
        for (Map.Entry<String, Arma> entry : armas.entrySet()) {
            itens.put(entry.getKey(), entry.getValue());
            armaDAO.insertArma(entry.getValue());
        }

        // ------------------------------------ Consumíveis -------------------------------------------------

        Map<String, Consumiveis> consumiveis = new HashMap<>();
        consumiveis.put("Revival Bead", new Consumiveis("Revival Bead", "Revivir", 600, "Revive um aliado com 50% de HP"));
        consumiveis.put("Bead Chain", new Consumiveis("Bead Chain", "Cura", 1000, "Restaura totalmente o HP de todos os aliados"));
        consumiveis.put("Soul Drop", new Consumiveis("Soul Drop", "SP", 150, "Recupera 10 SP de um aliado"));
        consumiveis.put("Snuff Soul", new Consumiveis("Snuff Soul", "SP", 400, "Recupera 50 SP de um aliado"));
        consumiveis.put("Medical Powder", new Consumiveis("Medical Powder", "Cura", 100, "Restaura 50 HP de um aliado"));

        // Adiciona consumível num mapa e insere no banco de dados:
        for (Map.Entry<String, Consumiveis> entry : consumiveis.entrySet()) {
            itens.put(entry.getKey(), entry.getValue());
            consumiveisDAO.insertConsumivel(entry.getValue());
        }
    }

    private static void adicionarHabilidadesPersonasProtagonista(Protagonista protagonista, Random random) {
        List<Personas> personas = protagonista.getPersonas();

        if (personas.isEmpty()) return;

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
    }

    private static void adicionarHabilidadesPersonasUsuario(Map<String, Usuarios> usuarios, Random random) {
        HabilidadesDAO habilidadesDAO = new HabilidadesDAO();

        // Yukari - Isis
        if (usuarios.get("Yukari") != null && usuarios.get("Yukari").getPersonas() != null) {
            usuarios.get("Yukari").getPersonas().addHabilidade(new Habilidades("Agilao", "Fire", "Causa dano de fogo médio a um inimigo", random.nextInt(10) + 20));
            usuarios.get("Yukari").getPersonas().addHabilidade(new Habilidades("Rebellion", "Support", "Aumenta a taxa de acerto crítico de um aliado por 3 turnos", 0));
            usuarios.get("Yukari").getPersonas().addHabilidade(new Habilidades("Maragi", "Fire", "Causa dano de fogo leve a todos os inimigos", random.nextInt(10) + 20));
            usuarios.get("Yukari").getPersonas().addHabilidade(new Habilidades("Fatal End", "Physical", "Causa dano físico pesado a um inimigo", random.nextInt(10) + 20));

            // Inserir no banco de dados
            for (Habilidades habilidade : usuarios.get("Yukari").getPersonas().getHabilidades()) {
                habilidadesDAO.insertHabilidade(habilidade);
            }
        }

        // Mitsuru - Artemisia
        if (usuarios.get("Mitsuru") != null && usuarios.get("Mitsuru").getPersonas() != null) {
            usuarios.get("Mitsuru").getPersonas().addHabilidade(new Habilidades("Bufudyne", "Ice", "Causa dano de gelo pesado a um inimigo", random.nextInt(10) + 20));
            usuarios.get("Mitsuru").getPersonas().addHabilidade(new Habilidades("Mind Charge", "Support", "Aumenta o próximo dano mágico causado", 0));
            usuarios.get("Mitsuru").getPersonas().addHabilidade(new Habilidades("Mabufula", "Ice", "Causa dano de gelo médio a todos os inimigos", random.nextInt(10) + 20));
            usuarios.get("Mitsuru").getPersonas().addHabilidade(new Habilidades("Ice Break", "Debuff", "Remove resistência ao gelo de um inimigo", 0));

            // Inserir no banco de dados
            for (Habilidades habilidade : usuarios.get("Mitsuru").getPersonas().getHabilidades()) {
                habilidadesDAO.insertHabilidade(habilidade);
            }
        }

        // Junpei - Trismegistus
        if (usuarios.get("Junpei") != null && usuarios.get("Junpei").getPersonas() != null) {
            usuarios.get("Junpei").getPersonas().addHabilidade(new Habilidades("Agidyne", "Fire", "Causa dano de fogo pesado a um inimigo", random.nextInt(10) + 20));
            usuarios.get("Junpei").getPersonas().addHabilidade(new Habilidades("Blade of Fury", "Physical", "Causa múltiplos ataques físicos leves", random.nextInt(10) + 20));
            usuarios.get("Junpei").getPersonas().addHabilidade(new Habilidades("Maragidyne", "Fire", "Causa dano de fogo pesado a todos os inimigos", random.nextInt(10) + 20));
            usuarios.get("Junpei").getPersonas().addHabilidade(new Habilidades("Revolution", "Support", "Todos os ataques se tornam críticos por 1 turno", 0));

            // Inserir no banco de dados
            for (Habilidades habilidade : usuarios.get("Junpei").getPersonas().getHabilidades()) {
                habilidadesDAO.insertHabilidade(habilidade);
            }
        }

        // Akihiko - Caesar
        if (usuarios.get("Akihiko") != null && usuarios.get("Akihiko").getPersonas() != null) {
            usuarios.get("Akihiko").getPersonas().addHabilidade(new Habilidades("Ziodyne", "Electric", "Causa dano elétrico pesado a um inimigo", random.nextInt(10) + 20));
            usuarios.get("Akihiko").getPersonas().addHabilidade(new Habilidades("Maziodyne", "Electric", "Causa dano elétrico pesado a todos os inimigos", random.nextInt(10) + 20));
            usuarios.get("Akihiko").getPersonas().addHabilidade(new Habilidades("Thunder Reign", "Electric", "Causa dano elétrico severo a um inimigo", random.nextInt(10) + 20));
            usuarios.get("Akihiko").getPersonas().addHabilidade(new Habilidades("Elec Break", "Debuff", "Remove resistência elétrica de um inimigo", 0));

            // Inserir no banco de dados
            for (Habilidades habilidade : usuarios.get("Akihiko").getPersonas().getHabilidades()) {
                habilidadesDAO.insertHabilidade(habilidade);
            }
        }

        // Fuuka - Juno (Support)
        if (usuarios.get("Fuuka") != null && usuarios.get("Fuuka").getPersonas() != null) {
            usuarios.get("Fuuka").getPersonas().addHabilidade(new Habilidades("Full Analysis", "Support", "Revela todas as informações do inimigo", 0));
            usuarios.get("Fuuka").getPersonas().addHabilidade(new Habilidades("Healing Wave", "Healing", "Restaura HP de todos os aliados gradualmente", 0));
            usuarios.get("Fuuka").getPersonas().addHabilidade(new Habilidades("Oracle", "Support", "Aumenta precisão e evasão de todos os aliados", 0));
            usuarios.get("Fuuka").getPersonas().addHabilidade(new Habilidades("Escape Route", "Support", "Garante fuga bem-sucedida da batalha", 0));

            // Inserir no banco de dados
            for (Habilidades habilidade : usuarios.get("Fuuka").getPersonas().getHabilidades()) {
                habilidadesDAO.insertHabilidade(habilidade);
            }
        }

        // Aigis - Athena
        if (usuarios.get("Aigis") != null && usuarios.get("Aigis").getPersonas() != null) {
            usuarios.get("Aigis").getPersonas().addHabilidade(new Habilidades("Akasha Arts", "Physical", "Causa dano físico severo a um inimigo", random.nextInt(10) + 20));
            usuarios.get("Aigis").getPersonas().addHabilidade(new Habilidades("Mediarahan", "Healing", "Restaura totalmente o HP de todos os aliados", 0));
            usuarios.get("Aigis").getPersonas().addHabilidade(new Habilidades("Samarecarm", "Healing", "Revive um aliado com HP total", 0));
            usuarios.get("Aigis").getPersonas().addHabilidade(new Habilidades("Shield All", "Support", "Reduz dano recebido por todos os aliados", 0));

            // Inserir no banco de dados
            for (Habilidades habilidade : usuarios.get("Aigis").getPersonas().getHabilidades()) {
                habilidadesDAO.insertHabilidade(habilidade);
            }
        }

        // Koromaru - Cerberus
        if (usuarios.get("Koromaru") != null && usuarios.get("Koromaru").getPersonas() != null) {
            usuarios.get("Koromaru").getPersonas().addHabilidade(new Habilidades("Agidyne", "Fire", "Causa dano de fogo pesado a um inimigo", random.nextInt(10) + 20));
            usuarios.get("Koromaru").getPersonas().addHabilidade(new Habilidades("Mudoon", "Dark", "Alta chance de causar morte instantânea a um inimigo", random.nextInt(10) + 20));
            usuarios.get("Koromaru").getPersonas().addHabilidade(new Habilidades("Megidola", "Almighty", "Causa dano pesado a todos os inimigos", random.nextInt(10) + 20));
            usuarios.get("Koromaru").getPersonas().addHabilidade(new Habilidades("Fire Break", "Debuff", "Remove resistência ao fogo de um inimigo", 0));

            // Inserir no banco de dados
            for (Habilidades habilidade : usuarios.get("Koromaru").getPersonas().getHabilidades()) {
                habilidadesDAO.insertHabilidade(habilidade);
            }
        }

        // Ken - Kala-Nemi
        if (usuarios.get("Ken") != null && usuarios.get("Ken").getPersonas() != null) {
            usuarios.get("Ken").getPersonas().addHabilidade(new Habilidades("Hamaon", "Light", "Alta chance de causar morte instantânea a um inimigo", random.nextInt(10) + 20));
            usuarios.get("Ken").getPersonas().addHabilidade(new Habilidades("Ziodyne", "Electric", "Causa dano elétrico pesado a um inimigo", random.nextInt(10) + 20));
            usuarios.get("Ken").getPersonas().addHabilidade(new Habilidades("Matarukaja", "Buff", "Aumenta o ataque de todos os aliados", 0));
            usuarios.get("Ken").getPersonas().addHabilidade(new Habilidades("Spear Master", "Physical", "Causa dano físico com lança a um inimigo", random.nextInt(10) + 20));

            // Inserir no banco de dados
            for (Habilidades habilidade : usuarios.get("Ken").getPersonas().getHabilidades()) {
                habilidadesDAO.insertHabilidade(habilidade);
            }
        }

        // Shinjiro - Castor
        if (usuarios.get("Shinjiro") != null && usuarios.get("Shinjiro").getPersonas() != null) {
            usuarios.get("Shinjiro").getPersonas().addHabilidade(new Habilidades("Akasha Arts", "Physical", "Causa dano físico severo a um inimigo", random.nextInt(10) + 20));
            usuarios.get("Shinjiro").getPersonas().addHabilidade(new Habilidades("Power Charge", "Support", "Aumenta o próximo dano físico causado", 0));
            usuarios.get("Shinjiro").getPersonas().addHabilidade(new Habilidades("Diarahan", "Healing", "Restaura totalmente o HP de um aliado", 0));
            usuarios.get("Shinjiro").getPersonas().addHabilidade(new Habilidades("Counterstrike", "Physical", "Contra-ataca quando recebe dano físico", random.nextInt(10) + 20));

            // Inserir no banco de dados
            for (Habilidades habilidade : usuarios.get("Shinjiro").getPersonas().getHabilidades()) {
                habilidadesDAO.insertHabilidade(habilidade);
            }
        }

        // Takaya - Hypnos (STREGA)
        if (usuarios.get("Takaya") != null && usuarios.get("Takaya").getPersonas() != null) {
            usuarios.get("Takaya").getPersonas().addHabilidade(new Habilidades("Mamudoon", "Dark", "Alta chance de causar morte instantânea a todos os inimigos", random.nextInt(10) + 20));
            usuarios.get("Takaya").getPersonas().addHabilidade(new Habilidades("Megidolaon", "Almighty", "Causa dano massivo a todos os inimigos", random.nextInt(10) + 20));
            usuarios.get("Takaya").getPersonas().addHabilidade(new Habilidades("Evil Smile", "Ailment", "Causa medo a todos os inimigos", 0));
            usuarios.get("Takaya").getPersonas().addHabilidade(new Habilidades("Dark Energy", "Dark", "Causa dano sombrio severo a um inimigo", random.nextInt(10) + 20));

            // Inserir no banco de dados
            for (Habilidades habilidade : usuarios.get("Takaya").getPersonas().getHabilidades()) {
                habilidadesDAO.insertHabilidade(habilidade);
            }
        }

        // Jin - Moros (STREGA)
        if (usuarios.get("Jin") != null && usuarios.get("Jin").getPersonas() != null) {
            usuarios.get("Jin").getPersonas().addHabilidade(new Habilidades("Garudyne", "Wind", "Causa dano de vento pesado a um inimigo", random.nextInt(10) + 20));
            usuarios.get("Jin").getPersonas().addHabilidade(new Habilidades("Magarudyne", "Wind", "Causa dano de vento pesado a todos os inimigos", random.nextInt(10) + 20));
            usuarios.get("Jin").getPersonas().addHabilidade(new Habilidades("Wind Break", "Debuff", "Remove resistência ao vento de um inimigo", 0));
            usuarios.get("Jin").getPersonas().addHabilidade(new Habilidades("Dekunda", "Support", "Remove penalidades de status de todos os aliados", 0));

            // Inserir no banco de dados
            for (Habilidades habilidade : usuarios.get("Jin").getPersonas().getHabilidades()) {
                habilidadesDAO.insertHabilidade(habilidade);
            }
        }

        // Chidori - Medea (STREGA)
        if (usuarios.get("Chidori") != null && usuarios.get("Chidori").getPersonas() != null) {
            usuarios.get("Chidori").getPersonas().addHabilidade(new Habilidades("Agidyne", "Fire", "Causa dano de fogo pesado a um inimigo", random.nextInt(10) + 20));
            usuarios.get("Chidori").getPersonas().addHabilidade(new Habilidades("Maragidyne", "Fire", "Causa dano de fogo pesado a todos os inimigos", random.nextInt(10) + 20));
            usuarios.get("Chidori").getPersonas().addHabilidade(new Habilidades("Mediarahan", "Healing", "Restaura totalmente o HP de todos os aliados", 0));
            usuarios.get("Chidori").getPersonas().addHabilidade(new Habilidades("Spring of Life", "Healing", "Regenera HP gradualmente por várias rodadas", 0));

            // Inserir no banco de dados
            for (Habilidades habilidade : usuarios.get("Chidori").getPersonas().getHabilidades()) {
                habilidadesDAO.insertHabilidade(habilidade);
            }
        }
    }


}