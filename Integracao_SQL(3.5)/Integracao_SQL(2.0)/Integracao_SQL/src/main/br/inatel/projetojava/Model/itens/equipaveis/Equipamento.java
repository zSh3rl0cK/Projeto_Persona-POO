package main.br.inatel.projetojava.Model.itens.equipaveis;

import main.br.inatel.projetojava.Model.itens.EquipaItem;
import main.br.inatel.projetojava.Model.itens.geral.Itens;
import main.br.inatel.projetojava.Model.personagens.abstratos.UsuarioPersona;

import java.util.HashSet;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class Equipamento extends Itens implements EquipaItem {
    private final double defesa;
    private final String genero;

    public Equipamento(String nome, String tipo, double valor, double defesa, String status, String genero) {
        super(nome, tipo, valor, status);
        this.defesa = defesa;
        this.genero = genero;
    }

    @Override
    public void equiparItem(UsuarioPersona usuario) {
        if (usuario.getItem() == null) {
            usuario.setItens(new HashSet<>()); // todo: meio sem necessidade fazer isso caso a instancia seja criada já
        }
        usuario.setItem(this);
        usuario.setDefesa(usuario.getDefesa() + this.getDefesa());
        /*if(this.getDefesa() > 0 && this.getDefesa() <= 100){
            usuario.setHp(usuario.getHp() + 20);
        }
        else if(this.getDefesa() > 100 && this.getDefesa() <= 200){
            usuario.setHp(usuario.getHp() + 40);
        }
        else{
            usuario.setHp(usuario.getHp() + 60);
        }*/
    }

    @Override
    public String toString() {
        return ANSI_CYAN + "Nome: " + getNome() + ANSI_RESET +
                ANSI_BLUE + ", Tipo: " + getTipo() +
                ", Valor: " + getValor() +
                ", Status: " + getStatus() +
                ", Defesa: " + getDefesa() +
                ", Gênero: " + getGenero() + ANSI_RESET;
    }

    public String getGenero() {
        return genero;
    }

    public double getDefesa() {
        return defesa;
    }

}
