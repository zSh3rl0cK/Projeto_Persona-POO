package main.br.inatel.projetojava.Model.sistema;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Creditos extends JFrame {
    private JPanel painelPrincipal;
    private ArrayList<String> creditos;
    private int creditoAtual = 0;
    private Timer timer;
    private final Color corFundo = new Color(26, 26, 46);
    private final Color corTexto = new Color(0, 255, 255);

    public Creditos() {
        inicializarCreditos();
        configurarJanela();
        iniciarAnimacao();
    }

    private void inicializarCreditos() {
        creditos = new ArrayList<>();
        creditos.add("JOGO BASEADO EM PERSONA 3");
        creditos.add("");
        creditos.add("DESENVOLVIDO POR:");
        creditos.add("Felipe Tagawa Reis & Pedro Henrique Ribeiro Dias");
        creditos.add("");
        creditos.add("ORIENTAÇÃO:");
        creditos.add("CHRISTOPHER");
        creditos.add("");
        creditos.add("OBRIGADO POR JOGAR!");
    }

    private void configurarJanela() {
        setTitle("Créditos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        painelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fundo gradiente
                GradientPaint gradient = new GradientPaint(
                        0, 0, corFundo,
                        getWidth(), getHeight(), new Color(15, 52, 96)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Efeito de brilho no centro
                RadialGradientPaint brilho = new RadialGradientPaint(
                        (float) getWidth() /2, (float) getHeight() /2, 300,
                        new float[]{0.0f, 1.0f},
                        new Color[]{new Color(255, 255, 255, 30), new Color(255, 255, 255, 0)}
                );
                g2d.setPaint(brilho);
                g2d.fillOval(getWidth()/2 - 300, getHeight()/2 - 300, 600, 600);
            }
        };

        painelPrincipal.setLayout(new BorderLayout());
        add(painelPrincipal);
    }

    private void iniciarAnimacao() {
        timer = new Timer(2000, _ -> mostrarProximoCredito());

        mostrarProximoCredito();
        timer.start();
    }

    private void mostrarProximoCredito() {
        painelPrincipal.removeAll();

        if (creditoAtual < creditos.size()) {
            String textoCredito = creditos.get(creditoAtual);

            JLabel labelCredito = new JLabel(textoCredito, SwingConstants.CENTER);

            if (creditoAtual == 0) {
                // Título principal
                labelCredito.setFont(new Font("Arial", Font.BOLD, 48));
                labelCredito.setForeground(corTexto);
            } else if (textoCredito.contains("DESENVOLVIDO") || textoCredito.contains("ORIENTAÇÃO") ||
                    textoCredito.contains("TECNOLOGIAS") || textoCredito.contains("AGRADECIMENTOS")) {
                // Seções
                labelCredito.setFont(new Font("Arial", Font.BOLD, 28));
                labelCredito.setForeground(new Color(255, 215, 0));
            } else if (textoCredito.equals("OBRIGADO POR JOGAR!")) {
                // Agradecimento final - CORREÇÃO: era "OBRIGADO POR ASSISTIR!"
                labelCredito.setFont(new Font("Arial", Font.BOLD, 36));
                labelCredito.setForeground(new Color(255, 100, 100));
            } else if (textoCredito.equals("FIM")) {
                // Fim
                labelCredito.setFont(new Font("Arial", Font.BOLD, 42));
                labelCredito.setForeground(corTexto);
            } else if (!textoCredito.isEmpty()) {
                // Texto normal
                labelCredito.setFont(new Font("Arial", Font.PLAIN, 24));
                labelCredito.setForeground(Color.WHITE);
            }

            painelPrincipal.add(labelCredito, BorderLayout.CENTER);
            creditoAtual++;
        } else {
            // Finalizar créditos
            timer.stop();
            JLabel finalLabel = new JLabel("Clique para fechar", SwingConstants.CENTER);
            finalLabel.setFont(new Font("Arial", Font.ITALIC, 18));
            finalLabel.setForeground(Color.LIGHT_GRAY);
            painelPrincipal.add(finalLabel, BorderLayout.SOUTH);

            Timer closeTimer = new Timer(5000, _ -> dispose());
            closeTimer.setRepeats(false);
            closeTimer.start();
        }

        painelPrincipal.revalidate();
        painelPrincipal.repaint();
    }
}