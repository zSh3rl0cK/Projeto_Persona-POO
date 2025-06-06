package main.br.inatel.projetojava.Model.personagens.jogaveis;


import main.br.inatel.projetojava.Model.itens.auxiliares.Ativador;
import main.br.inatel.projetojava.Model.personagens.Inventario;
import main.br.inatel.projetojava.Model.personagens.abstratos.UsuarioPersona;
import main.br.inatel.projetojava.Model.personagens.interacao.Interacao;
import main.br.inatel.projetojava.Model.personas.seres.Personas;

import static main.br.inatel.projetojava.Model.personagens.interacao.InteracaoManager.interagir;
import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class Usuarios extends UsuarioPersona implements Interacao {
    private int idUsuario;
    private String papel;
    private Personas persona;
    private boolean vilao;
    private int nivelConfidant = 0; // Todos iniciam 0.
    private Ativador ativador;

    // Construtor existente
    public Usuarios(String nome, int idade, String genero, int nivel, String arcana, double hp, double sp, String papel, boolean vilao) {
        super(nome, idade, genero, nivel, arcana, hp, sp);
        this.vilao = vilao;
        this.inventario = new Inventario();
        this.papel = papel;
    }

    public Usuarios(String nome, int idade, String genero, int nivel, String arcana, double hp, double sp, String papel, boolean vilao, int id) {
        super(nome, idade, genero, nivel, arcana, hp, sp);
        this.vilao = vilao;
        this.inventario = new Inventario();
        this.papel = papel;
        this.idUsuario = id;
    }

    @Override
    public void interacao(Protagonista protagonista) {
        interagir(this, protagonista);
    }

    @Override
    public void mostraInfoPersonagem() {
        System.out.println(ANSI_CYAN + "Nome do Usuário: " + nome + ANSI_RESET);
        System.out.println(ANSI_BLUE + "Idade: " + idade + " anos");
        System.out.println("Gênero: " + genero);
        System.out.println("Nível: " + nivel);
        System.out.println("Vida: " + hp);
        System.out.println("Mana: " + sp);
        System.out.println("Papel: " + papel);
        System.out.println("Arcana: " + arcana + ANSI_RESET);
        if(isVilao())
            System.out.println(ANSI_BLUE + "Vilão" + ANSI_RESET);
        else
            System.out.println(ANSI_BLUE + "Herói" + ANSI_RESET);
    }

    @Override
    public void addPersona(Personas persona) {
        this.persona = persona;
    }


    //--------------------------------- Getters e Setters ---------------------------------
    public Personas getPersonas() {
        return persona;
    }

    public boolean isVilao(){
        return vilao;
    }

    public String getPapel(){
        return papel;
    }
    public int getNivel(){
        return nivel;
    }

    public int getAtivadorId() {
        return ativador.getIdAtivador();
    }

    public void setId(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getId() {
        return this.idUsuario;
    }

    public int getNivelConfidant() {
        return nivelConfidant;
    }

    public void setNivelConfidant(int nivelConfidant) {
        this.nivelConfidant = nivelConfidant;
    }

    public void setVilao(boolean vilao) {
        this.vilao = vilao;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setIdade(int idade){
        this.idade = idade;
    }

    public void setGenero(String genero){
        this.genero = genero;
    }

    public void setHp(double hp){
        this.hp = hp;
    }

    public Ativador getAtivador() {
        return ativador;
    }
}
