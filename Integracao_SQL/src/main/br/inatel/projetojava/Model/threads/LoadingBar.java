package main.br.inatel.projetojava.Model.threads;

import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class LoadingBar implements Runnable {

    private final int totalSteps;
    private final int delay;

    public LoadingBar(int totalSteps, int delay) {
        this.totalSteps = totalSteps;
        this.delay = delay;
    }

    @Override
    public void run() {
        System.out.print(ANSI_GRAY + "Carregando: [" + ANSI_RESET);
        for (int i = 0; i < totalSteps; i++) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                System.out.println(ANSI_RED + "Loading interrompido." + ANSI_RESET);
                return;
            }
            System.out.print(ANSI_GRAY + "#" + ANSI_RESET);
        }
        System.out.println(ANSI_GRAY + "] Carregado!" + ANSI_RESET);
    }
}
