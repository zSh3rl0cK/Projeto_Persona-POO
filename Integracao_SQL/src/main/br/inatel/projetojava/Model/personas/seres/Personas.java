package main.br.inatel.projetojava.Model.personas.seres;

import main.br.inatel.projetojava.Model.personas.Habilidades;
import java.util.ArrayList;
import java.util.List;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class Personas {

    private String nome;
    private int nivel;
    private String arcana;
    private List<String> tipo;
    private String fraqueza;
    private String resistencia;
    private int dano;
    private int id;
    private final List<Habilidades> habilidades = new ArrayList<>();

    public void addHabilidade(Habilidades habilidade) {
        habilidades.add(habilidade);
    }


    public Personas(String nome, int nivel, String arcana, List<String> tipo, String fraqueza, String resistencia, int dano) {
        this.nome = nome;
        this.nivel = nivel;
        this.arcana = arcana;
        this.tipo = tipo;
        this.fraqueza = fraqueza;
        this.resistencia = resistencia;
        this.dano = dano;
    }

    // Sobrecarga de construtor para busca de persona com ID em SQL
    public Personas(String nome, int nivel, String arcana, List<String> tipo, String fraqueza, String resistencia, int dano, int id) {
        this.nome = nome;
        this.nivel = nivel;
        this.arcana = arcana;
        this.tipo = tipo;
        this.fraqueza = fraqueza;
        this.resistencia = resistencia;
        this.dano = dano;
        this.id = id;
    }

    public void mostrarStatusPersona(){
        System.out.println(ANSI_YELLOW + "Nome da Persona: " + nome + ANSI_RESET);
        System.out.println(ANSI_BLUE + "Nível: " + nivel);
        System.out.println("Arcana: " + arcana);
        System.out.println("Tipo: " + tipo.get(0) + ", " + tipo.get(1));
        System.out.println("Fraqueza: " + fraqueza);
        System.out.println("Resistência: " + resistencia);
        System.out.println("Dano: " + dano);
        System.out.println();
        System.out.println("Habilidades: " + ANSI_RESET);
        System.out.println();
        for(Habilidades habilidade : habilidades){
            habilidade.descreverHabilidade();
        }
    }

    // Setters n Getters:


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public List<String> getTipo() {
        return tipo;
    }

    public List<Habilidades> getHabilidades() {
        return habilidades;
    }

    public String getArcana() {
        return arcana;
    }

    public String getFraqueza() {
        return fraqueza;
    }

    public String getResistencia() {
        return resistencia;
    }

    public int getDano() {
        return dano;
    }

    public int getIdPersona() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFraqueza(String fraqueza) {
        this.fraqueza = fraqueza;
    }
    public void setResistencia(String resistencia) {
        this.resistencia = resistencia;
    }
    public void setDano(int dano) {
        this.dano = dano;
    }
    public void setTipo(List<String> tipo) {
        this.tipo = tipo;
    }
    public void setArcana(String arcana) {
        this.arcana = arcana;
    }
    public void setHabilidades(List<Habilidades> habilidades) {
        this.habilidades.addAll(habilidades);
    }
}