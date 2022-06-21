package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    double playTime;
    DecimalFormat dFomat = new DecimalFormat("#0.00");
    Graphics2D g2;
    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;
    }
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    public void drawPauseState(){
        String text = "PAUSE";
        int x = getXCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);
    }
    public int getXCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
    public void drawDialogueScreen(){
        int x = gp.titleSize*2;
        int y = gp.titleSize/2;
        int width = gp.screenWidth - gp.titleSize*4;
        int height = gp.screenHeight*5;
        drawSubWindow(x, y, width, height);
    }
    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0, 0,0);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
    }
    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        if(gp.gameState == gp.playState){
            if(gameFinished == true){
                String text;
                int textLength;
                int x, y;
                g2.setFont(arial_40);
                g2.setColor(Color.WHITE);
                text = "You found the treasure!";
                textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
                x = gp.screenWidth/2 - textLength/2;
                y = gp.screenHeight/2 - (gp.titleSize*3);
                g2.drawString(text, x, y);
                text = "Your time is : " + dFomat.format(playTime) + "!";
                textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
                x = gp.screenWidth/2 - textLength/2;
                y = gp.screenHeight/2 + (gp.titleSize*4);
                g2.drawString(text, x, y);

                g2.setFont(arial_80B);
                g2.setColor(Color.YELLOW);
                text = "Congratulation!";
                textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
                x = gp.screenWidth/2 - textLength/2;
                y = gp.screenHeight/2 + (gp.titleSize*2);
                g2.drawString(text, x, y);
                gp.gameThread = null;
            }
            else {
                g2.setFont(arial_40);
                g2.setColor(Color.WHITE);
                g2.drawImage(keyImage, gp.titleSize/2, gp.titleSize/2, gp.titleSize, gp.titleSize, null);
                g2.drawString(" x = " + gp.player.hasKey, 74, 65);
                //Message
                if(messageOn == true){
                    g2.setFont(g2.getFont().deriveFont(30F));
                    g2.drawString(message, gp.titleSize/2, gp.titleSize*5);
                    messageCounter++;
                    if(messageCounter > 120){
                        messageCounter = 0;
                        messageOn = false;
                    }
                }
                //Time
                playTime += (double) 1/60;
                g2.drawString("Time: " + dFomat.format(playTime), gp.titleSize*11, 65);
            }
        }
        if(gp.gameState == gp.pauseState){
            drawPauseState();
        }
        if(gp.gameState == gp.dialougeState){
            drawDialogueScreen();
        }
    }
}
