package core;

import personajesCombate.Enemy;
import personajesCombate.Player;
import javax.swing.*;
import java.awt.*;

public class CombatPanel extends JPanel {

    public final int originalTileSize = 16;
    public final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxscreenCol = 16;
    public final int maxscreenRow = 12;
    public final int ScreenWidth = tileSize * maxscreenCol;
    public final int ScreenHeight = tileSize * maxscreenRow;

    private Player player;
    private Enemy enemy;
    private CombatHUD hud;

    // Log de acciones para mostrar en pantalla
    private String actionLog = "";

    // Imagen de fondo para el combate
    private final Image backgroundImage;

    // Referencia al GamePanel y al índice del enemigo en el arreglo del mundo
    private int enemyIndex;
    private GamePanel gp;

    /**
     * Constructor del CombatPanel.
     *
     * @param enemyType  Tipo del enemigo (para cargar estadísticas y assets)
     * @param enemyIndex Índice del objeto enemigo en el GamePanel, para eliminarlo o moverlo al ganar
     * @param gp         Referencia al GamePanel principal
     */
    public CombatPanel(String enemyType, int enemyIndex, GamePanel gp) {
        this.enemyIndex = enemyIndex;
        this.gp = gp;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        setDoubleBuffered(true);
        setFocusable(true);

        // Cargamos la imagen de fondo del combate
        backgroundImage = new ImageIcon(getClass().getResource("/Background/descarga.gif")).getImage();

        // Iniciamos la música de combate a través del GamePanel
        gp.startCombatMusic(enemyType);

        // Creamos las entidades de combate
        player = new Player(this);
        enemy = new Enemy(this, enemyType);

        // Agregamos el HUD en la parte inferior
        hud = new CombatHUD(this, player);
        add(hud, BorderLayout.SOUTH);
    }

    /**
     * Se llama cuando el jugador selecciona una acción desde el HUD.
     * Ejecuta la acción del jugador y, tras un retardo, la acción del enemigo.
     */
    public void playerTurn() {
        hud.setVisible(false);
        player.executeAction(enemy);
        actionLog = "Player usa " + player.getAction() + ".\n";
        repaint();

        if (enemy.getVitality() > 0) {
            Timer timer = new Timer(1000, e -> {
                enemy.chooseAction();
                enemy.executeAction(player);
                actionLog += "Enemy usa " + enemy.getAction() + ".";
                repaint();
                ((Timer) e.getSource()).stop();
                hud.setVisible(true);
                if (player.getVitality() <= 0 || enemy.getVitality() <= 0) {
                    endCombat();
                }
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            endCombat();
        }
    }
    /**
     * Finaliza el combate:
     * - Si el jugador pierde, se muestra el mensaje y se cierra la aplicación.
     * - Si gana, se "desactiva" el enemigo moviéndolo fuera del área,
     *   se detiene la música, se reactivan los controles y se cierra el diálogo de combate.
     */
    private void endCombat() {
        // Detenemos la música de combate a través del GamePanel
        gp.stopCombatMusic();

        if (player.getVitality() <= 0) {
            showResultImage(false);  // Muestra la imagen de "Has perdido"
            JuegoArrancar.JuegoArrancar(null);

        } else {
            showResultImage(true);   // Muestra la imagen de "Has ganado"
            // "Elimina" al enemigo moviéndolo fuera del área visible
            if (enemy.getVitality() <= 0 && gp.obj[enemyIndex] != null) {
                gp.obj[enemyIndex].worldX = -10000;
                gp.obj[enemyIndex].worldY = -10000;
            }
            gp.gamePaused = false;
            gp.keyH.resetKeys();
            gp.requestFocusInWindow();

            // Cierra el diálogo de combate
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window instanceof JDialog) {
                ((JDialog) window).dispose();
            }
            System.out.println("Combate finalizado, juego reanudado.");
        }
    }

    private void showResultImage(boolean win) {
        // Tamaño deseado para la imagen
        int width = 200;
        int height = 150;
        // Ruta de la imagen según el resultado
        String imagePath = win ? "/Background/YouWin.png" : "/Background/YouLose.png";
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        // Escalamos la imagen
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel label = new JLabel(scaledIcon);
        label.setHorizontalAlignment(JLabel.CENTER);
        // Mostramos la imagen en un JOptionPane sin botones ni título
        JOptionPane.showMessageDialog(this, label, "", JOptionPane.PLAIN_MESSAGE);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujamos el fondo de combate
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        Graphics2D g2 = (Graphics2D) g.create();
        if (player.getVitality() > 0) {
            player.draw(g2);
        }
        if (enemy.getVitality() > 0) {
            enemy.draw(g2);
        }
        drawActionLog(g2);
        g2.dispose();
    }

    /**
     * Dibuja el log de acciones en la esquina inferior izquierda.
     */
    private void drawActionLog(Graphics2D g2) {
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        FontMetrics fm = g2.getFontMetrics();
        int padding = 10;
        String[] lines = actionLog.split("\n");
        int logWidth = 0;
        for (String line : lines) {
            logWidth = Math.max(logWidth, fm.stringWidth(line));
        }
        logWidth += padding * 2;
        int lineHeight = fm.getHeight();
        int logHeight = lineHeight * lines.length + padding * 2;
        int x = 10;
        int y = getHeight() - logHeight - 50;
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(x, y, logWidth, logHeight);
        g2.setColor(Color.WHITE);
        for (int i = 0; i < lines.length; i++) {
            g2.drawString(lines[i], x + padding, y + padding + fm.getAscent() + i * lineHeight);
        }
    }
}