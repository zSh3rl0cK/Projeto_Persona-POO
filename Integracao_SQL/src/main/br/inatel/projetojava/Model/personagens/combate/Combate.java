package main.br.inatel.projetojava.Model.personagens.combate;

import main.br.inatel.projetojava.Model.personagens.abstratos.UsuarioPersona;
import main.br.inatel.projetojava.Model.personas.seres.Personas;

public interface Combate {

    void atacar(Personas persona, UsuarioPersona alvo);

    void defender();

    boolean agir(int turno, Personas persona, UsuarioPersona alvo);

    // Ações automáticas (usado pelos inimigos)
    boolean agirAutomatico(int turno, Personas persona, UsuarioPersona alvo, boolean usarHabilidade);
}