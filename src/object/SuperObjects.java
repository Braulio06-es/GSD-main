package object;

import core.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObjects {

    public BufferedImage image;
    public ImageIcon animatedIcon;
    public String name;
    public boolean collision = false;
    public boolean enemyDetected = false;

    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefalultX = 0;
    public int solidAreaDefalultY = 0;

    public void draw(Graphics2D g2, GamePanel gp) {

        int screenX= worldX - gp.player.worldX + gp.player.screenX;
        int screenY= worldY - gp.player.worldY + gp.player.screenY;


        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

            if (animatedIcon != null) {
                // Usa el ImageIcon para pintar el GIF animado; gp es el componente observador
                animatedIcon.paintIcon(gp, g2, screenX, screenY);
            } else if (image != null) {
                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
        }

    }
}