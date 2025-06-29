package main.br.inatel.projetojava.Model.personas.seres;

import main.br.inatel.projetojava.Model.exceptions.InvalidMenuInputException;
import main.br.inatel.projetojava.Model.personagens.abstratos.UsuarioPersona;
import main.br.inatel.projetojava.Model.personagens.combate.Combate;
import main.br.inatel.projetojava.Model.personagens.combate.CombateInimigo;
import main.br.inatel.projetojava.Model.personas.Habilidades;
import java.util.List;
import java.util.Random;
import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class Shadow extends Personas implements CombateInimigo {
    private int hp;
    private double sp;
    private double defesa;
    Random random = new Random();

    public Shadow(String nome, int hp, int nivel, String arcana, List<String> tipo, String fraqueza, String resistencia, int dano) {
        super(nome, nivel, arcana, tipo, fraqueza, resistencia, dano);
        this.hp = hp;
        this.sp = 50; // SP padrão para Shadows
        this.defesa = 5; // Defesa base para Shadows
    }

    @Override
    public void mostrarStatusPersona(){
        super.mostrarStatusPersona();
    }

    public String toString() {
        return String.format(ANSI_PURPLE + "\nNome: %s\nVida: %s\nNível: %d\nArcana: %s\nFraqueza: %s\nResistência: %s\nDano: %s" + ANSI_RESET,
                this.getNome(),
                this.getHp(),
                this.getNivel(),
                this.getArcana(),
                this.getFraqueza(),
                this.getResistencia(),
                this.getDano());
    }

    private void ataqueCorpoACorpo(UsuarioPersona alvo) {
        double danoFinal = Math.max(0, this.getDano() - alvo.getDefesa());
        alvo.setHp(alvo.getHp() - danoFinal);
        System.out.println(ANSI_PURPLE + this.getNome() + " realizou um ataque sombrio causando " + danoFinal + " de dano em " + alvo.getNome() + "!" + ANSI_RESET);
    }

    private void usarHabilidadeSombria(UsuarioPersona alvo) {
        List<Habilidades> habilidades = this.getHabilidades();
        if (habilidades == null || habilidades.isEmpty()) {
            ataqueCorpoACorpo(alvo);
            return;
        }

        // Escolhe uma habilidade aleatória
        Habilidades hab = habilidades.get(random.nextInt(habilidades.size()));
        double custoSP = 10;

        if (sp < custoSP) {
            System.out.println(ANSI_PURPLE + this.getNome() + " não tem SP suficiente, usa ataque físico!" + ANSI_RESET);
            ataqueCorpoACorpo(alvo);
            return;
        }

        sp -= custoSP;
        double danoFinal = Math.max(0, hab.dano() - alvo.getDefesa());
        alvo.setHp(alvo.getHp() - danoFinal);
        System.out.println(ANSI_PURPLE + this.getNome() + " usou a habilidade sombria '" + hab.nome() + "' causando " + danoFinal + " de dano em " + alvo.getNome() + "!" + ANSI_RESET);
    }

    @Override
    public void defender() {
        this.defesa += 10; // Aumenta defesa temporariamente
        System.out.println(ANSI_PURPLE + this.getNome() + " assumiu posição defensiva!" + ANSI_RESET);
    }


    @Override
    public boolean agirAutomatico(int turno, Personas persona, UsuarioPersona alvo, boolean usarHabilidade) {
        if (alvo == null) {
            System.out.println(ANSI_RED + "Erro: Alvo nulo para Shadow!" + ANSI_RESET);
            return false;
        }

        if (this.hp <= 0) {
            System.out.println(ANSI_PURPLE + this.getNome() + " está derrotado e não pode agir." + ANSI_RESET);
            return true;
        }

        try {
            if (usarHabilidade && this.getHabilidades() != null && !this.getHabilidades().isEmpty()) {
                usarHabilidadeSombria(alvo);
            } else {
                ataqueCorpoACorpo(alvo);
            }

            // Pequena chance de defender no próximo turno
            if (random.nextDouble() < 0.2) { // 20% de chance
                defender();
            }

            return true;
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Erro durante ação automática do Shadow: " + e.getMessage() + ANSI_RESET);
            return false;
        }
    }

    // Getters e Setters específicos para combate
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, hp); // HP não pode ser negativo
    }

    public double getSp() {
        return sp;
    }

    public void setSp(double sp) {
        this.sp = Math.max(0, sp);
    }

    public double getDefesa() {
        return defesa;
    }

    public void setDefesa(double defesa) {
        this.defesa = defesa;
    }

    // Méto.do para verificar se o Shadow está vivo
    public boolean estaVivo() {
        return hp > 0;
    }

    // Méto.do para curar o Shadow (se necessário)
    public void curar(double quantidade) {
        this.hp += quantidade;
        System.out.println(ANSI_PURPLE + this.getNome() + " recuperou " + quantidade + " de HP!" + ANSI_RESET);
    }

    // Méto.do para recuperar SP
    public void recuperarSP(double quantidade) {
        this.sp += quantidade;
        System.out.println(ANSI_PURPLE + this.getNome() + " recuperou " + quantidade + " de SP!" + ANSI_RESET);
    }


}