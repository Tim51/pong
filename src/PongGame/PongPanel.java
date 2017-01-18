package PongGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PongPanel extends JPanel {

    private final Dimension gameDimension;
    private final PongGame pongGame;
    private BufferedImage menuTitle;
    private JFrame frame;

    PongPanel(Dimension gameDimension, PongGame pongGame, JFrame frame) {
        this.gameDimension = gameDimension;
        this.pongGame = pongGame;
        this.menuTitle = null;
        this.frame = frame;

        loadImages();
    }

    public void loadImages() {
        try {
            menuTitle = ImageIO.read(new File("PongMenuTitle.jpg"));
            System.out.println("load successful");
        } catch (IOException e) {
            System.out.println("load failed");
        }
    }

    public void paintComponent(Graphics g) {

        if (pongGame.getCurrentScreen().equals("menu")) {
            paintBackground(g);
            paintMenuTitle(g);
            paintMenuGrid(g);
        }
        
        if (pongGame.getCurrentScreen().equals("settings")) {
            paintBackground(g);
            paintMenuTitle(g);
            paintSettingsGrid(g);
        }
        
        if (pongGame.getCurrentScreen().equals("game")) {
            if (pongGame.getGameOn()) {
                paintBackground(g);
                paintBall(g);
                paintPaddles(g);
                paintWalls(g);
                paintScores(g);
                if (pongGame.getRoundStarting()) {
                    paintRoundStarting(g);
                }
            } else if (!pongGame.getWinner().equals("none")) {
                paintWinner(g);
                paintScores(g);
            }
        }
        
        if (pongGame.getCurrentScreen().equals("quit")) {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void paintMenuTitle(Graphics g) {
        g.drawImage(menuTitle, (int)gameDimension.width/2 - 145, 30, null);
    }
    
    public void paintMenuGrid(Graphics g) {
        g.setColor(Color.darkGray);
        for (int x = 1; x <= pongGame.getMenuGrid().getWidth(); x++) {
            for (int y = 1; y <= pongGame.getMenuGrid().getHeight(); y++) {
                g.drawRect((int)gameDimension.width/2 - 140 + 40*x, 100 + 40*y, 180, 30);
            }
        }
        g.setColor(Color.white);
        g.drawRect((int)gameDimension.width/2 - 140 + 40*pongGame.getMenuGrid().getPointerXPosition(),100 + 40*pongGame.getMenuGrid().getPointerYPosition(), 180, 30);
        g.drawString("NEW GAME", (int)gameDimension.width/2 - 40, 160);
        g.drawString("SETTINGS", (int)gameDimension.width/2 - 38, 200);
        g.drawString("QUIT", (int)gameDimension.width/2 - 22, 240);
    }
    
    public void paintSettingsGrid(Graphics g) {
        g.setColor(Color.green);
        g.fillRect((int)gameDimension.width/2 + 10, 145, pongGame.getPaddleWidth(), 20);
        g.fillRect((int)gameDimension.width/2 + 10, 185, (int)(pongGame.getPaddleSpeed()*20), 20);
        
        g.setColor(Color.darkGray);
        for (int x = 1; x <= pongGame.getSettingsGrid().getWidth(); x++) {
            for (int y = 1; y <= pongGame.getSettingsGrid().getHeight(); y++) {
                g.drawRect((int)gameDimension.width/2 - 140 + 40*x, 100 + 40*y, 180, 30);
            }
        }
        g.drawRect((int)gameDimension.width/2 + 10, 145, 60 , 20);
        g.drawRect((int)gameDimension.width/2 + 10, 185, 60 , 20);
        
        g.setColor(Color.white);
        g.drawRect((int)gameDimension.width/2 - 140 + 40*pongGame.getSettingsGrid().getPointerXPosition(),100 + 40*pongGame.getSettingsGrid().getPointerYPosition(), 180, 30);
        g.drawString("PADDLE WIDTH", (int)gameDimension.width/2 - 88, 160);
        g.drawString("PADDLE SPEED", (int)gameDimension.width/2 - 88, 200);
        g.drawString("BACK", (int)gameDimension.width/2 - 22, 240);
        
        if (pongGame.getSettingsGrid().getPointerYPosition()==1) {
            g.drawRect((int)gameDimension.width/2 + 10, 145, 60 , 20);
        }
        if (pongGame.getSettingsGrid().getPointerYPosition()==2) {
            g.drawRect((int)gameDimension.width/2 + 10, 185, 60 , 20);
        }
        
    }
    
    public void paintBackground(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, (int) gameDimension.getWidth(), (int) gameDimension.getHeight());
    }

    public void paintBall(Graphics g) {
        g.setColor(Color.white);
        Rectangle hitBox = pongGame.getBall().getHitBox();
        g.fill3DRect((int) hitBox.getX(), (int) hitBox.getY(), (int) hitBox.getWidth(), (int) hitBox.getHeight(), true);
    }

    public void paintPaddles(Graphics g) {
        g.setColor(Color.white);
        Rectangle hitBox1 = pongGame.getPlayer1().getHitBox();
        g.fill3DRect((int) hitBox1.getX(), (int) hitBox1.getY(), (int) hitBox1.getWidth(), (int) hitBox1.getHeight(), true);
        Rectangle hitBox2 = pongGame.getPlayer2().getHitBox();
        g.fill3DRect((int) hitBox2.getX(), (int) hitBox2.getY(), (int) hitBox2.getWidth(), (int) hitBox2.getHeight(), true);
    }

    public void paintScores(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(17, 10, 12, 14);
        g.fillRect((int) gameDimension.getWidth() - 33, 10, 12, 14);
        
        g.setColor(Color.white);
        //g.setFont(new Font("Serif", Font.BOLD, 14));
        g.drawString("" + pongGame.getPoints1(), 20, 22);
        g.drawString("" + pongGame.getPoints2(), (int) gameDimension.getWidth() - 30, 22);
    }

    public void paintRoundStarting(Graphics g) {
        g.setColor(Color.white);
        if (pongGame.getRoundStarting()) {
            g.drawString("GET READY!", (int) gameDimension.getWidth() / 2 - 35, 120);
        }
    }

    public void paintWinner(Graphics g) {
        g.setColor(Color.white);
        g.drawString(pongGame.getWinner() + " WINS!", (int) gameDimension.getWidth() / 2 - 50, 140);
        g.drawString("(PRESS SPACE TO RETURN TO MENU)", 185, 170);
    }

    public void paintWalls(Graphics g) {
        float[] HSB = Color.RGBtoHSB(222, 222, 222, null);
        g.setColor(Color.getHSBColor(HSB[0], HSB[1], HSB[2]));
        for (Wall wall : pongGame.getWalls()) {
            g.fill3DRect((int) wall.getX(), (int) wall.getY(), (int) wall.getHitBox().getWidth(), (int) wall.getHitBox().getHeight(), true);
        }
    }

}
