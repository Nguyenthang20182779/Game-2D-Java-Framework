package main;

import entity.Player;
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

    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public Player player = new Player(this, keyH);
    TileManager tileM = new TileManager(this);
    public CollisionChecker cChecker = new CollisionChecker(this);

    //FPS
    int FPS = 60;

    //set default's player position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    //World setting
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = titleSize * maxWorldCol; //40*50=2400
    public final int worldHeight = titleSize * maxWorldRow;

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

    public void update(){
        player.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
        player.draw(g2);


        g2.dispose();
    }

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
