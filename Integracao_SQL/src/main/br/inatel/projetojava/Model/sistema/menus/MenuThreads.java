package main.br.inatel.projetojava.Model.sistema.menus;

import main.br.inatel.projetojava.Model.threads.Clock;
import main.br.inatel.projetojava.Model.threads.LoadingBar;
import static main.br.inatel.projetojava.Model.sistema.front.Cores.ANSI_RED;
import static main.br.inatel.projetojava.Model.sistema.front.Cores.ANSI_RESET;

public class MenuThreads {
    public static void inicioThreads(){

        LoadingBar loading = new LoadingBar(30, 100);
        Thread loadingThread = new Thread(loading);
        loadingThread.start();

        try {
            loadingThread.join();
        } catch (InterruptedException e) {
            System.out.println(ANSI_RED + "Thread da Main interrompida." + ANSI_RESET);
        }

        Clock clock = new Clock();
        Thread clockThread = new Thread(clock);

        clockThread.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println(ANSI_RED + "Thread do relógio interrompido." + ANSI_RESET);
        }

        clockThread.interrupt();
    }
}
