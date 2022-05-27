package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }
    public void checkTile(Entity entity){
        int entityLeftWorldX = (int)entity.worldX + entity.solidArea.x;
        int entityRightWorldX = (int)entity.worldX + entity.solidArea.x +entity.solidArea.width;
        int entityTopWorldY = (int)entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = (int)entity.worldY + entity.solidArea.y +entity.solidArea.height;
        int entityLeftCol = entityLeftWorldX / gp.titleSize;
        int entityRightCol = entityRightWorldX / gp.titleSize;
        int entityTopRow = entityTopWorldY / gp.titleSize;
        int entityBottomRow = entityBottomWorldY / gp.titleSize;
        int titleNum1, titleNum2;
        switch(entity.direction){
            case "up":
                entityTopRow = (entityTopWorldY - (int) entity.speed)/gp.titleSize;
                titleNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                titleNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if(gp.tileM.tile[titleNum1].collision==true || gp.tileM.tile[titleNum2].collision==true){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + (int) entity.speed)/gp.titleSize;
                titleNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                titleNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[titleNum1].collision==true || gp.tileM.tile[titleNum2].collision==true){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - (int) entity.speed)/gp.titleSize;
                titleNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                titleNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if(gp.tileM.tile[titleNum1].collision==true || gp.tileM.tile[titleNum2].collision==true){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX - (int) entity.speed)/gp.titleSize;
                titleNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                titleNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[titleNum1].collision==true || gp.tileM.tile[titleNum2].collision==true){
                    entity.collisionOn = true;
                }
                break;
        }
    }
}
