package main.br.inatel.projetojava.Model.personas.seres;

import java.util.List;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class Shadow extends Personas {

    private final double vida;
    private int id;

    public Shadow(String nome, double vida, int nivel, String arcana, List<String> tipo, String fraqueza, String resistencia, double dano) {
        super(nome, nivel, arcana, tipo, fraqueza, resistencia, dano);
        this.vida = vida;
    }

    public Shadow(String nome, double vida, int nivel, String arcana, List<String> tipo, String fraqueza, String resistencia, double dano, int id) {
        super(nome, nivel, arcana, tipo, fraqueza, resistencia, dano);
        this.vida = vida;
        this.id = id;
    }


    @Override
    public void mostrarStatusPersona(){
        super.mostrarStatusPersona();
    }

    public String toString() {
        return String.format(ANSI_BLUE + "\nNome: %s\nVida: %.2f\nNível: %d\nArcana: %s\nFraqueza: %s\nResistência: %s\nDano: %.2f" + ANSI_RESET,
                this.getNome(),
                this.vida,
                this.getNivel(),
                this.getArcana(),
                this.getFraqueza(),
                this.getResistencia(),
                this.getDano());
    }

    public int getIdPersona() {
        return id;
    }

}