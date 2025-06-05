package main.br.inatel.projetojava.Model.personagens;

import java.util.HashMap;

import main.br.inatel.projetojava.Model.itens.geral.Itens;
import main.br.inatel.projetojava.Model.itens.auxiliares.Consumiveis;
import main.br.inatel.projetojava.Model.itens.equipaveis.Arma;
import main.br.inatel.projetojava.Model.itens.equipaveis.Equipamento;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class Inventario {
    private final HashMap<Arma, Integer> armas;
    private final HashMap<Equipamento, Integer> equipamentos;
    private final HashMap<Consumiveis, Integer> consumiveis;

    public Inventario() {
        this.armas = new HashMap<>();
        this.equipamentos = new HashMap<>();
        this.consumiveis = new HashMap<>();
    }

    public void mostrarInventarioPersonagem() {
        if(!armas.isEmpty()) {
            System.out.println(ANSI_YELLOW + "\n----- Arma -----" + ANSI_RESET);
            for (Arma arma : armas.keySet()) {
                System.out.println(arma);
            }
        }
        else{
            System.out.println(ANSI_BLUE + "Personagem sem armas!" + ANSI_RESET);
        }
        if(!equipamentos.isEmpty()) {
            System.out.println(ANSI_YELLOW + "----- Equipamento -----" + ANSI_RESET);
            for (Equipamento equipamento : equipamentos.keySet()) {
                System.out.println(equipamento);
            }
        }
        else{
            System.out.println(ANSI_BLUE + "Personagem sem equipamentos!" + ANSI_RESET);
        }

        if(!consumiveis.isEmpty()) {
            System.out.println(ANSI_YELLOW + "----- Consumiveis -----" + ANSI_RESET);
            for (Consumiveis consumivel : consumiveis.keySet()) {
                System.out.println(consumivel);
            }
        }
        else{
            System.out.println(ANSI_BLUE + "Personagem sem consumiveis!" + ANSI_RESET);
        }
        System.out.println();
    }


    public void adicionarArma(Arma arma, int quantidade) {
        armas.put(arma, armas.getOrDefault(arma, 0) + quantidade);
        // GetOrDefault : padrão é 0, quando se tem arma, incrementa.
    }

    public void adicionarEquipamento(Equipamento equipamento, int quantidade) {
        equipamentos.put(equipamento, equipamentos.getOrDefault(equipamento, 0) + quantidade);
    }

    public void adicionarConsumivel(Consumiveis consumivel, int quantidade) {
        consumiveis.put(consumivel, consumiveis.getOrDefault(consumivel, 0) + quantidade);
    }

    public void removerArma(Itens item) {
        if (item instanceof Arma) {
            armas.remove(item);
        }
    }

    public void removerEquipamento(Itens item) {
        if (item instanceof Equipamento) {
            equipamentos.remove(item);
        }
    }

    public void removerConsumivel(Itens item) {
        if (item instanceof Consumiveis) {
            consumiveis.remove(item);
        }
    }

    public int getQuantidadeArma(Arma arma) {
        return armas.getOrDefault(arma, 0);
    }

    public int getQuantidadeEquipamento(Equipamento equipamento) {
        return equipamentos.getOrDefault(equipamento, 0);
    }

    public int getQuantidadeConsumivel(Consumiveis consumivel) {
        return consumiveis.getOrDefault(consumivel, 0);
    }

    public HashMap<Consumiveis, Integer> getConsumiveis() {
        return new HashMap<>(consumiveis);
    }
}