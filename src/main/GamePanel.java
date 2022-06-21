package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //Screen Setting
    final int originalTitleSize = 16; // 16x16 title
    final int scale = 3;
    public int titleSize = originalTitleSize * scale; // 48x48 title
    public int maxScreenCol = 16;
    public int maxScreenRow = 12;
    public int screenWidth = titleSize * maxScreenCol; // 768 pixels
    public int screenHeight = titleSize * maxScreenRow; // 576 pixels
    //World setting
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = titleSize * maxWorldCol; //48*50=2400
    public final int worldHeight = titleSize * maxWorldRow;
    //System
    Thread gameThread;
    KeyHandler keyH = new KeyHandler(this);
    TileManager tileM = new TileManager(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public UI ui = new UI(this);
    //Entity and Object
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];
    //FPS
    int FPS = 60;
    //set default's player position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;
    //Game state
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialougeState = 3;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void setupGame(){
        aSetter.setObject();
        aSetter.setNPC();
        playMusic(0);
        stopMusic();
        gameState = playState;
    }
    public void update(){
        if(gameState == playState){
            //player
            player.update();
            //NPC
            for(int i=0; i<npc.length; i++){
                if(npc[i] != null){
                    npc[i].update();
                }
            }
        }
        if(gameState == pauseState){
            //nothing
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        long drawStart = 0;
        //Debug
        if(keyH.checkDrawTime == true){
            drawStart = System.nanoTime();
        }
        //title
        tileM.draw(g2);
        //object
        for(int i=0; i<obj.length; i++){
            if(obj[i] != null){
                obj[i].draw(g2, this);
            }
        }
        //NPC
        for(int i=0; i< npc.length; i++){
            if(npc[i] != null){
                npc[i].draw(g2);
            }
        }
        //player
        player.draw(g2);
        //UI
        ui.draw(g2);
        //Debug
        if(keyH.checkDrawTime == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.WHITE);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }
        g2.dispose();
    }
    //Zoom in and zoom out function
   public void zoomInOut(int i){
        int oldWorldWidth = titleSize * maxWorldCol;  //2400
        titleSize += i;
        int newWorldWidth = titleSize * maxWorldCol; //2350
        player.speed = (double)newWorldWidth / 600;
        double multiplier = (double) newWorldWidth / oldWorldWidth;
        System.out.println("titleSize: " + titleSize);
        System.out.println("worldWidth: " + newWorldWidth);
        System.out.println("player.worldX: " + player.worldX);
        double newPlayerWorldX = player.worldX * multiplier;
        double newPlayerWorldY = player.worldY * multiplier;
        player.worldX = newPlayerWorldX;
        player.worldY = newPlayerWorldY;
    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
    // Delta method
    public void run(){
        double drawInterval = 1000000000 / FPS;  // 0.0166666667 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while (gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    // Sleep method
/*    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // 0.0166666667 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null){
            update(); //1. Update: update information such as character positions
            repaint(); //2. Draw: draw the screen with the update information
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;
                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
*/
}
