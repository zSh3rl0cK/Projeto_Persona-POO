package main.br.inatel.projetojava.Model.personas;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class Habilidades {

    private final String nome;
    private final String tipo;
    private final String efeito;
    private final int dano;
    // private int id; // Usar apenas se for inserir no banco de dados.

    public Habilidades(String nome, String tipo, String efeito, int dano){
        this.nome = nome;
        this.tipo = tipo;
        this.efeito = efeito;
        this.dano = dano;
    }

    /*
    Usar se for inserir no banco de dados:
    public Habilidades(String nome, String tipo, String efeito, double dano, int id){
        this.nome = nome;
        this.tipo = tipo;
        this.efeito = efeito;
        this.dano = dano;
        this.id = id;
    }*/

    public void descreverHabilidade(){
        System.out.println(ANSI_CYAN + "Nome da Habilidade: " + getNome() + ANSI_RESET);
        System.out.println(ANSI_BLUE + "Tipo: " + getTipo());
        System.out.println("Efeito: " + getEfeito());
        System.out.println("Dano: " + getDano() + ANSI_RESET);
    }

    // Setters e Getters:


    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public String getEfeito() {
        return efeito;
    }

    public int getDano() {
        return dano;
    }

    /*
    public int getId() {
        return id;
    }
    */
}
