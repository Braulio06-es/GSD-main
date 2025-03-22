package object;

import javax.imageio.ImageIO;
import javax.swing.*;

public class OBJ_Key extends SuperObjects {

    public OBJ_Key() {

        name = "Key";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}