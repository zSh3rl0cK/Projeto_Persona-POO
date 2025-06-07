package main.br.inatel.projetojava.Model.personas;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public record Habilidades(String nome, String tipo, String efeito, int dano) { // Classes Record são classes imutáveis que servem de armazenamento de dados

    // private int id; // Usar apenas se for inserir no banco de dados.

    /*
    Usar se for inserir no banco de dados:
    public Habilidades(String nome, String tipo, String efeito, double dano, int id){
        this.nome = nome;
        this.tipo = tipo;
        this.efeito = efeito;
        this.dano = dano;
        this.id = id;
    }*/

    public void descreverHabilidade() {
        System.out.println(ANSI_CYAN + "Nome da Habilidade: " + nome() + ANSI_RESET);
        System.out.println(ANSI_BLUE + "Tipo: " + tipo());
        System.out.println("Efeito: " + efeito());
        System.out.println("Dano: " + dano() + ANSI_RESET);
    }

    // Setters e Getters:

    /*
    public int getId() {
        return id;
    }
    */
}
