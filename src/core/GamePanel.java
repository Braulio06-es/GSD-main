package core;

import entity.Player;
import object.SuperObjects;
import tile.TileManager;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // Configuración de la pantalla y el mundo
    public final int originalTileSize = 64;
    public final int scale = 1;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // Configuración del mundo
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxScreenCol;
    public final int worldHeight = tileSize * maxScreenRow;
    private Clip combatMusic;
    public boolean gamePaused = false;
    public boolean dialogShown = false;

    // FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);

    KeyHandler keyH = new KeyHandler();

    Thread gameThread;

    public CollisionChecker cChecker = new CollisionChecker(this);

    public boolean enemyDetected = false;

    public AssetSetter aSetter = new AssetSetter(this);

    public Player player = new Player(this, keyH);

    public SuperObjects obj[] = new SuperObjects[20];

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        aSetter.setObject();
    }



    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval = 1_000_000_000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                if (!gamePaused) {
                    update();  // Aquí se mueve al jugador y se actualiza el juego
                }
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1_000_000_000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(){
        player.update();
    }


    public void startCombatMusic(String enemyType) {
        String musicPath;
        if (enemyType.equals("devil1") || enemyType.equals("devil2")) {
            musicPath = "/sound/SoundtrackCombate.wav";
        } else if (enemyType.equals("devil3")) {
            musicPath = "/sound/SoundtrackJefe.wav";
        } else {
            musicPath = "/sound/SoundCombate.wav";
        }
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResource(musicPath));
            combatMusic = AudioSystem.getClip();
            combatMusic.open(ais);
            combatMusic.loop(Clip.LOOP_CONTINUOUSLY);
            System.out.println("Música de combate iniciada: " + musicPath);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Método para detener la música de combate de forma segura
    public void stopCombatMusic() {
        if (combatMusic != null) {
            // Ejecutamos en un hilo separado para no bloquear el hilo principal de Swing
            new Thread(() -> {
                try {
                    if (combatMusic.isRunning()) {
                        combatMusic.stop();
                    }
                    combatMusic.close();
                    combatMusic = null;
                    System.out.println("Música de combate detenida.");
                } catch (Exception e) {
                    System.out.println("Error al detener la música: " + e.getMessage());
                }
            }).start();
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        tileM.draw(g2);
        for(int i = 0; i < obj.length; i++) {
            if(obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }
        player.draw(g2);
        g2.dispose();
    }
}