package main.br.inatel.projetojava.Model.itens.lojas;

import main.br.inatel.projetojava.Model.itens.geral.Itens;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Protagonista;

import java.util.Map;

public abstract class Lojas {

    private final String nome;
    private final Map<Itens, Integer> itens;

    public Lojas(String nome, Map<Itens, Integer> itens) {
        this.nome = nome;
        this.itens = itens;
    }


    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public Map<Itens, Integer> getItens() {
        return itens;
    }

    public abstract void mostraInfoLoja();

    public abstract void venderItem(Protagonista protagonista, Itens novo_item);

    public abstract void mostrarItens();

}
