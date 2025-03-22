package personajesCombate;

import core.CombatPanel;
import core.SoundPlayer;

import javax.swing.ImageIcon;
import java.awt.*;
import java.util.Random;

public class Enemy extends Entity {

    private CombatPanel cp;
    public final int screenX, screenY;
    private String action = "attack";
    private Random random = new Random();
    private boolean isDefending = false;
    private double defenseMultiplier = 1.0;
    private double attackMultiplier = 1.0; // Buff acumulable para ataque

    // Tipo de enemigo para diferenciar assets y estadísticas
    private String enemyType;

    // Variable para el GIF animado (para enemigos que lo requieran)
    private ImageIcon animatedIcon;

    // Contador para limitar el uso combinado de posturas a 5 veces
    private int specialUsageCount = 0;

    // Constructor que recibe el tipo de enemigo
    public Enemy(CombatPanel cp, String enemyType) {
        this.cp = cp;
        this.enemyType = enemyType;
        screenX = cp.ScreenWidth / 4 - (cp.tileSize / 2);
        screenY = cp.ScreenHeight / 5 - (cp.tileSize / 4);
        setDefaultValues();
        getEnemyImage();
    }

    public void setDefaultValues() {
        switch (enemyType) {
            case "devil1":
                attack = 10;
                maxVitality = vitality = 150;
                break;
            case "devil2":
                attack = 15;
                maxVitality = vitality = 175;
                break;
            case "devil3":
                attack = 20;
                maxVitality = vitality = 200;
                break;
            default:
                attack = 10;
                maxVitality = vitality = 60;
                break;

        }
        action = "attack";
        isDefending = false;
        defenseMultiplier = 1.0;
        attackMultiplier = 1.0;
        specialUsageCount = 0;
    }

    public void getEnemyImage() {
        // Se carga el GIF animado mediante ImageIcon.
        // No se asigna animatedIcon.getImage() a image para preservar la animación.
        if (enemyType.equals("devil1")) {
            animatedIcon = new ImageIcon(getClass().getResource("/objects/devil1.gif"));
        } else if (enemyType.equals("devil2")) {
            animatedIcon = new ImageIcon(getClass().getResource("/objects/devil2.gif"));
        } else {
            animatedIcon = new ImageIcon(getClass().getResource("/objects/devil3.gif"));
        }
    }

    public void chooseAction() {
        int choice = random.nextInt(5);
        switch (choice) {
            case 0:
                action = "attack";
                isDefending = false;
                defenseMultiplier = 1.0;
                break;
            case 1:
                action = "defend";
                isDefending = true;
                defenseMultiplier = 0.5;
                break;
            case 2: // Postura Soldado: acumula buff de ataque
                if (specialUsageCount < 5) {
                    specialUsageCount++;
                    attackMultiplier *= 1.5;
                    action = "posturaSoldado";
                    System.out.println("Enemy usa POSTURA SOLDADO. Buff de ataque acumulado: x"
                            + attackMultiplier + " (uso " + specialUsageCount + "/5)");
                } else {
                    action = "attack";
                    System.out.println("Enemy ya no puede usar POSTURA SOLDADO. Usa attack.");
                }
                break;
            case 3: // Postura Guardián: acumula buff de defensa
                if (specialUsageCount < 5) {
                    specialUsageCount++;
                    defenseMultiplier *= 0.75;
                    isDefending = true;
                    action = "posturaGuardian";
                    System.out.println("Enemy usa POSTURA GUARDIÁN. Buff de defensa acumulado: x"
                            + defenseMultiplier + " (uso " + specialUsageCount + "/5)");
                } else {
                    action = "attack";
                    System.out.println("Enemy ya no puede usar POSTURA GUARDIÁN. Usa attack.");
                }
                break;
            case 4:
                action = "tajoBrutal";
                isDefending = false;
                break;
        }
        System.out.println("Enemy elige: " + action);
    }

    public boolean isDefending() {
        return isDefending;
    }

    public double getDefenseMultiplier() {
        return defenseMultiplier;
    }

    public String getAction() {
        return action;
    }

    public void executeAction(Player player) {
        // Si la acción es una postura o defender, se prepara y no ataca en ese turno.
        if (action.equals("defend") || action.equals("posturaSoldado") || action.equals("posturaGuardian")) {
            if (action.equals("defend")) {
                SoundPlayer.playSound("/sound/defend.wav");
            } else {
                SoundPlayer.playSound("/sound/posture.wav");
            }
            System.out.println("Enemy se prepara (" + action + ") y no ataca este turno.");
            return;
        }
        int damage = attack;
        if (action.equals("tajoBrutal")) {
            SoundPlayer.playSound("/sound/brutal.wav");
            damage *= 3;
        } else if (action.equals("attack")) {
            SoundPlayer.playSound("/sound/attack.wav");
            damage = (int)(damage * attackMultiplier);
        }
        if (player.isDefending()) {
            damage /= 2;
        } else if (player.getDefenseMultiplier() < 1.0) {
            damage = (int)(damage * player.getDefenseMultiplier());
        }
        player.decreaseVitality(damage);
        System.out.println("Enemy usa " + action + " y causa " + damage + " de daño.");
    }

    public void draw(Graphics2D g2) {
        // Si se cargó el GIF animado, se utiliza animatedIcon para pintar el GIF.
        if (animatedIcon != null) {
            animatedIcon.paintIcon(cp, g2, screenX, screenY);
        } else {
            int newWidth = image.getWidth(null) / 10 * 6;
            int newHeight = image.getHeight(null) / 10 * 6;
            g2.drawImage(image, screenX, screenY, newWidth, newHeight, cp);
        }
        drawHealthBar(g2, screenX, screenY, 50);
    }
}
