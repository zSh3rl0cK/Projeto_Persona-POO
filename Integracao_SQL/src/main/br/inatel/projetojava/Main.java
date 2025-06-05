package main.br.inatel.projetojava;

import static main.br.inatel.projetojava.Model.sistema.menus.MenuThreads.inicioThreads;
import static main.br.inatel.projetojava.Model.sistema.menus.MenuGeral.mostrarMenuGeral;
import static main.br.inatel.projetojava.Model.sistema.front.TelaInicial.exibirTela;

public class Main {

    public static void main(String[] args) {

        // --------------------------------- Tela Inicial + Threads --------------------------------- //

        exibirTela();
        inicioThreads();

        // ------------------------------------------ Menu: ----------------------------------------- //

        mostrarMenuGeral();
    }
}