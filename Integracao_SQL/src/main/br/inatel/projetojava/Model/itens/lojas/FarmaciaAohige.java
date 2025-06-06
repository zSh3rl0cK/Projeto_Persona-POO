package main.br.inatel.projetojava.Model.itens.lojas;

import main.br.inatel.projetojava.Model.itens.auxiliares.Consumiveis;
import main.br.inatel.projetojava.Model.itens.geral.Itens;
import main.br.inatel.projetojava.Model.personagens.NPC;
import main.br.inatel.projetojava.Model.personagens.jogaveis.Protagonista;

import java.util.HashSet;
import java.util.Map;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class FarmaciaAohige extends Lojas {
    NPC farmaceutico = new NPC("Aohige", 58, "Masculino","Farmacêutico", null);

    public FarmaciaAohige(String nome, Map<Itens, Integer> itens) {
        super(nome, itens);
    }

    @Override
    public void mostraInfoLoja() {
        System.out.println(ANSI_CYAN + "\nNome da Loja: " + this.getNome() + ANSI_RESET);
        farmaceutico.mostraInfoPersonagem();
    }

    @Override
    public void venderItem(Protagonista protagonista, Itens novo_item) {
        if (protagonista.getItem() == null) {
            protagonista.setItens(new HashSet<>()); // Evitar erros de NullPointer
        }
        if (protagonista.getSaldo() < novo_item.getValor()) {
            System.out.println(ANSI_RED + "Saldo insuficiente para realizar a compra." + ANSI_RESET);
        } else{
            if (novo_item instanceof Consumiveis consumiveis) {
                if (this.getItens().containsKey(novo_item) && this.getItens().get(novo_item) > 0) {
                    protagonista.getInventario().adicionarConsumivel(consumiveis, 1);
                    this.getItens().put(novo_item, this.getItens().get(novo_item) - 1);
                    if (this.getItens().get(novo_item) == 0) {
                        this.getItens().remove(novo_item);
                    }
                    protagonista.setSaldo(protagonista.getSaldo() - novo_item.getValor());
                }
            } else {
                throw new IllegalStateException("Unexpected value: " + novo_item); // todo: tratar erro erro
            }
        }
    }

    @Override
    public void mostrarItens() {
        System.out.println();
        System.out.println(ANSI_CYAN + "Itens da Loja:" + ANSI_RESET);
        System.out.println();

        System.out.println(ANSI_YELLOW + "----- Consumiveis para Venda -----" + ANSI_RESET);
        for (Map.Entry<Itens, Integer> entry : this.getItens().entrySet()) {
            if (entry.getKey() instanceof Consumiveis) {
                System.out.println(ANSI_BLUE + entry.getKey() + " - Quantidade: " + entry.getValue() + ANSI_RESET);
            }
        }
        System.out.println();
    }
}
