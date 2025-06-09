package main.br.inatel.projetojava.Model.personagens;

import java.util.HashMap;

import main.br.inatel.projetojava.Model.itens.geral.Itens;
import main.br.inatel.projetojava.Model.itens.auxiliares.Consumiveis;
import main.br.inatel.projetojava.Model.itens.equipaveis.Arma;
import main.br.inatel.projetojava.Model.itens.equipaveis.Equipamento;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class Inventario  {
    private HashMap<Itens, Integer> itens = new HashMap<>();

    public void mostrarInventarioPersonagem() { // mano:
        //Funcionamento básico:
        //
        //Se a chave existe: retorna o valor associado à chave
        //Se a chave não existe: retorna o valor padrão especificado ok, um amigo meu quer ver dps kkkk
        // vo manda pro claude faezr o ngc com isntace of
        // Todo?: outra forma de fazer essa logica seria adicionar os ittens instancias de arma no arma
        if(!itens.isEmpty()) {
            System.out.println(ANSI_YELLOW + "\n----- Arma -----" + ANSI_RESET);
            for (Itens arma : itens.keySet()) {
                if (arma instanceof Arma) {
                    System.out.println(arma);
                }
            }
        }
        else{
            System.out.println(ANSI_BLUE + "Personagem sem armas!" + ANSI_RESET);
        }

        if(!itens.isEmpty()) {
            System.out.println(ANSI_YELLOW + "----- Equipamento -----" + ANSI_RESET);
            for (Itens equipamento : itens.keySet()) {
                if(equipamento instanceof Equipamento) {
                    System.out.println(equipamento);
                }
            }
        }
        else{
            System.out.println(ANSI_BLUE + "Personagem sem equipamentos!" + ANSI_RESET);
        }

        if(!itens.isEmpty()) {
            System.out.println(ANSI_YELLOW + "----- Consumiveis -----" + ANSI_RESET);
            for (Itens consumivel : itens.keySet()) {
                if(consumivel instanceof Consumiveis) {
                    System.out.println(consumivel);
                }
            }
        }
        else{
            System.out.println(ANSI_BLUE + "Personagem sem consumiveis!" + ANSI_RESET);
        }
        System.out.println();
    }

    // Getters e setters
    public void adicionarItem(Itens item, int quantidade) {
        itens.put(item, itens.getOrDefault(item, 0) + quantidade);
    }

    public void removerItem(Itens item) {
        itens.remove(item);
    }

    public int getQuantidadeArma(Arma arma) {
        // Verifica se o item existe no HashMap e se é uma instância de Arma
        if (arma != null) {
            return itens.getOrDefault((Itens) arma, 0);
        }
        return 0;
    }

    public int getQuantidadeEquipamento(Equipamento equipamento) {
        // Verifica se o item existe no HashMap e se é uma instância de Equipamento
        if (equipamento != null) {
            return itens.getOrDefault((Itens) equipamento, 0);
        }
        return 0;
    }

    public int getQuantidadeConsumivel(Consumiveis consumivel) {
        // Verifica se o item existe no HashMap e se é uma instância de Consumiveis
        if (consumivel != null) {
            return itens.getOrDefault((Itens) consumivel, 0);
        }
        return 0;
    }

    // Méto.do que retorna apenas os consumiveis do inventário
    public HashMap<Consumiveis, Integer> getConsumiveis() {
        HashMap<Consumiveis, Integer> consumiveisMap = new HashMap<>();

        // Itera sobre todos os itens no HashMap unificado
        for (HashMap.Entry<Itens, Integer> entry : itens.entrySet()) {
            Itens item = entry.getKey();
            Integer quantidade = entry.getValue();

            // Verifica se o item é uma instância de Consumiveis
            if (item instanceof Consumiveis) {
                consumiveisMap.put((Consumiveis) item, quantidade);
            }
        }

        return consumiveisMap;
    }
}