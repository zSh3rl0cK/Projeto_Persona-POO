package main.br.inatel.projetojava.Model.relacional;

public class UsuarioHasPersona {

    private int idUsuario;
    private int idPersona;

    public UsuarioHasPersona(int idUsuario, int idPersona) {
        this.idUsuario = idUsuario;
        this.idPersona = idPersona;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }
}

