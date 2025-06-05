package main.br.inatel.projetojava.Model.sistema.front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class TelaInicial extends JFrame {

    public TelaInicial() {
        setTitle("Persona 3 - SEES Terminal");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel com gradiente
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(0, 0, 40);
                Color color2 = new Color(0, 180, 255);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Título
        JLabel titulo = new JLabel("JOGO PERSONA 3", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);
        panel.add(titulo, BorderLayout.NORTH);

        // Painel central para imagem + botão
        JPanel centro = new JPanel();
        centro.setOpaque(false);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));

        // Carregando e exibindo a imagem:
        ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/persona.jpeg")));
        Image scaledImage = originalIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imagemLabel = new JLabel(scaledIcon);
        imagemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centro.add(imagemLabel);

        centro.add(Box.createVerticalStrut(20)); // espaço entre imagem e botão

        // Botão Start
        JButton botaoStart = new JButton("Iniciar");
        botaoStart.setFont(new Font("Monospaced", Font.BOLD, 18));
        botaoStart.setBackground(Color.BLACK);
        botaoStart.setForeground(Color.CYAN);
        botaoStart.setFocusPainted(false);
        botaoStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoStart.addActionListener((ActionEvent e) -> {
            dispose(); // fecha a tela
            // continue com o que precisar chamar depois
        });

        centro.add(botaoStart);
        panel.add(centro, BorderLayout.CENTER);

        add(panel);
    }

    public static void exibirTela() {
        SwingUtilities.invokeLater(() -> {
            TelaInicial tela = new TelaInicial();
            tela.setVisible(true);
        });
    }
}
