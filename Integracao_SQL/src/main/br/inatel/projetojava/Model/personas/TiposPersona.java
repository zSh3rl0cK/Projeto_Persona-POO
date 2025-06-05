package main.br.inatel.projetojava.Model.personas;

public class TiposPersona {

    // Tipos dos Personas Protagonistas
    public static final String[][] TIPOS_PERSONA_PROTAGONISTA = {
            {"Almighty", "Dark"},     // Alice
            {"Physical", "Fire"},     // Eligor
            {"Curse", "Physical"},    // Arsène
            {"Fire", "None"},         // Jack-o'-Lantern
            {"Electric", "Healing"},  // Pixie
            {"Dark", "Ailment"},      // Incubus
            {"Dark", "Charm"},        // Succubus
            {"Ice", "Healing"},       // Silky
            {"Fire", "Support"},      // Orobas
            {"Physical", "Dark"}      // Bicorn
    };

    // Tipos dos Personas dos Usuários
    public static final String[][] TIPOS_USUARIOS = {
            {"Wind", "Healing"},      // Yukari - Isis
            {"Ice", "Status"},        // Mitsuru - Artemisia
            {"Fire", "Physical"},     // Junpei - Trismegistus
            {"Electric", "Physical"}, // Akihiko - Caesar
            {"Support", "Analysis"},  // Fuuka - Juno
            {"Physical", "Pierce"},   // Aigis - Athena
            {"Fire", "Dark"},         // Koromaru - Cerberus
            {"Light", "Pierce"},      // Ken - Kala-Nemi
            {"Physical", "None"},     // Shinjiro - Castor
            {"Dark", "Light"},        // Takaya - Hypnos
            {"Support", "Almighty"},  // Jin - Moros
            {"Fire", "Healing"}       // Chidori - Medea
    };

    // Method: buscar tipo por índice - Protagonista
    public static String[] getTipoProtagonistaPorIndice(int indice) {
        if (indice >= 0 && indice < TIPOS_PERSONA_PROTAGONISTA.length) {
            return TIPOS_PERSONA_PROTAGONISTA[indice];
        }
        throw new IllegalArgumentException("Índice inválido: " + indice);
    }

    // Method: buscar tipo por índice - Usuários
    public static String[] getTipoUsuarioPorIndice(int indice) {
        if (indice >= 0 && indice < TIPOS_USUARIOS.length) {
            return TIPOS_USUARIOS[indice];
        }
        throw new IllegalArgumentException("Índice inválido: " + indice);
    }
}