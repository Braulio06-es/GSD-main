package core;

import object.*;

public class AssetSetter {

    GamePanel gp;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject(){
        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 5 * gp.tileSize;
        gp.obj[0].worldY = 4 * gp.tileSize;

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 20 * gp.tileSize;
        gp.obj[1].worldY = 10 * gp.tileSize;

        gp.obj[2] = new OBJ_Key();
        gp.obj[2].worldX = 17 * gp.tileSize;
        gp.obj[2].worldY = 38 * gp.tileSize;

        gp.obj[3] = new OBJ_Door();
        gp.obj[3].worldX = 5 * gp.tileSize;
        gp.obj[3].worldY = 31 * gp.tileSize;

        gp.obj[4] = new OBJ_Door();
        gp.obj[4].worldX = 33 * gp.tileSize;
        gp.obj[4].worldY = 40 * gp.tileSize;

        gp.obj[5] = new OBJ_Door();
        gp.obj[5].worldX = 16 * gp.tileSize;
        gp.obj[5].worldY = 5 * gp.tileSize;

        gp.obj[6] = new OBJ_Chest();
        gp.obj[6].worldX = 22 * gp.tileSize;
        gp.obj[6].worldY = 17 * gp.tileSize;

        gp.obj[7] = new OBJ_Chest();
        gp.obj[7].worldX = 32 * gp.tileSize;
        gp.obj[7].worldY = 44 * gp.tileSize;

        gp.obj[8] = new OBJ_Chest();
        gp.obj[8].worldX = 38 * gp.tileSize;
        gp.obj[8].worldY = 33* gp.tileSize;

        gp.obj[9] = new OBJ_Enemy("devil1");
        gp.obj[9].worldX = 23 * gp.tileSize;
        gp.obj[9].worldY = 5 * gp.tileSize;

        gp.obj[10] = new OBJ_Enemy("devil1");
        gp.obj[10].worldX = 8 * gp.tileSize;
        gp.obj[10].worldY = 37 * gp.tileSize;

        gp.obj[11] = new OBJ_Enemy("devil2");
        gp.obj[11].worldX = 21 * gp.tileSize;
        gp.obj[11].worldY = 39 * gp.tileSize;

        gp.obj[12] = new OBJ_Enemy("devil2");
        gp.obj[12].worldX = 35 * gp.tileSize;
        gp.obj[12].worldY = 33 * gp.tileSize;

        gp.obj[13] = new OBJ_Enemy("devil3");
        gp.obj[13].worldX = 29 * gp.tileSize;
        gp.obj[13].worldY = 38 * gp.tileSize;
    }
}