package main.br.inatel.projetojava.Model.personagens.jogaveis;

import main.br.inatel.projetojava.Model.exceptions.InvalidMenuInputException;
import main.br.inatel.projetojava.Model.itens.auxiliares.Ativador;
import main.br.inatel.projetojava.Model.itens.geral.Itens;
import main.br.inatel.projetojava.Model.itens.equipaveis.Arma;
import main.br.inatel.projetojava.Model.itens.auxiliares.Consumiveis;
import main.br.inatel.projetojava.Model.itens.equipaveis.Equipamento;
import main.br.inatel.projetojava.Model.personagens.Inventario;
import main.br.inatel.projetojava.Model.personagens.abstratos.UsuarioPersona;
import main.br.inatel.projetojava.Model.personas.Habilidades;
import main.br.inatel.projetojava.Model.personas.seres.Personas;
import main.br.inatel.projetojava.Model.personas.seres.Shadow;

import java.util.*;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;
import static main.br.inatel.projetojava.Model.sistema.front.Dado.imprimirDado;

public class Protagonista extends UsuarioPersona {

    private List<Personas> personas;  // O protagonista pode ter mais de uma persona
    private double saldo;             // Útil para compras
    private int id;                   // Identificador básico
    private final Ativador ativador;  // Ativador Persona
    private Personas persona_atual;   // Apenas uma e específica para combate

    public Protagonista(String nome, int idade, String genero, int nivel, String arcana, double hp, double sp, double saldo, int Ativador_idAtivador) {
        super(nome, idade, genero, nivel, arcana, hp, sp);
        this.personas = new ArrayList<>(); // Ordem necessária
        this.ativador = new Ativador(Ativador_idAtivador);
        this.saldo = saldo;
    }

    // Sobrecarga de construtor para busca de protagonista com ID em SQL
    public Protagonista(String nome, int idade, String genero, int nivel, String arcana, double hp, double sp, double saldo, int Ativador_idAtivador, int id) {
        super(nome, idade, genero, nivel, arcana, hp, sp);
        this.personas = new ArrayList<>();
        this.saldo = saldo;
        this.inventario = new Inventario();
        this.ativador = new Ativador(Ativador_idAtivador);
        this.id = id;
    }

    @Override
    public void addPersona(Personas persona) {
        this.personas.add(persona);
    }

    @Override
    public void mostraInfoPersonagem() {
        System.out.println(ANSI_CYAN + "Nome do Protagonista: " + nome + ANSI_RESET);
        System.out.println(ANSI_BLUE + "Idade: " + idade);
        System.out.println("Gênero: " + genero);
        System.out.println("Nível: " + nivel);
        System.out.println("Vida: " + hp);
        System.out.println("Mana: " + sp);
        System.out.println("Arcana: " + arcana);
        System.out.println("Saldo: " + saldo);
        System.out.print(ANSI_RESET);
    }

    public void darItemUsuario(Usuarios usuario, Itens novo_item){
        if(usuario.getItem() == null){
            usuario.setItens(new HashSet<>());
        }
        switch(novo_item){
            case Arma armaItem -> {
                if(this.inventario.getQuantidadeArma(armaItem) > 0){
                    usuario.getInventario().adicionarItem((Arma)novo_item, 1);
                    this.inventario.removerItem(novo_item);
                    System.out.println(ANSI_BLUE + "Arma " + novo_item.getNome() + " transferido para " + usuario.getNome() + "\n" + ANSI_RESET);
                }
                else{
                    System.out.println(ANSI_RED + "Protagonista não possui essa arma. Tente novamente." + ANSI_RESET);
                }
            }
            case Equipamento equipamento -> {
                if(this.inventario.getQuantidadeEquipamento(equipamento) > 0){
                    usuario.getInventario().adicionarItem((Equipamento)novo_item, 1);
                    this.inventario.removerItem(novo_item);
                    System.out.println(ANSI_BLUE + "Equipamento " + novo_item.getNome() + " transferido para " + usuario.getNome() + "\n" + ANSI_RESET);
                }
                else{
                    System.out.println(ANSI_RED + "Protagonista não possui esse equipamento. Tente novamente." + ANSI_RESET);
                }
            }
            case Consumiveis consumivel -> {
                if (this.inventario.getQuantidadeConsumivel(consumivel) > 0) {
                    usuario.getInventario().adicionarItem((Consumiveis)novo_item, 1);
                    this.inventario.removerItem(novo_item);
                    System.out.println(ANSI_BLUE + "Colecionavel " + novo_item.getNome() + " transferido para " + usuario.getNome() + "\n" + ANSI_RESET);
                }
                else{
                    System.out.println(ANSI_RED + "Protagonista não possui esse consumível. Tente novamente." + ANSI_RESET);
                }
            }
            default -> System.out.println(ANSI_RED + "Item não está entre os tipos! Tente novamente." + ANSI_RESET);
        }
    }

    public void trocarPersona(){
        Scanner entrada = new Scanner(System.in);
        String opcao;

        while(true) {
            System.out.println(ANSI_BLUE + "Suas personas são: \n" + ANSI_RESET);
            int i = 0;
            for (Personas persona : personas) {
                System.out.println(ANSI_BLUE + (i+1) + " - " + persona.getNome() + ANSI_RESET);
                i++;
            }
            System.out.println(ANSI_BLUE + "Digite o índice ou nome da persona para qual quer trocar: " + ANSI_RESET);
            opcao = entrada.nextLine().trim();

            // Primeiro tenta por índice
            try {
                int indice = Integer.parseInt(opcao);

                if(indice > 0 && indice <= personas.size()){
                    persona_atual = personas.get(indice - 1);
                    System.out.println(ANSI_GREEN + "Persona trocada para: " + persona_atual.getNome() + ANSI_RESET);
                    break;
                } else {
                    System.out.println(ANSI_RED + "Índice inválido. Digite um número entre 1 e " + personas.size() + ANSI_RESET);
                }
            } catch (NumberFormatException e) {
                // Se não for número, tenta buscar por nome
                boolean encontrada = false;
                for (Personas persona : personas) {
                    if (persona.getNome().equalsIgnoreCase(opcao)) {
                        persona_atual = persona;
                        System.out.println(ANSI_GREEN + "Persona trocada para: " + persona_atual.getNome() + ANSI_RESET);
                        encontrada = true;
                        break;
                    }
                }

                if (encontrada) {
                    break;
                } else {
                    System.out.println(ANSI_RED + "Persona não encontrada. Digite um índice válido ou nome exato da persona." + ANSI_RESET);
                }
            }
        }
    }

    @Override
    public void atacar(Personas persona_atual, UsuarioPersona alvo) throws InvalidMenuInputException {
        if (alvo == null || persona_atual == null) {
            System.out.println(ANSI_RED + "Ataque inválido! Persona ou alvo nulo." + ANSI_RESET);
            return;
        }

        System.out.println(ANSI_BLUE + "\nEscolha o tipo de ataque:");
        System.out.println("1 - Ataque Físico");
        System.out.println("2 - Usar Habilidade da Persona atual");
        System.out.println("3 - Trocar persona atual" + ANSI_RESET);
        int escolhaTipo;

        Scanner scanner = new Scanner(System.in);
        while(true){
            try {
                escolhaTipo = scanner.nextInt();
                if(escolhaTipo > 0 && escolhaTipo <= 3){
                    break;
                }
                else System.out.println(ANSI_RED + "Escreva um valor válido (1, 2 ou 3) e tente novamente:" + ANSI_RESET);
            }catch(InputMismatchException e){
                System.out.println(ANSI_RED + "Erro: Entrada inválida. Digite um número inteiro (1, 2 ou 3)." + ANSI_RESET);
                scanner.nextLine(); // Limpa o buffer
            }
        }

        switch (escolhaTipo) {
            case 1 -> {
                double danoFinal = Math.max(0, this.arma.getDano() - alvo.getDefesa());
                alvo.setHp(alvo.getHp() - danoFinal);
                System.out.println(ANSI_BLUE + nome + " realizou um ataque físico causando " + danoFinal + " de dano em " + alvo.getNome() + ANSI_RESET);
            }
            case 2 -> {
                List<Habilidades> habilidades = persona_atual.getHabilidades();
                if (habilidades.isEmpty()) {
                    System.out.println(ANSI_RED + "Essa persona não possui habilidades!" + ANSI_RESET);
                    return;
                }

                System.out.println(ANSI_BLUE + "Escolha a habilidade:" + ANSI_RESET);
                for (int i = 0; i < habilidades.size(); i++) {
                    Habilidades hab = habilidades.get(i);
                    System.out.println(ANSI_BLUE + (i + 1) + " - " + hab.nome() + " (Dano: " + (hab.dano()-alvo.getDefesa()) + ", SP: 10)" + ANSI_RESET);
                }

                // Try-catch com while para entrada segura
                int escolhaHab = -1;
                boolean entradaValida = false;

                while (!entradaValida) {
                    try {
                        System.out.print(ANSI_CYAN + "Digite o número da habilidade: " + ANSI_RESET);
                        escolhaHab = scanner.nextInt() - 1;

                        if (escolhaHab >= 0 && escolhaHab < habilidades.size()) {
                            entradaValida = true;
                        } else {
                            System.out.println(ANSI_RED + "Erro: Escolha um número entre 1 e " + habilidades.size() + "!" + ANSI_RESET);
                        }
                    } catch (InputMismatchException e) {
                        System.out.println(ANSI_RED + "Erro: Digite apenas números inteiros!" + ANSI_RESET);
                        scanner.nextLine(); // Limpa o buffer do scanner
                    }
                }

                Habilidades hab = habilidades.get(escolhaHab);
                double custoSP = 10;

                if (sp < custoSP) {
                    System.out.println(ANSI_RED + "SP insuficiente!" + ANSI_RESET);
                    return;
                }

                sp -= custoSP;
                double danoFinal = Math.max(0, hab.dano() - alvo.getDefesa());
                alvo.setHp(alvo.getHp() - danoFinal);
                System.out.println(ANSI_BLUE + nome + " usou " + hab.nome() + " causando " + danoFinal + " de dano!");
                System.out.println("SP restante: " + sp + ANSI_RESET);
            }
            case 3 -> trocarPersona();
            default -> System.out.println(ANSI_RED + "Opção inválida!" + ANSI_RESET);
        }

        defesa = 0; // Reseta defesa no fim do turno
    }

    @Override
    public void atacarShadow(Personas persona, Shadow alvo) throws InvalidMenuInputException {
        Random random = new Random();
        if (alvo == null || persona == null) {
            System.out.println(ANSI_RED + "Ataque inválido! Persona ou alvo nulo." + ANSI_RESET);
            return;
        }

        System.out.println(ANSI_BLUE + "\nEscolha o tipo de ataque:");
        System.out.println("1 - Ataque Físico");
        System.out.println("2 - Usar Habilidade da Persona");
        System.out.println("3 - Trocar persona atual" + ANSI_RESET);

        Scanner scanner = new Scanner(System.in);
        int escolhaTipo;

        // Loop para garantir entrada válida do tipo de ataque (1 ou 2)
        while (true) {
            try {
                escolhaTipo = scanner.nextInt();
                if (escolhaTipo == 1 || escolhaTipo == 2) {
                    break; // Sai do loop se o número é 1 ou 2
                } else {
                    System.out.println(ANSI_RED + "Opção inválida! Digite 1 para Ataque Físico ou 2 para Usar Habilidade da Persona." + ANSI_RESET);
                }
            } catch (InputMismatchException e) {
                System.out.println(ANSI_RED + "Por favor, digite um número válido!" + ANSI_RESET);
                scanner.next(); // Limpa a entrada inválida
            }
        }

        switch (escolhaTipo) {
            case 1 -> {
                double danoFinal = Math.max(0, this.arma.getDano() - alvo.getDefesa()); // todo: alterado danoArma para a arma (apenas observação)
                int missHit = random.nextInt(6) + 1; // (1 a 6)
                System.out.println(imprimirDado(missHit));
                if(missHit == 1) System.out.println("Missed!");
                else if (missHit == 6){
                    danoFinal = danoFinal*1.5;
                    alvo.setHp(alvo.getHp() - (danoFinal)); // Crítico
                    System.out.println(ANSI_BLUE + nome + " realizou um ataque físico *crítico* causando " + danoFinal + " de dano em " + alvo.getNome() + ANSI_RESET);
                }
                else {
                    System.out.println(ANSI_GREEN + "Golpe executado com sucesso!" + ANSI_RESET);
                    alvo.setHp(alvo.getHp() - danoFinal);
                    System.out.println(ANSI_BLUE + nome + " realizou um ataque físico causando " + danoFinal + " de dano em " + alvo.getNome() + ANSI_RESET);
                }
            }
            case 2 -> {
                List<Habilidades> habilidades = persona.getHabilidades();
                if (habilidades.isEmpty()) {
                    System.out.println(ANSI_RED + "Essa persona não possui habilidades!" + ANSI_RESET);
                    return;
                }

                int missHit = random.nextInt(6) + 1;
                System.out.println(imprimirDado(missHit));
                if(missHit == 1) System.out.println("Missed!");
                else {
                    System.out.println(ANSI_GREEN + "Sucesso!" + ANSI_RESET);
                    System.out.println(ANSI_BLUE + "Escolha a habilidade:" + ANSI_RESET);
                    for (int i = 0; i < habilidades.size(); i++) {
                        Habilidades hab = habilidades.get(i);
                        System.out.println(ANSI_BLUE + (i + 1) + " - " + hab.nome() + " (Dano: " + (hab.dano() - alvo.getDefesa()) + ", SP: 10)" + ANSI_RESET);
                    }

                    int escolhaHab;

                    // Loop para garantir entrada válida da habilidade
                    while (true) {
                        try {
                            escolhaHab = scanner.nextInt() - 1;
                            if (escolhaHab >= 0 && escolhaHab < habilidades.size()) {
                                break; // Sai do loop se a escolha for válida
                            } else {
                                System.out.println(ANSI_RED + "Habilidade inválida! Escolha entre 1 e " + habilidades.size() + ANSI_RESET);
                            }
                        } catch (InputMismatchException e) {
                            System.out.println(ANSI_RED + "Por favor, digite um número válido!" + ANSI_RESET);
                            scanner.next(); // Limpa a entrada inválida
                        }
                    }

                    Habilidades hab = habilidades.get(escolhaHab);
                    double custoSP = 10;

                    if (sp < custoSP) {
                        System.out.println(ANSI_RED + "SP insuficiente!" + ANSI_RESET);
                        return;
                    }

                    sp -= custoSP;
                    double danoFinal = Math.max(0, hab.dano() - alvo.getDefesa());
                    alvo.setHp(alvo.getHp() - danoFinal);
                    System.out.println(ANSI_BLUE + nome + " usou " + hab.nome() + " causando " + danoFinal + " de dano!");
                    System.out.println("SP restante: " + sp + ANSI_RESET);
                }
            }
            case 3 -> trocarPersona();
            default -> System.out.println(ANSI_RED + "Opção inválida!" + ANSI_RESET);
        }

        defesa = 0; // Reseta defesa no fim do turno
    }

    // --------------------------------- Getters e setters ---------------------------------

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getNivel() {
        return nivel;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setIdade(int idade){
        this.idade = idade;
    }

    public void setGenero(String genero){
        this.genero = genero;
    }

    public void setHp(double hp){
        this.hp = hp;
    }

    public Ativador getAtivador() {
        return ativador;
    }

    public Personas getPersona_atual() {
        return persona_atual;
    }

    public List<Personas> getPersonas() {
        return personas;
    }

    public void setPersonas(List<Personas> personas) {
        this.personas = personas;
    }

    public void setPersona_atual(Personas persona_atual) {
        this.persona_atual = persona_atual;
    }
}