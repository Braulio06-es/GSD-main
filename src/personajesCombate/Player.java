package personajesCombate;

import core.CombatPanel;
import core.SoundPlayer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player extends Entity {
    private CombatPanel cp;
    public final int screenX, screenY;

    // Acción actual y flags
    private String action = "attack";
    private boolean isDefending = false;

    // Multiplicadores que se acumulan (stackable)
    private double attackMultiplier = 1.0;
    private double defenseMultiplier = 1.0;

    // Número de usos especiales disponibles (stackables)
    private int specialUsesRemaining = 5; // Puedes ajustar el límite para el jugador si lo deseas

    public Player(CombatPanel cp) {
        this.cp = cp;
        screenX = cp.ScreenWidth / 2 - (cp.tileSize / 2);
        screenY = cp.ScreenHeight / 2 - (cp.tileSize / 2);
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        attack = 15;
        maxVitality = vitality = 100;
        attackMultiplier = 1.0;
        defenseMultiplier = 1.0;
        specialUsesRemaining = 2;  // reiniciamos los usos especiales
        action = "attack";
        isDefending = false;
    }

    public void getPlayerImage(){
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Player/CS.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Asigna la acción del jugador.
     * Si se usa posturaSoldado o posturaGuardian, se aplica el buff de forma acumulativa y no se ataca en ese turno.
     */
    public void setAction(String action) {
        if (action.equals("posturaSoldado")) {
            if (specialUsesRemaining > 0) {
                specialUsesRemaining--;
                // Si quieres acumular el buff, puedes multiplicar el multiplicador actual
                attackMultiplier *= 1.5;
                this.action = action;
                System.out.println("Player entra en POSTURA SOLDADO. Buff de ataque acumulado: x"
                        + attackMultiplier + " (especiales restantes: " + specialUsesRemaining + ")");
                return;
            } else {
                System.out.println("No quedan especiales. Usando ataque normal.");
                this.action = "attack";
            }
        } else if (action.equals("posturaGuardian")) {
            if (specialUsesRemaining > 0) {
                specialUsesRemaining--;
                defenseMultiplier *= 0.75;
                isDefending = true;
                this.action = action;
                System.out.println("Player entra en POSTURA GUARDIÁN. Buff de defensa acumulado: x"
                        + defenseMultiplier + " (especiales restantes: " + specialUsesRemaining + ")");
                return;
            } else {
                System.out.println("No quedan especiales. Usando ataque normal.");
                this.action = "attack";
            }
        } else if (action.equals("tajoBrutal")) {
            // Ahora "Tajo Brutal" se considera especial y consume un uso
            if (specialUsesRemaining > 0) {
                specialUsesRemaining--;
                this.action = action;
                System.out.println("Player usa TAJO BRUTAL (especial). Especiales restantes: " + specialUsesRemaining);
                return;
            } else {
                System.out.println("No quedan especiales. Usando ataque normal.");
                this.action = "attack";
            }
        } else if (action.equals("defend")) {
            isDefending = true;
            this.action = action;
            System.out.println("Player defiende.");
        } else {
            // Para "attack" u otras acciones que no sean especiales
            this.action = action;
            System.out.println("Player usa " + action + ".");
        }
    }



    /**
     * Ejecuta la acción del jugador.
     * Si se eligió una postura, se activa el buff y no se ataca en ese turno.
     */
    public void executeAction(Enemy enemy) {
        if (action.equals("defend") || action.equals("posturaSoldado") || action.equals("posturaGuardian")) {
            if (action.equals("defend")) {
                SoundPlayer.playSound("/sound/defend.wav");
            } else {
                SoundPlayer.playSound("/sound/posture.wav");
            }
            System.out.println("Player se prepara (" + action + ") y no ataca este turno.");
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
        if (enemy.isDefending()) {
            damage /= 2;
        } else if (enemy.getDefenseMultiplier() < 1.0) {
            damage = (int)(damage * enemy.getDefenseMultiplier());
        }
        enemy.decreaseVitality(damage);
        System.out.println("Player ataca y causa " + damage + " de daño.");
    }

    public double getAttackMultiplier() {
        return attackMultiplier;
    }

    public double getDefenseMultiplier() {
        return defenseMultiplier;
    }

    public boolean isDefending() {
        return isDefending;
    }

    public void draw(Graphics2D g2) {
        int newWidth = image.getWidth(null) / 15 * 6;
        int newHeight = image.getHeight(null) / 15 * 6;
        g2.drawImage(image, screenX, screenY, newWidth, newHeight, null);
        drawHealthBar(g2, screenX, screenY, newWidth);
    }

    public int getSpecialUsesRemaining() {
        return specialUsesRemaining;
    }

    public int getTotalSpecials() {
        return 2;
    }

    public String getAction() {
        return action;
    }
}
