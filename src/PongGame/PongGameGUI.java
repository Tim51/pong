package PongGame;


import Keyboard.*;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PongGameGUI implements Runnable {

    private Thread t;

    private JFrame frame;
    private Dimension gameDimension;
    private PongPanel pongPanel;
    private PongGame pongGame;

    public PongGameGUI() throws InterruptedException {
        initComponents();

        this.start();
        
        pongGame.waitKeyboard();
        pongGame.startMenuScreen();

    }

    public void initComponents() {
        frame = new JFrame("Pong");
        gameDimension = new Dimension(600, 300);
        pongGame = new PongGame(gameDimension);
        pongPanel = new PongPanel(gameDimension, pongGame,frame);

        frame.setDefaultCloseOperation(3);
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(600, 330));
        frame.setLocation(300, 150);
        frame.getContentPane().add(pongPanel);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void run() {
        KeyboardManager keyboardManager = new Keyboard.KeyboardManager(pongPanel);
        while (true) {
            if (pongGame.isLoaded()) {
                pongGame.setKeyboardManager(keyboardManager);
                break;
            }
        }

        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(PongGameGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            pongPanel.repaint();
        }

    }

    public void start() {
        if (t == null) {
            t = new Thread(this, "Graphics");
            t.start();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PongGameGUI ui = new PongGameGUI();
        SwingUtilities.invokeLater(ui);
    }
}
