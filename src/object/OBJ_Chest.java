package object;

import javax.imageio.ImageIO;

public class OBJ_Chest extends SuperObjects {
    public OBJ_Chest() {

        name = "Chest";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}