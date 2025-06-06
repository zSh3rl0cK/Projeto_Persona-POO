package main.br.inatel.projetojava.Model.personagens.abstratos;

public abstract class SerHumano {

    protected String nome;
    protected int idade;
    protected String genero;
    protected String arcana;


    public SerHumano(String nome, int idade, String genero, String arcana) {
        this.nome = nome;
        this.idade = idade;
        this.genero = genero;
        this.arcana = arcana;
    }

    public abstract void mostraInfoPersonagem();

    public String getNome(){
        return this.nome;
    }
    public int getIdade(){
        return this.idade;
    }
    public String getGenero(){
        return this.genero;
    }
    public String getArcana(){
        return this.arcana;
    }

}
