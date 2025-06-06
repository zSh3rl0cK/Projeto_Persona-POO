package main.br.inatel.projetojava.Model.relacional;

public class ProtagonistaHasPersona {

    private int idProtagonista;
    private int idPersona;

    public ProtagonistaHasPersona(int idProtagonista, int idPersona) {
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
