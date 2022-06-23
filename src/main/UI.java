package main;

import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;

import java.awt.*;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.awt.Graphics2D;

public class UI {
    public boolean messageOn = false;
    public String message = "";
    public boolean gameFinished = false;
    public String currentDialouge = "";
    public int commandNum = 0;
    public int titleScreenState = 0;
    int messageCounter = 0;
    double playTime;
    GamePanel gp;
    Font arial_40, arial_80B;
    Font maruMonica, purisaB;
    BufferedImage heart_full, heart_half, heart_blank;
    BufferedImage keyImage;
    DecimalFormat dFomat = new DecimalFormat("#0.00");
    Graphics2D g2;

    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        SuperObject key = new OBJ_Key(gp);
        keyImage = key.image;
        try{
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch(FontFormatException e){
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
        SuperObject heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
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
        int width = gp.screenWidth - (gp.titleSize*4);
        int height = gp.titleSize*4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        x += gp.titleSize;
        y += gp.titleSize;
        for(String line : currentDialouge.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }
    }
    public void drawSubWindow(int x, int y, int width, int height){
        Color c1 = new Color(0, 0,0, 210);
        g2.setColor(c1);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        Color c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    public void drawTitleScreen(){
        if(titleScreenState == 0){
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            //titleName
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String text = "Blue Boy Adventure";
            int x = getXCenteredText(text);
            int y = gp.titleSize*3;
            //shadow color
            g2.setColor(Color.GRAY);
            g2.drawString(text, x+5, y+5);
            //main color
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);
            //blue boy image title screen
            x = gp.screenWidth/2 - gp.titleSize;
            y += gp.titleSize*2;
            g2.drawImage(gp.player.down1, x, y, gp.titleSize*2, gp.titleSize*2, null);
            //menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "NEW GAME";
            x = getXCenteredText(text);
            y += gp.titleSize*3.5;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x - gp.titleSize, y);
            }
            text = "LOAD GAME";
            x = getXCenteredText(text);
            y += gp.titleSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x - gp.titleSize, y);
            }
            text = "QUIT";
            x = getXCenteredText(text);
            y += gp.titleSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x - gp.titleSize, y);
            }
        }
        else if(titleScreenState == 1){
            //class selection screen
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(42F));
            String text = "Select your class!";
            int x = getXCenteredText(text);
            int y = gp.titleSize*3;
            g2.drawString(text, x, y);

            text = "Fighter";
            x = getXCenteredText(text);
            y += gp.titleSize*3;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x-gp.titleSize, y);
            }
            text = "Thief";
            x = getXCenteredText(text);
            y += gp.titleSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x-gp.titleSize, y);
            }
            text = "Sorcerer";
            x = getXCenteredText(text);
            y += gp.titleSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x-gp.titleSize, y);
            }
            text = "Back";
            x = getXCenteredText(text);
            y += gp.titleSize*2;
            g2.drawString(text, x, y);
            if(commandNum == 3){
                g2.drawString(">", x-gp.titleSize, y);
            }
        }
    }
    public void drawPlayerLife(){
        //gp.player.life = 5;
        int x = gp.titleSize/2;
        int y = gp.titleSize*2;
        int i = 0;
        //Draw max life
        while(i < gp.player.maxLife/2){
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.titleSize;
        }
        //Reset
        x = gp.titleSize/2;
        y = gp.titleSize*2;
        i = 0;
        //Draw current life
        while(i < gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.titleSize;
        }
    }
    public void draw(Graphics2D g2){
        this.g2 = g2;
        //g2.setFont(arial_40);
        g2.setFont(maruMonica);
        g2.setColor(Color.WHITE);
        //PlayState
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
            drawPlayerLife();
        }
        //PauseState
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseState();
        }
        //DialougeState
        if(gp.gameState == gp.dialougeState){
            drawPlayerLife();
            drawDialogueScreen();
        }
        //TitleState
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
    }
}
