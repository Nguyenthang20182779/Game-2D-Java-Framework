package object;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    UtilityTool uTool = new UtilityTool();
    public void draw(Graphics2D g2, GamePanel gp){
        int screenX = worldX - (int)gp.player.worldX + gp.player.screenX;
        int screenY = worldY - (int)gp.player.worldY + gp.player.screenY;
        if(worldX + gp.titleSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.titleSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.titleSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.titleSize < gp.player.worldY + gp.player.screenY){
            g2.drawImage(image, screenX, screenY, gp.titleSize, gp.titleSize, null);
        }
    }
}
