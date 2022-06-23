package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends SuperObject{
    GamePanel gp;
    public OBJ_Heart(GamePanel gp){
        this.gp = gp;
        name = "Key";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/heart_full.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_half.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_blank.png"));
            image = uTool.scaleImage(image, gp.titleSize, gp.titleSize);
            image2 = uTool.scaleImage(image2, gp.titleSize, gp.titleSize);
            image3 = uTool.scaleImage(image3, gp.titleSize, gp.titleSize);
        }catch (IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}