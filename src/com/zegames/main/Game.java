package com.zegames.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import com.zegames.entities.Entity;
import com.zegames.entities.Player;
import com.zegames.graficos.Spritesheet;
import com.zegames.graficos.UI;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener,MouseMotionListener{
    private static final long serialVersionUID = 1L;
    public static JFrame frame;
    private Thread thread;
    private boolean isRunning = true;
    public static final int WIDTH = 120;
    public static final int HEIGHT = 160;
    public static final int SCALE = 3;

    private BufferedImage image;

    //public static World world;
    public static List<Entity> entities;
    public static Spritesheet spritesheet;
    public static Player player;
    public static EnemySpawn enemySpawn;

    public UI ui;
    public BufferedImage background;
    public BufferedImage background2;

    public int backY = 0;
    public int backY2 = 160;
    public int backSpd = 1;

    public static int score = 0;
    public static int life = 5;

    public Game() throws IOException {
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        initFrame();
        image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

        //Inicializando objetos.
        entities = new ArrayList<Entity>();
        spritesheet = new Spritesheet("/spritesheet.png");
        player = new Player(WIDTH/2 - 8,HEIGHT - 9,16,16,1,spritesheet.getSprite(0, 0, 16, 16));
        //world = new World();
        ui = new UI();
        enemySpawn = new EnemySpawn();
        entities.add(player);

        background = ImageIO.read(getClass().getResource("/background.png"));
        background2 = ImageIO.read(getClass().getResource("/background.png"));
    }

    public void initFrame(){
        frame = new JFrame("Space Invaders");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public synchronized void start(){
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop(){
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws IOException {
        Game game = new Game();
        game.start();
    }

    public void tick(){
        for(int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.tick();
        }

        ui.tick();
        enemySpawn.tick();

        backY -= backSpd;

        if (backY + 160 <= 0) {
            backY = 160;
        }

        if (backY2 + 160 <= 0) {
            backY2 = 160;
        }
    }

    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics();
        g.setColor(new Color(0,0,0));
        g.fillRect(0, 0,WIDTH,HEIGHT);
        g.drawImage(background, 0, backY, null);
        g.drawImage(background2, 0, backY2, null);

        /*Renderização do jogo*/
        //Graphics2D g2 = (Graphics2D) g;
        //world.render(g);
        Collections.sort(entities,Entity.nodeSorter);
        for(int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.render(g);
        }
        /***/
        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
        ui.render(g);
        bs.show();
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        requestFocus();
        while(isRunning){
            long now = System.nanoTime();
            delta+= (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1) {
                tick();
                render();
                frames++;
                delta--;
            }

            if(System.currentTimeMillis() - timer >= 1000){
                System.out.println("FPS: "+ frames);
                frames = 0;
                timer+=1000;
            }

        }

        stop();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.right = true;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.left = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.right = false;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.left = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.isShooting = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
