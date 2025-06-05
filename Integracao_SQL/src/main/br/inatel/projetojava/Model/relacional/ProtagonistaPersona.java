package main.br.inatel.projetojava.Model.relacional;

public class ProtagonistaPersona {

    private int idProtagonista;
    private int idPersona;

    public ProtagonistaPersona(int idProtagonista, int idPersona) {
        this.idProtagonista = idProtagonista;
        this.idPersona = idPersona;
    }

    public int getIdProtagonista() {
        return idProtagonista;
    }


    public int getIdPersona() {
        return idPersona;
    }

}
