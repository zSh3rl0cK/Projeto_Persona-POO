package main.br.inatel.projetojava.Model.itens.geral;

import main.br.inatel.projetojava.Model.itens.equipaveis.Arma;
import main.br.inatel.projetojava.Model.itens.auxiliares.Consumiveis;
import main.br.inatel.projetojava.Model.itens.equipaveis.Equipamento;
import main.br.inatel.projetojava.Model.personagens.NPC;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Protagonista;

import java.util.HashSet;
import java.util.Map;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class LojadeItens {
    NPC vendedor = new NPC("Oficial Kurosawa", 43, "Masculino", "Vendedor de Armas", null);
    private final Map<Itens, Integer> itens;
    private final String nome;

    public LojadeItens(String nome, Map<Itens, Integer> itens) {
        this.nome = nome;
        this.itens = itens;
    }

    public void mostraInfoLojadeItens(){
        System.out.println(ANSI_CYAN + "\nNome da Loja: " + nome + ANSI_RESET);
        vendedor.mostraInfoPersonagem();
    }

    public void venderItem(Protagonista protagonista, Itens novo_item) {
        if (protagonista.getItem() == null) {
            protagonista.setItens(new HashSet<>());// Inicializa o HashSet se ele for nulo
        }
        if (protagonista.getSaldo() < novo_item.getValor()) {
            System.out.println(ANSI_RED + "Saldo insuficiente para realizar a compra." + ANSI_RESET);
        } else {
            switch (novo_item) {
                case Arma arma -> {
                    if (this.itens.containsKey(novo_item) && this.itens.get(novo_item) > 0) {
                        protagonista.getInventario().adicionarArma(arma, 1);
                        this.itens.put(novo_item, this.itens.get(novo_item) - 1);
                        if (this.itens.get(novo_item) == 0) {
                            this.itens.remove(novo_item);
                        }
                        protagonista.setSaldo(protagonista.getSaldo() - novo_item.getValor());
                    }
                }
                case Equipamento equipamento -> {
                    if (this.itens.containsKey(novo_item) && this.itens.get(novo_item) > 0) {
                        protagonista.getInventario().adicionarEquipamento(equipamento, 1);
                        this.itens.put(novo_item, this.itens.get(novo_item) - 1);
                        if (this.itens.get(novo_item) == 0) {
                            this.itens.remove(novo_item);
                        }
                        protagonista.setSaldo(protagonista.getSaldo() - novo_item.getValor());
                    }
                }
                case Consumiveis consumiveis -> {
                    if (this.itens.containsKey(novo_item) && this.itens.get(novo_item) > 0) {
                        protagonista.getInventario().adicionarConsumivel(consumiveis, 1);
                        this.itens.put(novo_item, this.itens.get(novo_item) - 1);
                        if (this.itens.get(novo_item) == 0) {
                            this.itens.remove(novo_item);
                        }
                        protagonista.setSaldo(protagonista.getSaldo() - novo_item.getValor());
                    }
                }
                default -> {
                }
            }
        }
    }

    public void mostrarItens() {
        System.out.println();
        System.out.println(ANSI_CYAN + "Itens da Loja:" + ANSI_RESET);
        System.out.println();

        System.out.println(ANSI_YELLOW + "----- Armas para Venda -----" + ANSI_RESET);
        for (Map.Entry<Itens, Integer> entry : itens.entrySet()) {
            if (entry.getKey() instanceof Arma) {
                System.out.println(ANSI_BLUE + entry.getKey() + " - Quantidade: " + entry.getValue() + ANSI_RESET);
            }
        }

        System.out.println();

        System.out.println(ANSI_YELLOW + "----- Equipamentos para Venda -----" + ANSI_RESET);
        for (Map.Entry<Itens, Integer> entry : itens.entrySet()) {
            if (entry.getKey() instanceof Equipamento) {
                System.out.println(ANSI_BLUE + entry.getKey() + " - Quantidade: " + entry.getValue() + ANSI_RESET);
            }
        }

        System.out.println();

        System.out.println(ANSI_YELLOW + "----- Consumiveis para Venda -----" + ANSI_RESET);
        for (Map.Entry<Itens, Integer> entry : itens.entrySet()) {
            if (entry.getKey() instanceof Consumiveis) {
                System.out.println(ANSI_BLUE + entry.getKey() + " - Quantidade: " + entry.getValue() + ANSI_RESET);
            }
        }

        System.out.println();
    }

    public Map<Itens, Integer> getItens() {
        return itens;
    }
}
