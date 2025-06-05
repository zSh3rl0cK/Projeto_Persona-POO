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

import java.util.*;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class Protagonista extends UsuarioPersona {

    public List<Personas> personas; // O protagonista pode ter mais de uma persona.
    private double saldo;
    private int id;
    public Personas persona_atual;
    public Ativador ativador;

    public Protagonista(String nome, int idade, String genero, int nivel, String arcana, double hp, double sp, double saldo, int Ativador_idAtivador) {
        super(nome, idade, genero, nivel, arcana, hp, sp);
        this.personas = new ArrayList<>(); // Composição
        this.saldo = saldo;
        this.inventario = new Inventario();
        this.ativador = new Ativador(Ativador_idAtivador);
    }

    // Sobrecarga de construtor para busca de protagonista com ID em SQL
    public Protagonista(String nome, int idade, String genero, int nivel, String arcana, double hp, double sp, double saldo, int Ativador_idAtivador, int id) {
        super(nome, idade, genero, nivel, arcana, hp, sp);
        this.personas = new ArrayList<>(); // Composição
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
        System.out.println();
        System.out.println(ANSI_RESET);
        System.out.println(ANSI_PURPLE + "Personas do Protagonista: " + ANSI_RESET);
        /*for(Personas persona : personas){
            if(persona != null){
                persona.mostrarStatusPersona();
                System.out.println();
            }
        }*/
        System.out.print(ANSI_RESET);
    }

    public void darItemUsuario(Usuarios usuario, Itens novo_item){
        if(usuario.getItem() == null){
            usuario.setItens(new HashSet<>());
        }
        switch(novo_item){
            case Arma arma -> {
                if(this.inventario.getQuantidadeArma(arma) > 0){
                    usuario.inventario.adicionarArma((Arma)novo_item, 1);
                    this.inventario.removerArma(novo_item);
                    System.out.println(ANSI_BLUE + "Arma " + novo_item.getNome() + " transferido para " + usuario.getNome() + "\n" + ANSI_RESET);
                }
                else{
                    System.out.println(ANSI_RED + "Protagonista não possui essa arma. Tente novamente." + ANSI_RESET);
                }
            }
            case Equipamento equipamento -> {
                if(this.inventario.getQuantidadeEquipamento(equipamento) > 0){
                    usuario.inventario.adicionarEquipamento((Equipamento)novo_item, 1);
                    this.inventario.removerEquipamento(novo_item);
                    System.out.println(ANSI_BLUE + "Equipamento " + novo_item.getNome() + " transferido para " + usuario.getNome() + "\n" + ANSI_RESET);
                }
                else{
                    System.out.println(ANSI_RED + "Protagonista não possui esse equipamento. Tente novamente." + ANSI_RESET);
                }
            }
            case Consumiveis consumivel -> {
                if (this.inventario.getQuantidadeConsumivel(consumivel) > 0) {
                    usuario.inventario.adicionarConsumivel((Consumiveis)novo_item, 1);
                    this.inventario.removerConsumivel(novo_item);
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
        int opcao;

       while(true) {
           System.out.println(ANSI_BLUE + "Suas personas são: \n" + ANSI_RESET);
           int i = 0;
           for (Personas persona : personas) {
               System.out.println(ANSI_BLUE + (i+1) + " " + persona.getNome() + ANSI_RESET);
               i++;
           }
           System.out.println(ANSI_BLUE + "Digite o indice da persona para qual quer trocar: " + ANSI_RESET);
           opcao = entrada.nextInt();

           if(opcao > 0 && opcao < personas.size()){
               persona_atual = personas.get(opcao - 1);
               break;
           }
           else{
               System.out.println(ANSI_RED + "Escolha invalida" + ANSI_RESET);
           }
       }
    }

    /* Fazer se der tempo apenas
    public void fundirPersona(Personas p1, Personas p2){

    }
    */

    @Override
    public void atacar(Personas persona, UsuarioPersona alvo) throws InvalidMenuInputException {
        if (alvo == null || persona == null) {
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
                escolhaTipo = scanner.nextInt(); // todo: fazer try catch aqui
                if(escolhaTipo > 0 && escolhaTipo <= 3){
                    break;
                }
            }catch(InputMismatchException e){
                System.out.println("Erro: " + e.getMessage());
            }
        }

        switch (escolhaTipo) {
            case 1 -> {
                double danoFinal = Math.max(0, this.arma.getDano() - alvo.getDefesa());
                alvo.setHp(alvo.getHp() - danoFinal);
                System.out.println(ANSI_BLUE + nome + " realizou um ataque físico causando " + danoFinal + " de dano em " + alvo.getNome() + ANSI_RESET);
            }
            case 2 -> {
                List<Habilidades> habilidades = persona.getHabilidades();
                if (habilidades.isEmpty()) {
                    System.out.println(ANSI_RED + "Essa persona não possui habilidades!" + ANSI_RESET);
                    return;
                }

                System.out.println(ANSI_BLUE + "Escolha a habilidade:" + ANSI_RESET);
                for (int i = 0; i < habilidades.size(); i++) {
                    Habilidades hab = habilidades.get(i);
                    System.out.println(ANSI_BLUE + (i + 1) + " - " + hab.getNome() + " (Dano: " + (hab.getDano()-alvo.getDefesa()) + ", SP: 10)" + ANSI_RESET);
                }

                int escolhaHab = scanner.nextInt() - 1;
                if (escolhaHab < 0 || escolhaHab >= habilidades.size()) {
                    System.out.println(ANSI_RED + "Habilidade inválida!" + ANSI_RESET);
                    return;
                }

                Habilidades hab = habilidades.get(escolhaHab);
                double custoSP = 10;

                if (sp < custoSP) {
                    System.out.println(ANSI_RED + "SP insuficiente!" + ANSI_RESET);
                    return;
                }

                sp -= custoSP;
                double danoFinal = Math.max(0, hab.getDano() - alvo.getDefesa());
                alvo.setHp(alvo.getHp() - danoFinal);
                System.out.println(ANSI_BLUE + nome + " usou " + hab.getNome() + " causando " + danoFinal + " de dano!");
                System.out.println("SP restante: " + sp + ANSI_RESET);
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
}