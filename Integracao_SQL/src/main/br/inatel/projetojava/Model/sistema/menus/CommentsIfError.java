package main.br.inatel.projetojava.Model.sistema.menus;

public class CommentsIfError {

    /*
    HabilidadesDAO habilidadesdao = new HabilidadesDAO();

        for (Personas persona : protagonista.getPersonas()) {
            for (Habilidades habilidade : persona.getHabilidades()) {
                habilidadesdao.insertHabilidade(habilidade);
            }
        }


        habilidadesdao.insertHabilidade(protagonista.getPersonas().getFirst().getHabilidades().get(0));
        habilidadesdao.insertHabilidade(protagonista.getPersonas().getFirst().getHabilidades().get(1));
        habilidadesdao.insertHabilidade(protagonista.getPersonas().getFirst().getHabilidades().get(2));

        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(1).getHabilidades().get(0));
        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(1).getHabilidades().get(1));
        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(1).getHabilidades().get(2));

        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(2).getHabilidades().get(0));
        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(2).getHabilidades().get(1));
        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(2).getHabilidades().get(2));

        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(3).getHabilidades().get(0));
        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(3).getHabilidades().get(1));
        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(3).getHabilidades().get(2));

        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(4).getHabilidades().get(0));
        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(4).getHabilidades().get(1));
        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(4).getHabilidades().get(2));

        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(5).getHabilidades().get(0));
        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(5).getHabilidades().get(1));
        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(5).getHabilidades().get(2));

        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(6).getHabilidades().get(0));
        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(6).getHabilidades().get(1));
        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(6).getHabilidades().get(2));

        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(7).getHabilidades().get(0));
        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(7).getHabilidades().get(1));
        habilidadesdao.insertHabilidade(protagonista.getPersonas().get(7).getHabilidades().get(2));

        habilidadesdao.insertHabilidade(protagonista.getPersonas().getLast().getHabilidades().get(0));
        habilidadesdao.insertHabilidade(protagonista.getPersonas().getLast().getHabilidades().get(1));
        habilidadesdao.insertHabilidade(protagonista.getPersonas().getLast().getHabilidades().get(2));
        */

    /*
    HabilidadesDAO habilidadesdaouser = new HabilidadesDAO();

    String[] usuarios = {"Yukari", "Junpei", "Mitsuru", "Akihiko", "Ken", "Koromaru",
            "Aigis", "Shinjiro", "Takaya", "Jin", "Chidori"};

        for (String nomeUsuario : usuarios) {
        List<Habilidades> habilidades = user.get(nomeUsuario).getPersonas().getHabilidades();
        for (Habilidades habilidade : habilidades) {
            habilidadesdaouser.insertHabilidade(habilidade);
        }
    }


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
        */

    /*
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
    */

    /* // Inserção de usuários STREGA no banco de dados:
        usuariosDAO.insertUsuario(user.get("Takaya"));
        usuariosDAO.insertUsuario(user.get("Jin"));
        usuariosDAO.insertUsuario(user.get("Chidori"));
    */

    /*
        personasDAOuser.insertPersona(user.get("Yukari").getPersonas());
        personasDAOuser.insertPersona(user.get("Mitsuru").getPersonas());
        personasDAOuser.insertPersona(user.get("Junpei").getPersonas());
        personasDAOuser.insertPersona(user.get("Akihiko").getPersonas());
        personasDAOuser.insertPersona(user.get("Fuuka").getPersonas());
        personasDAOuser.insertPersona(user.get("Aigis").getPersonas());
        personasDAOuser.insertPersona(user.get("Koromaru").getPersonas());
        personasDAOuser.insertPersona(user.get("Ken").getPersonas());
        personasDAOuser.insertPersona(user.get("Shinjiro").getPersonas());

        personasDAOuser.insertPersona(user.get("Takaya").getPersonas());
        personasDAOuser.insertPersona(user.get("Jin").getPersonas());
        personasDAOuser.insertPersona(user.get("Chidori").getPersonas());
    */

    /*
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
        npcdao.insertNPC(npcs.get("Pharos"));
        npcdao.insertNPC(npcs.get("Hayase"));
    */

}
