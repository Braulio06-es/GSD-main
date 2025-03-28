package entity;

import core.GamePanel;
import core.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    int hasKey = 0;


    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32);

        solidAreaDefalultX = solidArea.x;
        solidAreaDefalultY = solidArea.y;

        getPlayerImage();
        setDefaultValues();
    }

    public void setDefaultValues(){
        worldX= gp.tileSize * 4;
        worldY= gp.tileSize * 4;
        speed=4;
        direction = "up";
    }

    public void getPlayerImage(){
        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("/player/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/up2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/player/up3.png"));
            up4 = ImageIO.read(getClass().getResourceAsStream("/player/up4.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/down2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/player/down3.png"));
            down4 = ImageIO.read(getClass().getResourceAsStream("/player/down4.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/player/left3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/player/left4.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/player/right3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/player/right4.png"));

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public void update() {

        if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true){

            if(keyH.upPressed == true){
                direction = "up";
                // manera eficiente y reducida
                // playerY = playerY - playerSpeed; // para entenderlo
            }
            else if(keyH.downPressed == true){
                direction = "down";

            }
            else if(keyH.leftPressed == true){
                direction = "left";

            }
            else if(keyH.rightPressed == true){
                direction = "right";

            }


            // check Tile Collision

            collisionOn = false;
            gp.cChecker.checkTile(this);

            // check object collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // if collision is false player can move

            if(collisionOn == false){
                switch(direction){
                    case "up": worldY -= speed;     break;
                    case "down": worldY += speed;   break;
                    case "left": worldX -= speed;   break;
                    case "right": worldX += speed;  break;
                }
            }

            spriteCounter++;
            if(spriteCounter > 10)  {

                if(spriteNum == 1)        {
                    spriteNum = 2;
                }
                else if (spriteNum == 2)  {
                    spriteNum = 3;
                }
                else if (spriteNum == 3)  {
                    spriteNum = 4;
                }
                else if (spriteNum == 4)  {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

        }
    }

    public void pickUpObject(int i){

        if(i !=999) {
            String objectName = gp.obj[i].name;

            switch (objectName){
                case "Key":
                    hasKey ++;
                    gp.obj[i] = null;
                    System.out.println("Key : " + hasKey );
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--;
                    }
                    System.out.println("Key : " + hasKey );
                    break;
//                case "Boots":
//                    speed+= 1;
//                    gp.obj[i] = null;
//                break;
            }

        }

    }

    public void draw (Graphics2D g2) {

//        g2.setColor(Color.WHITE);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch(direction){
            case "up":
                if (spriteNum == 1){
                    image = up1;
                }
                if (spriteNum == 2){
                    image = up2;
                }
                if (spriteNum == 3){
                    image = up3;
                }
                if (spriteNum == 4) {
                    image = up4;
                }
                break;
            case "down":
                if (spriteNum == 1){
                    image = down1;
                }
                if (spriteNum == 2){
                    image = down2;
                }
                if (spriteNum == 3){
                    image = down3;
                }
                if (spriteNum == 4) {
                    image = down4;
                }
                break;
            case "left":
                if (spriteNum == 1){
                    image = left1;
                }
                if (spriteNum == 2){
                    image = left2;
                }
                if (spriteNum == 3){
                    image = left3;
                }
                if (spriteNum == 4){
                    image = left4;
                }
                break;
            case "right":
                if (spriteNum == 1){
                    image = right1;
                }
                if (spriteNum == 2){
                    image = right2;
                }
                if (spriteNum == 3){
                    image = right3;
                }
                if (spriteNum == 4){
                    image = right4;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}