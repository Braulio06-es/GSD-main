package personajesCombate;

import java.awt.*;

public class Entity {
    public Image image;

    // Estadísticas de combate
    protected int attack;
    protected int vitality;
    protected int maxVitality;

    public int getAttack() {
        return attack;
    }

    public int getVitality() {
        return vitality;
    }

    // Disminuye la vitalidad sin bajar de cero.
    public void decreaseVitality(int damage) {
        vitality = Math.max(0, vitality - damage);
    }

    // Dibuja la barra de vida (6px de alto, 10px sobre el sprite).
    public void drawHealthBar(Graphics2D g2, int x, int y, int width) {
        int barHeight = 6;
        int barX = x;
        int barY = y - 10;
        int healthWidth = (int)(((double)vitality / maxVitality) * width);

        g2.setColor(Color.GRAY);
        g2.fillRect(barX, barY, width, barHeight);

        g2.setColor(Color.GREEN);
        g2.fillRect(barX, barY, healthWidth, barHeight);

        g2.setColor(Color.BLACK);
        g2.drawRect(barX, barY, width, barHeight);
    }
}
