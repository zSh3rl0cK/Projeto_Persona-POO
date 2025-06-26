package main.br.inatel.projetojava.Model.sistema.inicializacao;

import main.br.inatel.projetojava.Model.itens.lojas.FarmaciaAohige;
import main.br.inatel.projetojava.Model.itens.lojas.LojadeArmas;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Protagonista;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Usuarios;
import main.br.inatel.projetojava.Model.personagens.NPC;
import main.br.inatel.projetojava.Model.personas.seres.Shadow;
import main.br.inatel.projetojava.Model.itens.geral.Itens;
import main.br.inatel.projetojava.Model.sistema.Cidade;

import java.util.*;

public class DadosIniciais {

    private Protagonista protagonista;
    private Map<String, Usuarios> usuarios;
    private Map<String, NPC> npcs;
    private List<Shadow> shadows;
    private Map<String, Itens> itens;
    private LojadeArmas lojaArmas;
    private FarmaciaAohige farmacia;
    private Cidade tatsumiPort;

    public DadosIniciais(Protagonista protagonista, Map<String, Usuarios> usuarios,
                         Map<String, NPC> npcs, List<Shadow> shadows,
                         Map<String, Itens> itens, LojadeArmas lojaArmas,
                         FarmaciaAohige farmacia, Cidade tatsumiPort) {
        this.protagonista = protagonista;
        this.usuarios = usuarios;
        this.npcs = npcs;
        this.shadows = shadows;
        this.itens = itens;
        this.lojaArmas = lojaArmas;
        this.farmacia = farmacia;
        this.tatsumiPort = tatsumiPort;
    }

    // Getters
    public Protagonista getProtagonista() { return protagonista; }
    public Map<String, Usuarios> getUsuarios() { return usuarios; }
    public Map<String, NPC> getNpcs() { return npcs; }
    public List<Shadow> getShadows() { return shadows; }
    public Map<String, Itens> getItens() { return itens; }
    public LojadeArmas getLojaArmas() { return lojaArmas; }
    public FarmaciaAohige getFarmacia() { return farmacia; }
    public Cidade getTatsumiPort() { return tatsumiPort; }

}
