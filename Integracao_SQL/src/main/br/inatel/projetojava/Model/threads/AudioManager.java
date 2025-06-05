package main.br.inatel.projetojava.Model.threads;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AudioManager implements Runnable {

    private Thread audioThread;

    // Tipos de áudio
    public enum AudioType {
        MUSIC, SFX, VOICE
    }

    // Estados do jogo para música contextual
    public enum GameState {
        MAIN_MENU, CITY, BATTLE, INTERACTION, DORMITORY, INVENTORY, SQL
    }

    // Comando para a thread de áudio
    private static class AudioCommand {
        AudioType type;
        String fileName;
        boolean loop;
        float volume;
        String action; // "play", "stop"

        AudioCommand(AudioType type, String fileName, boolean loop, float volume, String action) {
            this.type = type;
            this.fileName = fileName;
            this.loop = loop;
            this.volume = volume;
            this.action = action;
        }
    }

    // Fila de comandos de áudio
    private final BlockingQueue<AudioCommand> audioQueue = new LinkedBlockingQueue<>();

    // Clips de áudio carregados
    private final Map<String, Clip> loadedClips = new HashMap<>();

    // Música atual
    private Clip currentMusic;
    private String currentMusicName;

    // Controle da thread
    private volatile boolean running = true;

    // Volume master para cada tipo
    private float musicVolume = 0.8f;
    private float sfxVolume = 1.0f;

    boolean alreadyRunning = false;

    @Override
    public void run() {
        if (alreadyRunning) return; // Protege contra segunda execução
        alreadyRunning = true;
        // System.out.println("Audio Thread iniciada!");

        while (running) {
            try {
                AudioCommand command = audioQueue.take();
                processAudioCommand(command);
            } catch (InterruptedException e) {
                // Sai do loop se a flag for falsa
                if (!running) break;
            }
        }
        // System.out.println("Audio Thread finalizada");
    }

    private void processAudioCommand(AudioCommand command) {
        switch (command.action) {
            case "play":
                playAudio(command);
                break;
            case "stop":
                Clip clip = getOrLoadClip(command.fileName);
                stopAudio(command.fileName);
                assert clip != null;
                clip.setFramePosition(0); // Volta ao início
                break;
        }
    }

    private void playAudio(AudioCommand command) {
        try {
            // Se é música, para a música atual primeiro
            if (command.type == AudioType.MUSIC && currentMusic != null) {
                currentMusic.stop();
            }

            Clip clip = getOrLoadClip(command.fileName);
            if (clip != null) {
                // Ajusta volume baseado no tipo
                setVolume(clip, command.volume * getTypeVolume(command.type));

                // Configura loop se necessário
                if (command.loop) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } else {
                    clip.setFramePosition(0); // Volta ao início
                }

                clip.start();

                // Se é música, guarda referência
                if (command.type == AudioType.MUSIC) {
                    currentMusic = clip;
                    currentMusicName = command.fileName;
                }

                // System.out.println("Tocando: " + command.fileName);
            }

        } catch (Exception e) {
            System.err.println("Erro ao tocar " + command.fileName + ": " + e.getMessage());
        }
    }

    private Clip getOrLoadClip(String fileName) {
        // Se já está carregado, reutiliza
        if (loadedClips.containsKey(fileName)) {
            return loadedClips.get(fileName);
        }

        try {
            // Carrega o arquivo como recurso do classpath
            try (var audioStream = AudioSystem.getAudioInputStream(
                    Objects.requireNonNull(getClass().getResourceAsStream("/audio/" + fileName)))) {

                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);

                loadedClips.put(fileName, clip);
                return clip;
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Erro ao carregar " + fileName + ": " + e.getMessage());
            return null;
        }
    }

    private void setVolume(Clip clip, float volume) {
        try {
            // Garante que o volume está entre 0.0 e 1.0
            volume = Math.max(0.0f, Math.min(1.0f, volume));

            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            // Converte volume linear (0.0-1.0) para decibéis
            float dB;
            if (volume == 0.0f) {
                dB = volumeControl.getMinimum(); // Mudo completo
            } else {
                dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                // Limita aos valores suportados pelo controle
                dB = Math.max(volumeControl.getMinimum(), Math.min(volumeControl.getMaximum(), dB));
            }

            volumeControl.setValue(dB);
            // System.out.println("Volume ajustado para: " + (volume * 100) + "% (" + dB + " dB)");

        } catch (Exception e) {
            System.err.println("Erro ao ajustar volume: " + e.getMessage());
        }
    }

    private float getTypeVolume(AudioType type) {
        return switch (type) {
            case MUSIC -> musicVolume;
            case SFX -> sfxVolume;
            default -> 1.0f;
        };
    }

    // ===== MÉTODOS PÚBLICOS PARA O GAME LOOP =====

    public void playMusic(String fileName, boolean loop) {
        audioQueue.offer(new AudioCommand(AudioType.MUSIC, fileName, loop, 1.0f, "play"));
    }

    public void playSFX(String fileName) {
        audioQueue.offer(new AudioCommand(AudioType.SFX, fileName, false, 1.0f, "play"));
    }

    public void stopMusic() {
        if (currentMusicName != null) {
            audioQueue.offer(new AudioCommand(AudioType.MUSIC, currentMusicName, false, 1.0f, "stop"));
        }
    }

    // Música contextual baseada no estado do jogo
    public void setGameStateMusic(GameState state) {
        String musicFile = getMusicForState(state);
        playMusic(musicFile, true);
    }

    private String getMusicForState(GameState state) {
        return switch (state) {
            case MAIN_MENU -> "Full-Moon-Full-Life.wav";
            case CITY -> "When-The-Moon_s-Reaching-Out-Stars-Reload-.wav";
            case INTERACTION -> "Joy-_P3R-ver._.wav";
            case BATTLE -> "It’s-Going-Down-Now.wav";
            case DORMITORY -> "Iwatodai-Dorm-Reload-.wav";
            case INVENTORY -> "Color-Your-Night.wav";
            case SQL -> "Changing-Seasons-Reload-.wav";
        };
    }

    public void playMenuSelectSFX() {
        playSFX("menuSelect.wav");
    }

    public void playMenuBackSFX() {
        playSFX("backMenu.wav");
    }


    public void playLevelUpSFX() {
        playSFX("check.wav");
    }

    // Controle de volume por categoria
    public void setMusicVolume(float volume) {
        this.musicVolume = Math.max(0.0f, Math.min(0.7f, volume));
        // Aplica imediatamente na música atual
        if (currentMusic != null && currentMusic.isRunning()) {
            setVolume(currentMusic, musicVolume);
        }
    }

    public void setSFXVolume(float volume) {
        this.sfxVolume = Math.max(0.0f, Math.min(1.0f, volume));
    }


    // Getters para volume atual
    public float getMusicVolume() { return musicVolume; }
    public float getSFXVolume() { return sfxVolume; }

    private void stopAudio(String fileName) {
        Clip clip = loadedClips.get(fileName);
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public synchronized void stopAllAudio() { // Tratar erros de concorrência
        for (Clip clip : new HashMap<>(loadedClips).values()) {
            try {
                if (clip.isRunning()) {
                    clip.stop();
                }
                clip.close();
            } catch (Exception e) {
                System.err.println("Erro ao fechar clip: " + e.getMessage());
            }
        }
        loadedClips.clear();
    }

    public static AudioManager instance;

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
            instance.audioThread = new Thread(instance);
            instance.audioThread.setDaemon(false); // ou true, dependendo do comportamento desejado
            instance.audioThread.start();
        }
        return instance;
    }

    public void shutdown() {
        running = false;
        if (audioThread != null) {
            audioThread.interrupt(); // Interrompe o take() da fila
        }

        // Para a música atual explicitamente
        if (currentMusic != null) {
            try {
                currentMusic.stop();
                currentMusic.close();
            } catch (Exception e) {
                System.err.println("Erro ao fechar música atual: " + e.getMessage());
            }
            currentMusic = null;
            currentMusicName = null;
        }

        stopAllAudio();
        // System.out.println("Músicas Desligadas.");
    }

    public static void resetInstance() {
        if (instance != null) {
            instance.shutdown();
            instance = null;
        }
    }
}