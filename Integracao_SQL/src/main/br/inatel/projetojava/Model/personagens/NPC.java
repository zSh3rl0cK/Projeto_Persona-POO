package main.br.inatel.projetojava.Model.personagens;

import main.br.inatel.projetojava.Model.personagens.abstratos.SerHumano;
import main.br.inatel.projetojava.Model.personagens.interacao.Interacao;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Protagonista;

import static main.br.inatel.projetojava.Model.personagens.interacao.InteracaoManager.interagir;
import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class NPC extends SerHumano implements Interacao {

    private String ocupacao;
    // private int id; // Apenas se inserir no BD
    private int nivelConfidant = 0; // todos iniciam em 0.

    public NPC(String nome, int idade, String genero, String ocupacao, String arcana) {
        super(nome, idade, genero, arcana);
        this.ocupacao = ocupacao;
    }

    /* Usar se inserir no BD
    public NPC(String nome, int idade, String genero, String ocupacao, String arcana, int id) {
        super(nome, idade, genero, arcana);
        this.ocupacao = ocupacao;
        // this.id = id;
    }
    */

    @Override
    public void interacao(Protagonista protagonista) {
        System.out.println("NPC" + nome + " interage!");

        interagir(this, protagonista);
    }

    @Override
    public void mostraInfoPersonagem() {
        System.out.println(ANSI_CYAN  + "\nNome do NPC: " + nome + ANSI_RESET);
        System.out.println(ANSI_BLUE + "Idade: " + idade + " anos");
        System.out.println("Gênero: " + genero);
        System.out.println("Ocupacao: " + ocupacao + ANSI_RESET);
        if(arcana == null){
            System.out.println(ANSI_BLUE + "Não tem arcana!" + ANSI_RESET);
        }
        else {
            System.out.println(ANSI_BLUE + "Arcana: " + arcana + "\n" + ANSI_RESET);
        }
    }

    public String getOcupacao() {
        return ocupacao;
    }

    /* public int getId() {
        return id;
    }*/

    public int getNivelConfidant() {
        return nivelConfidant;
    }

    public void setNivelConfidant(int nivelConfiant) {
        this.nivelConfidant = nivelConfiant;
    }

    /*public void setId(int id) {
        this.id = id;
    }*/

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
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

    public void setArcana(String arcana){
        this.arcana = arcana;
    }
}
