package main.br.inatel.projetojava.Model.personagens.combate;

import main.br.inatel.projetojava.Model.personagens.abstratos.UsuarioPersona;
import main.br.inatel.projetojava.Model.personas.seres.Personas;

public interface CombateInimigo {
    void defender();

    // Ações automáticas (usado pelos inimigos)
    boolean agirAutomatico(int turno, Personas persona, UsuarioPersona alvo, boolean usarHabilidade);
}
