package main.br.inatel.projetojava.Model.personagens.abstratos;

import main.br.inatel.projetojava.Model.itens.equipaveis.Arma;
import main.br.inatel.projetojava.Model.itens.geral.Itens;
import main.br.inatel.projetojava.Model.itens.auxiliares.Consumiveis;
import main.br.inatel.projetojava.Model.personagens.Inventario;
import main.br.inatel.projetojava.Model.personagens.combate.Combate;
import main.br.inatel.projetojava.Model.personas.Habilidades;
import main.br.inatel.projetojava.Model.personas.seres.Personas;

import java.util.*;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;
import static main.br.inatel.projetojava.Model.sistema.front.Dado.imprimirDado;

public abstract class UsuarioPersona extends SerHumano implements Combate{

    Random random = new Random();

    protected int nivel;
    protected Set<Itens> item = new HashSet<>(); // Agregação
    protected Inventario inventario; // Não faz sentido um inventário existir sem usuário - composição - public pra usar na main.
    protected double defesa = 0;
    protected double sp;
    protected double hp;
    protected Arma arma = new Arma("Punhos", "Físico", 20, "dano físico normal", 30); // instancia inicial de arma

    public UsuarioPersona(String nome, int idade, String genero, int nivel, String arcana, double hp, double sp) {
        super(nome, idade, genero, arcana);
        this.nivel = nivel;
        this.inventario = new Inventario();
        this.hp = hp;
        this.sp = sp;
    }

    public abstract void addPersona(Personas persona);

    public void usarItem(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(ANSI_BLUE + "\nConsumíveis disponíveis:" + ANSI_RESET);
        Map<Consumiveis, Integer> consumiveis = this.inventario.getConsumiveis();

        if (consumiveis.isEmpty()) {
            System.out.println(ANSI_RED + "Nenhum item consumível disponível!" + ANSI_RESET);
            return;
        }

        int i = 1;
        List<Consumiveis> listaConsumivel = new ArrayList<>();
        for (Map.Entry<Consumiveis, Integer> entry : consumiveis.entrySet()) {
            System.out.println(ANSI_BLUE + i + " - " + entry.getKey().getNome() + " (Quantidade: " + entry.getValue() + ")" + ANSI_RESET);
            listaConsumivel.add(entry.getKey());
            i++;
        }

        System.out.println(ANSI_BLUE + "\nEscolha um item para usar (0 para cancelar):" + ANSI_RESET);
        int escolha = scanner.nextInt();

        if (escolha == 0) {
            return;
        }

        if (escolha > 0 && escolha <= listaConsumivel.size()) {
            Consumiveis itemSelecionado = listaConsumivel.get(escolha - 1);

            if (itemSelecionado.getStatus().equalsIgnoreCase("HP")) {
                this.setHp(this.getHp() + itemSelecionado.getValor());
                System.out.println(ANSI_GREEN + "HP recuperado: +" + itemSelecionado.getValor() + ANSI_RESET);
            } else if (itemSelecionado.getStatus().equalsIgnoreCase("SP")) {
                this.setSp(this.getSp() + itemSelecionado.getValor());
                System.out.println(ANSI_GREEN + "SP recuperado: +" + itemSelecionado.getValor() + ANSI_RESET);
            }

            this.inventario.removerConsumivel(itemSelecionado);
            System.out.println(ANSI_GREEN + "Item " + itemSelecionado.getNome() + " usado com sucesso!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "Opção inválida!" + ANSI_RESET);
        }
    }

    // Como a evolução será igual para protagonista e/ou usuário, não precisa ser abstract.
    public void evoluirPersona(Personas persona){
        persona.setNivel(persona.getNivel() + 1);
    }

    public void setArcana(String Arcana) {
        this.arcana = Arcana;
    }

    // Implementação da 'interface' de Combate

    @Override
    public void atacar(Personas persona, UsuarioPersona alvo) {
        if (alvo == null || persona == null) {
            System.out.println(ANSI_RED + "Ataque inválido! Persona ou alvo nulo." + ANSI_RESET);
            return;
        }

        System.out.println(ANSI_BLUE + "\nEscolha o tipo de ataque:");
        System.out.println("1 - Ataque Físico");
        System.out.println("2 - Usar Habilidade da Persona" + ANSI_RESET);

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
                        System.out.println(ANSI_BLUE + (i + 1) + " - " + hab.getNome() + " (Dano: " + (hab.getDano() - alvo.getDefesa()) + ", SP: 10)" + ANSI_RESET);
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
                    double danoFinal = Math.max(0, hab.getDano() - alvo.getDefesa());
                    alvo.setHp(alvo.getHp() - danoFinal);
                    System.out.println(ANSI_BLUE + nome + " usou " + hab.getNome() + " causando " + danoFinal + " de dano!");
                    System.out.println("SP restante: " + sp + ANSI_RESET);
                }
            }
            default -> System.out.println(ANSI_RED + "Opção inválida!" + ANSI_RESET);
        }

        defesa = 0; // Reseta defesa no fim do turno
    }

    @Override
    public void defender() {
        System.out.println(ANSI_BLUE + nome + " está se defendendo e receberá menos dano no próximo ataque." + ANSI_RESET);
        this.defesa = 10; // pode ser adaptado conforme a lógica de buffs futuros
    }

    @Override
    public boolean agir(int turno, Personas persona, UsuarioPersona alvo) {
        System.out.println(ANSI_PURPLE + "\nTurno " + turno + " de " + nome);
        System.out.println("Escolha sua ação: ");
        System.out.println("1 - Atacar");
        System.out.println("2 - Defender");
        System.out.println("3 - Usar Item");
        System.out.println("4 - Fugir" + ANSI_RESET);

        Scanner scanner = new Scanner(System.in);
        int opcao;

        // Loop para garantir que a entrada seja um inteiro válido entre 1 e 4
        while (true) {
            try {
                opcao = scanner.nextInt();
                if (opcao >= 1 && opcao <= 4) {
                    break; // Sai do loop se o número é válido
                } else {
                    System.out.println(ANSI_RED + "Opção inválida! Por favor, escolha entre 1 e 4." + ANSI_RESET);
                }
            } catch (InputMismatchException e) {
                System.out.println(ANSI_RED + "Por favor, digite um número válido!" + ANSI_RESET);
                scanner.next(); // Limpa a entrada inválida
            }
        }

        switch (opcao) {
            case 1:
                atacar(persona, alvo);
                break;
            case 2:
                defender();
                break;
            case 3:
                usarItem();
                break;
            case 4:
                System.out.println(ANSI_YELLOW + "Saindo..." + ANSI_RESET);
                return false;
        }
        return true;
    }

    @Override
    public boolean agirAutomatico(int turno, Personas persona, UsuarioPersona alvo, boolean usarHabilidade) {
        System.out.println(ANSI_CYAN + "\nTurno de " + this.getNome() + ANSI_RESET);

        // Decisão aleatória entre ataque físico e habilidade (60% físico, 40% habilidade)
        boolean decidiuUsarHabilidade = usarHabilidade && random.nextDouble() < 0.4
                && !persona.getHabilidades().isEmpty()
                && this.sp > 0;

        if (decidiuUsarHabilidade) {
            List<Habilidades> habilidades = persona.getHabilidades();
            Habilidades habilidade = habilidades.get(random.nextInt(habilidades.size()));

            // Custo baseado no dano da habilidade
            double custoSP = Math.max(5, habilidade.getDano() * 0.1);

            if (this.sp >= custoSP) {
                this.sp -= custoSP;
                usarHabilidade(persona, alvo, habilidade, custoSP);
            } else {
                System.out.println(ANSI_YELLOW + this.getNome() + " tentou usar " + habilidade.getNome() +
                        " mas não tem SP suficiente!" + ANSI_RESET);
                atacarAutomatico(persona, alvo); // ataques automáticos
            }
        } else {
            atacarAutomatico(persona, alvo); // ataques automáticos
        }

        return alvo.getHp() > 0;
    }

    //ataques automáticos (sem interação do usuário)
    private void atacarAutomatico(Personas persona, UsuarioPersona alvo) {
        double danoFinal = Math.max(0, this.arma.getDano() - alvo.getDefesa());
        int missHit = random.nextInt(6) + 1;

        System.out.println(imprimirDado(missHit));

        if(missHit == 1) {
            System.out.println(ANSI_RED + "Missed! " + this.getNome() + " errou o ataque!" + ANSI_RESET);
        }
        else if (missHit == 6) {
            danoFinal *= 1.5;
            alvo.setHp(alvo.getHp() - danoFinal);
            System.out.println(ANSI_YELLOW + "Crítico! " + ANSI_RESET);
            System.out.println(ANSI_BLUE + this.getNome() + " realizou um ataque físico *crítico* causando " +
                    danoFinal + " de dano em " + alvo.getNome() + ANSI_RESET);
        }
        else {
            alvo.setHp(alvo.getHp() - danoFinal);
            System.out.println(ANSI_BLUE + this.getNome() + " realizou um ataque físico causando " +
                    danoFinal + " de dano em " + alvo.getNome() + ANSI_RESET);
        }

        defesa = 0; // Reseta defesa no fim do turno
    }

    private void usarHabilidade(Personas persona, UsuarioPersona alvo, Habilidades habilidade, double custoSP) {
        double danoBase = habilidade.getDano();
        double danoFinal = Math.max(1, danoBase - alvo.getDefesa());

        // Sistema de acerto (1-6)
        int dado = random.nextInt(6) + 1;
        System.out.println(imprimirDado(dado));

        if (dado == 1) {
            System.out.println(ANSI_RED + "Errou! " + this.getNome() + " falhou ao usar " +
                    habilidade.getNome() + ANSI_RESET);
            return;
        }

        // Crítico
        if (dado == 6) {
            danoFinal *= 1.5;
            System.out.println(ANSI_YELLOW + "Golpe crítico! " + ANSI_RESET);
        }

        alvo.setHp(alvo.getHp() - danoFinal);

        System.out.println(ANSI_PURPLE + this.getNome() + " usou " + habilidade.getNome() +
                " (Custo: " + custoSP + " SP)" + ANSI_RESET);
        System.out.println(ANSI_RED + alvo.getNome() + " sofreu " + danoFinal +
                " de dano!" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "SP restante: " + this.sp + ANSI_RESET);
    }


    public double getSp() {
        return sp;
    }

    public void setSp(double sp) {
        this.sp = sp;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = Math.max(0, hp); // Garante que não fique negativo
    }

    public Set<Itens> getItem() {
        return item;
    }

    public void setItens(Set<Itens> item) {
        this.item = item;
    }

    public void setItem(Itens item) {
        this.item.add(item);
    }

    public void setArma(Arma arma) {
        this.arma = arma;
    }
    public Arma getArma() {
        return arma;
    }

    public double getDefesa(){
        return this.defesa;
    }

    public void setDefesa(double defesa) {
        this.defesa = defesa;
    }

    public Inventario getInventario() {
        return inventario;
    }
}