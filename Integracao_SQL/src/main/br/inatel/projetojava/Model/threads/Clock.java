package main.br.inatel.projetojava.Model.threads;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static main.br.inatel.projetojava.Model.sistema.front.Cores.*;

public class Clock implements Runnable {
    @Override
    public void run() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        while (true) {
            LocalDateTime agora = LocalDateTime.now();
            String dataHoraFormatada = agora.format(formatter);

            System.out.println(ANSI_GRAY + "Horário: " + dataHoraFormatada + ANSI_RESET);

            try {
                Thread.sleep(1000); // atualiza a cada segundo
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
