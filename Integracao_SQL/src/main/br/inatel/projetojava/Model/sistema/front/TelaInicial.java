package main.br.inatel.projetojava.Model.sistema.front;

import main.br.inatel.projetojava.Model.sistema.Creditos;
import main.br.inatel.projetojava.Model.threads.AudioManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class TelaInicial extends JFrame {

    private final CountDownLatch latch;
    public TelaInicial(CountDownLatch latch) {
        this.latch = latch;
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
        JLabel titulo = new JLabel("TARTARUS", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);
        panel.add(titulo, BorderLayout.NORTH);

        // Painel central para imagem + botões
        JPanel centro = new JPanel();
        centro.setOpaque(false);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));

        // Carregando e exibindo a imagem:
        try {
            ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/persona.jpeg")));
            Image scaledImage = originalIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            JLabel imagemLabel = new JLabel(scaledIcon);
            imagemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            centro.add(imagemLabel);
        } catch (Exception e) {
            // Se não encontrar a imagem, adiciona um placeholder
            JLabel placeholderLabel = new JLabel("[ PERSONA 3 IMAGE ]", SwingConstants.CENTER);
            placeholderLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
            placeholderLabel.setForeground(Color.WHITE);
            placeholderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            centro.add(placeholderLabel);
        }

        centro.add(Box.createVerticalStrut(30)); // espaço entre imagem e botões

        // Painel para os botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setOpaque(false);
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));

        // Botão Iniciar
        JButton botaoStart = new JButton("Iniciar");
        botaoStart.setFont(new Font("Monospaced", Font.BOLD, 18));
        botaoStart.setBackground(Color.BLACK);
        botaoStart.setForeground(Color.CYAN);
        botaoStart.setFocusPainted(false);
        botaoStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoStart.setPreferredSize(new Dimension(150, 40));
        botaoStart.addActionListener((ActionEvent _) -> {
            dispose(); // fecha a tela
            latch.countDown(); // Libera a thread principal
        });

        // Botão Opções
        JButton btnOpcoes = new JButton("Opções");
        btnOpcoes.setFont(new Font("Monospaced", Font.BOLD, 16));
        btnOpcoes.setBackground(new Color(70, 70, 70));
        btnOpcoes.setForeground(Color.WHITE);
        btnOpcoes.setFocusPainted(false);
        btnOpcoes.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnOpcoes.setPreferredSize(new Dimension(150, 35));
        btnOpcoes.addActionListener(_ -> abrirTelaOpcoes());

        // Botão Créditos
        JButton btnCreditos = new JButton("Ver Créditos");
        btnCreditos.setFont(new Font("Monospaced", Font.BOLD, 16));
        btnCreditos.setBackground(new Color(50, 50, 50));
        btnCreditos.setForeground(Color.WHITE);
        btnCreditos.setFocusPainted(false);
        btnCreditos.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCreditos.setPreferredSize(new Dimension(150, 35));
        btnCreditos.addActionListener(_ -> new Creditos().setVisible(true));

        // Botão Sair
        JButton btnSair = new JButton("Sair");
        btnSair.setFont(new Font("Monospaced", Font.BOLD, 16));
        btnSair.setBackground(new Color(80, 20, 20));
        btnSair.setForeground(Color.WHITE);
        btnSair.setFocusPainted(false);
        btnSair.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSair.setPreferredSize(new Dimension(150, 35));
        btnSair.addActionListener(_ -> {
            int resposta = JOptionPane.showConfirmDialog(
                    this,
                    "Tem certeza que deseja sair?",
                    "Confirmar Saída",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (resposta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // Adicionando os botões ao painel na order solicitada
        painelBotoes.add(botaoStart);
        painelBotoes.add(Box.createVerticalStrut(12)); // espaço entre botões
        painelBotoes.add(btnOpcoes);
        painelBotoes.add(Box.createVerticalStrut(12));
        painelBotoes.add(btnCreditos);
        painelBotoes.add(Box.createVerticalStrut(12));
        painelBotoes.add(btnSair);

        centro.add(painelBotoes);
        panel.add(centro, BorderLayout.CENTER);

        add(panel);
    }

    private void abrirTelaOpcoes() {
        // Cria uma janela para opções
        JDialog opcoes = new JDialog(this, "Opções de Áudio", true);
        opcoes.setSize(400, 300);
        opcoes.setLocationRelativeTo(this);
        opcoes.setResizable(false);

        // Painel principal com gradiente
        JPanel painelOpcoes = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(20, 20, 60);
                Color color2 = new Color(0, 100, 180);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título
        JLabel tituloOpcoes = new JLabel("CONFIGURAÇÕES DE ÁUDIO");
        tituloOpcoes.setFont(new Font("Serif", Font.BOLD, 20));
        tituloOpcoes.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        painelOpcoes.add(tituloOpcoes, gbc);
        gbc.gridwidth = 1;

        // AudioManager instance
        AudioManager audioManager = AudioManager.getInstance();

        // Volume da Música
        JLabel labelMusica = new JLabel("Volume da Música:");
        labelMusica.setForeground(Color.WHITE);
        labelMusica.setFont(new Font("Monospaced", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        painelOpcoes.add(labelMusica, gbc);

        JSlider sliderMusica = new JSlider(0, 100, (int)(audioManager.getMusicVolume() * 100));
        sliderMusica.setOpaque(false);
        sliderMusica.setForeground(Color.WHITE);
        sliderMusica.addChangeListener(_ -> {
            float volume = sliderMusica.getValue() / 100.0f;
            audioManager.setMusicVolume(volume);
        });
        gbc.gridx = 1; gbc.gridy = 1;
        painelOpcoes.add(sliderMusica, gbc);

        // Valor do volume da música
        JLabel valorMusica = new JLabel(String.format("%.0f%%", audioManager.getMusicVolume() * 100));
        valorMusica.setForeground(Color.CYAN);
        valorMusica.setFont(new Font("Monospaced", Font.BOLD, 12));
        sliderMusica.addChangeListener(_ -> valorMusica.setText(sliderMusica.getValue() + "%"));
        gbc.gridx = 2; gbc.gridy = 1;
        painelOpcoes.add(valorMusica, gbc);

        // Volume dos Efeitos Sonoros
        JLabel labelSFX = new JLabel("Volume dos Efeitos:");
        labelSFX.setForeground(Color.WHITE);
        labelSFX.setFont(new Font("Monospaced", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 2;
        painelOpcoes.add(labelSFX, gbc);

        JSlider sliderSFX = new JSlider(0, 100, (int)(audioManager.getSFXVolume() * 100));
        sliderSFX.setOpaque(false);
        sliderSFX.setForeground(Color.WHITE);
        sliderSFX.addChangeListener(_ -> {
            float volume = sliderSFX.getValue() / 100.0f;
            audioManager.setSFXVolume(volume);
        });
        gbc.gridx = 1; gbc.gridy = 2;
        painelOpcoes.add(sliderSFX, gbc);

        // Valor do volume dos efeitos
        JLabel valorSFX = new JLabel(String.format("%.0f%%", audioManager.getSFXVolume() * 100));
        valorSFX.setForeground(Color.CYAN);
        valorSFX.setFont(new Font("Monospaced", Font.BOLD, 12));
        sliderSFX.addChangeListener(_ -> valorSFX.setText(sliderSFX.getValue() + "%"));
        gbc.gridx = 2; gbc.gridy = 2;
        painelOpcoes.add(valorSFX, gbc);

        // Botão de teste de som
        JButton btnTestarSom = new JButton("Testar Som");
        btnTestarSom.setFont(new Font("Monospaced", Font.BOLD, 12));
        btnTestarSom.setBackground(new Color(50, 150, 50));
        btnTestarSom.setForeground(Color.WHITE);
        btnTestarSom.setFocusPainted(false);
        btnTestarSom.addActionListener(_ -> audioManager.playLevelUpSFX());
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        painelOpcoes.add(btnTestarSom, gbc);
        gbc.gridwidth = 1;

        // Botão Fechar
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setFont(new Font("Monospaced", Font.BOLD, 14));
        btnFechar.setBackground(new Color(80, 80, 80));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFocusPainted(false);
        btnFechar.addActionListener(_ -> opcoes.dispose());
        gbc.gridx = 1; gbc.gridy = 4;
        painelOpcoes.add(btnFechar, gbc);

        opcoes.add(painelOpcoes);
        opcoes.setVisible(true);
    }

    public static void exibirTela(CountDownLatch latch) {
        SwingUtilities.invokeLater(() -> {
            TelaInicial tela = new TelaInicial(latch);
            tela.setVisible(true);
        });
    }
}