package main.br.inatel.projetojava;

import main.br.inatel.projetojava.Model.sistema.front.TelaInicial;

import java.util.concurrent.CountDownLatch;

import static main.br.inatel.projetojava.Model.sistema.menus.MenuThreads.inicioThreads;
import static main.br.inatel.projetojava.Model.sistema.menus.MenuGeral.mostrarMenuGeral;

public class Main {

    public static void main(String[] args) {

        // -------------------------- Tela Inicial com controle -------------------------- //

        CountDownLatch latch = new CountDownLatch(1);
        TelaInicial.exibirTela(latch);

        try {
            latch.await(); // Espera até que o botão "Iniciar" seja pressionado
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // -------------------------- Execução principal -------------------------- //

        inicioThreads();
        mostrarMenuGeral(); // Começa o menu no terminal automaticamente
    }
}
