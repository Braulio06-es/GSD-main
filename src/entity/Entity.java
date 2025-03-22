package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public Image image;
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, down1, left1, right1, up2, down2, left2, right2, up3, down3, left3, right3, up4, down4, left4, right4;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;

    public int solidAreaDefalultX, solidAreaDefalultY;

    public boolean collisionOn = false;
}