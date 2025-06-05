package main.br.inatel.projetojava.Model.sistema;

import main.br.inatel.projetojava.Model.personagens.abstratos.SerHumano;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class Cidade {

    private String nomeCidade;
    private Map<String, ArrayList<SerHumano>> locais; // <nomeLocal, personagens no local>

    public Cidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
        this.locais = new HashMap<>();
    }

    // Adiciona um novo local à cidade (ex: "Escola", "Shopping", "Estação de trem")
    public void adicionarLocal(String nomeLocal) {
        if (!locais.containsKey(nomeLocal)) {
            locais.put(nomeLocal, new ArrayList<>());
        }
    }

    // Adiciona um personagem a um local específico
    public void adicionarPersonagemAoLocal(String nomeLocal, SerHumano personagem) {
        // caso o local nao exista, criando um novo
        if (!locais.containsKey(nomeLocal)) {
            adicionarLocal(nomeLocal);
        }
        locais.get(nomeLocal).add(personagem);
    }

    // Remove um personagem de um local -- usar pra caso um usuario va sair do local
    public void removerPersonagemDoLocal(String nomeLocal, SerHumano personagem) {
        if (locais.containsKey(nomeLocal)) {
            locais.get(nomeLocal).remove(personagem);
        }
        else{
            System.out.println(ANSI_RED + "\n"+"Chave de local invalida" + ANSI_RESET);
        }
    }

    // Lista os personagens disponíveis para interação em um local
    public ArrayList<SerHumano> getPersonagensNoLocal(String nomeLocal) {
        return locais.getOrDefault(nomeLocal, new ArrayList<>());
    }

    // Mostra todos os locais disponíveis
    public void listarLocais() {
        System.out.println(ANSI_BLUE + "Locais disponíveis em " + nomeCidade + ":" + ANSI_RESET);
        for (String local : locais.keySet()) {
            System.out.println(ANSI_PURPLE + "- " + local + ANSI_RESET);
        }
    }

    // Mostra todos os personagens em todos os locais (útil para debug ou menu do jogo)
    public void listarTodosPersonagens() {
        for (Map.Entry<String, ArrayList<SerHumano>> entry : locais.entrySet()) {
            System.out.println(ANSI_BLUE + "Local: " + entry.getKey() + ANSI_RESET);
            for (SerHumano s : entry.getValue()) {
                System.out.println(ANSI_PURPLE + "  - " + s.getNome() + ANSI_RESET);
            }
        }
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public Map<String, ArrayList<SerHumano>> getLocais() {
        return locais;
    }

    public void setLocais(Map<String, ArrayList<SerHumano>> locais) {
        this.locais = locais;
    }
}

