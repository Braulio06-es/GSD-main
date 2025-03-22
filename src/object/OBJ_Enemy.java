package object;

import javax.imageio.ImageIO;
import javax.swing.*;

public class OBJ_Enemy extends SuperObjects {

    // Atributo para identificar el tipo de enemigo
    private String enemyType;
    private boolean inCombat = false;
    public OBJ_Enemy(String enemyType) {
        this.enemyType = enemyType;
        name = "Enemy";
        try {
            // Carga una imagen para el mundo según el tipo.
            // Puedes tener imágenes distintas para la vista en el mundo y para el combate.
            if(enemyType.equals("devil1")) {
                animatedIcon = new ImageIcon(getClass().getResource("/objects/mapdevil1.gif"));
            } else if(enemyType.equals("devil2")) {
                animatedIcon = new ImageIcon(getClass().getResource("/objects/mapdevil2.gif"));
            } else {
                animatedIcon = new ImageIcon(getClass().getResource("/objects/mapdevil3.gif"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(String enemyType) {
        this.enemyType = enemyType;
    }

    public boolean isInCombat() {
        return inCombat;
    }

    public void setInCombat(boolean inCombat) {
        this.inCombat = inCombat;
    }
}