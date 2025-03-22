package core;

import entity.Entity;
import object.OBJ_Enemy;
import javax.swing.*;
import java.awt.*;

public class CollisionChecker {

    GamePanel gp;
    GameDialog gameDialog;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }
    public int checkObject(Entity entity, boolean player) {
        int index = 999;
        entity.collisionOn = false;

        // Recorremos el array de objetos
        for (int i = 0; i < gp.obj.length; i++) {
            // Si el objeto es null, lo saltamos
            if (gp.obj[i] == null) continue;

            // Creamos rectángulos temporales para la entidad y para el objeto
            Rectangle entityRect = new Rectangle(
                    entity.worldX + entity.solidArea.x,
                    entity.worldY + entity.solidArea.y,
                    entity.solidArea.width,
                    entity.solidArea.height
            );
            Rectangle objectRect = new Rectangle(
                    gp.obj[i].worldX + gp.obj[i].solidArea.x,
                    gp.obj[i].worldY + gp.obj[i].solidArea.y,
                    gp.obj[i].solidArea.width,
                    gp.obj[i].solidArea.height
            );

            // Ajustamos el rectángulo según la dirección del movimiento
            if (entity.direction.equals("up")) {
                entityRect.y -= entity.speed;
            } else if (entity.direction.equals("down")) {
                entityRect.y += entity.speed;
            } else if (entity.direction.equals("left")) {
                entityRect.x -= entity.speed;
            } else if (entity.direction.equals("right")) {
                entityRect.x += entity.speed;
            }

            // Si se intersectan...
            if (entityRect.intersects(objectRect)) {
                if (gp.obj[i].collision) {
                    entity.collisionOn = true;
                }
                if (player) {
                    index = i;
                }
                // Si es un enemigo y no está en combate, iniciamos el combate
                if (gp.obj[i].name.equals("Enemy")) {
                    if (gp.obj[i] instanceof object.OBJ_Enemy) {
                        object.OBJ_Enemy enemyObj = (object.OBJ_Enemy) gp.obj[i];
                        if (!enemyObj.isInCombat()) {
                            enemyObj.setInCombat(true);
                            String enemyType = enemyObj.getEnemyType();
                            GameDialog dialog = new GameDialog(enemyType, i, gp);
                            dialog.start();
                            gp.gamePaused = true;
                        }
                    }
                }
            }
        }

        return index;
    }


}