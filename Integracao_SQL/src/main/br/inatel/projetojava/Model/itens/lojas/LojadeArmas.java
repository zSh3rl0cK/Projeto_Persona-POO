package main.br.inatel.projetojava.Model.itens.lojas;

import main.br.inatel.projetojava.Model.itens.equipaveis.Arma;
import main.br.inatel.projetojava.Model.itens.equipaveis.Equipamento;
import main.br.inatel.projetojava.Model.itens.geral.Itens;
import main.br.inatel.projetojava.Model.personagens.NPC;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Protagonista;

import java.util.HashSet;
import java.util.Map;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class LojadeArmas extends Lojas {
    NPC vendedor = new NPC("Oficial Kurosawa", 43, "Masculino", "Vendedor de Armas", null);

    public LojadeArmas(String nome, Map<Itens, Integer> itens) {
        super(nome, itens);
    }

    @Override
    public void mostraInfoLoja() {
        System.out.println(ANSI_CYAN + "\nNome da Loja: " + this.getNome() + ANSI_RESET);
        vendedor.mostraInfoPersonagem();
    }

    @Override
    public void venderItem(Protagonista protagonista, Itens novo_item) {
        if (protagonista.getItem() == null) {
            protagonista.setItens(new HashSet<>());// Inicializa o HashSet se ele for nulo
        }
        if (protagonista.getSaldo() < novo_item.getValor()) {
            System.out.println(ANSI_RED + "Saldo insuficiente para realizar a compra." + ANSI_RESET);
        } else {
            switch (novo_item) {
                case Arma arma -> {
                    if (this.getItens().containsKey(novo_item) && this.getItens().get(novo_item) > 0) {
                        protagonista.getInventario().adicionarItem(arma, 1);
                        this.getItens().put(novo_item, this.getItens().get(novo_item) - 1);
                        if (this.getItens().get(novo_item) == 0) {
                            this.getItens().remove(novo_item);
                        }
                        protagonista.setSaldo(protagonista.getSaldo() - novo_item.getValor());
                    }
                }
                case Equipamento equipamento -> {
                    if (this.getItens().containsKey(novo_item) && this.getItens().get(novo_item) > 0) {
                        protagonista.getInventario().adicionarItem(equipamento, 1);
                        this.getItens().put(novo_item, this.getItens().get(novo_item) - 1);
                        if (this.getItens().get(novo_item) == 0) {
                            this.getItens().remove(novo_item);
                        }
                        protagonista.setSaldo(protagonista.getSaldo() - novo_item.getValor());
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + novo_item); // todo: tratar isso aqui
            }
        }
    }

    public void mostrarItens() {
        System.out.println();
        System.out.println(ANSI_CYAN + "Itens da Loja:" + ANSI_RESET);
        System.out.println();

        System.out.println(ANSI_YELLOW + "----- Armas para Venda -----" + ANSI_RESET);
        for (Map.Entry<Itens, Integer> entry : this.getItens().entrySet()) {
            if (entry.getKey() instanceof Arma) {
                System.out.println(ANSI_BLUE + entry.getKey() + " - Quantidade: " + entry.getValue() + ANSI_RESET);
            }
        }

        System.out.println();

        System.out.println(ANSI_YELLOW + "----- Equipamentos para Venda -----" + ANSI_RESET);
        for (Map.Entry<Itens, Integer> entry : this.getItens().entrySet()) {
            if (entry.getKey() instanceof Equipamento) {
                System.out.println(ANSI_BLUE + entry.getKey() + " - Quantidade: " + entry.getValue() + ANSI_RESET);
            }
        }

        System.out.println();
    }
}