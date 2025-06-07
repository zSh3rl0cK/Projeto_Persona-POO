package main.br.inatel.projetojava.Model.itens.equipaveis;

import main.br.inatel.projetojava.Model.itens.geral.EquipaItem;
import main.br.inatel.projetojava.Model.itens.geral.Itens;
import main.br.inatel.projetojava.Model.personagens.abstratos.UsuarioPersona;

import java.util.HashSet;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class Arma extends Itens implements EquipaItem {

    private double dano;

    public Arma(String nome, String tipo, double valor, String status, double dano) {
        super(nome, tipo, valor, status);
        this.dano = dano;
    }

    @Override
    public void equiparItem(UsuarioPersona usuario) {
        if (usuario.getItem() == null) {
            usuario.setItens(new HashSet<>());
        }
        usuario.setItem(this);
        usuario.setArma(this);
    }

    @Override
    public String toString(){
        return ANSI_CYAN + "Nome: " + getNome() + ANSI_RESET +
                ANSI_BLUE + ", Tipo: " + getTipo() +
                ", Valor: " + getValor() +
                ", Status: " + getStatus() +
                ", Dano: " + this.dano + ANSI_RESET;
    }

    public double getDano() {
        return dano;
    }

    public void setDano(double dano) {
        this.dano = dano;
    }
}
