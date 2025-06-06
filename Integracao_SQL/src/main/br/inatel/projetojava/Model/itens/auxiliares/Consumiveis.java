package main.br.inatel.projetojava.Model.itens.auxiliares;

import main.br.inatel.projetojava.Model.itens.geral.Itens;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class Consumiveis extends Itens {

    public Consumiveis(String nome, String tipo, double valor, String status) {
        super(nome, tipo, valor, status);
    }

    public String toString(){
        return ANSI_CYAN + "Nome: " + getNome() + ANSI_RESET +
                ANSI_BLUE + ", Tipo: " + getTipo() +
                ", Valor: " + getValor() +
                ", Status: " + getStatus() + ANSI_RESET;
    }

}