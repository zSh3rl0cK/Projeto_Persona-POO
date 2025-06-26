package main.br.inatel.projetojava.Model.itens.geral;

public abstract class Itens {
    protected String nome;
    protected String tipo;
    protected double valor;
    protected String status;

    public Itens(String nome, String tipo, double valor, String status) {
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
        this.status = status;
    }

    // Setters e Getters:
    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
