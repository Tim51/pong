package PongGame;

import Keyboard.KeyboardManager;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class PongGame {

    private final Dimension gameDimension;
    private boolean loaded;
    private KeyboardManager keyboardManager;
    private String currentScreen;
    //Menu elements
    private Grid menuGrid;
    private Grid settingsGrid;
    //Game elements
    private Ball ball;
    private Paddle player1, player2;
    private Wall upperWall, lowerWall;
    private ArrayList<Wall> walls;
    //Dont Change
    private int tickCount;
    private int points1, points2;
    private boolean roundStarting;
    private boolean gameOn;
    private String winner;
    //Can Change
    private final int roundsToWin = 3;
    private int paddleWidth;
    private double paddleSpeed;
    private final double speedCurveFactor = 0;
    private final double paddleCurveMultiplier = 0.9;
    private final double wallCurveMultiplier = 0.9;
    private final double paddleAngleMultiplier = 0.2;

    public PongGame(Dimension gameDimension) {
        this.gameDimension = gameDimension;
        this.loaded = true;
        this.gameOn = false;
        this.winner = "none";
        this.currentScreen = "menu";

        this.paddleWidth = 30;
        this.paddleSpeed = 1.5;
        loadSettings();
        
        initiateMenuGrid();
        initiateSettingsGrid();
    }

    public void loadSettings() {
        File file = new File("settings.txt");
        if (file.exists()) {
            try {
                Scanner reader = new Scanner(file.getAbsoluteFile());
                this.paddleWidth = Integer.parseInt(reader.nextLine().split(" ")[1]);
                this.paddleSpeed = Double.parseDouble(reader.nextLine().split(" ")[1]);
                System.out.println("Settings loaded");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PongGame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void saveSettings() {
        try {
            File file = new File("settings.txt");
            if (!file.exists()) {
                file.createNewFile();
                file.getAbsolutePath();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            bw.write("PaddleWidth= " + paddleWidth);
            bw.newLine();
            bw.write("PaddleSpeed= " + paddleSpeed);
            bw.close();
            System.out.println("saved");
        } catch (IOException ex) {
            Logger.getLogger(PongGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void waitKeyboard() {
        while (keyboardManager == null) {
            System.out.print("");
        }
    }

    public void initiateMenuGrid() {
        menuGrid = new Grid(1, 3, false, true);
        menuGrid.set(1, 1, "newGame");
        menuGrid.set(1, 2, "settings");
        menuGrid.set(1, 3, "quit");
    }

    public void initiateSettingsGrid() {
        settingsGrid = new Grid(1, 3, false, true);
        settingsGrid.set(1, 1, "paddleWidth");
        settingsGrid.set(1, 2, "paddleSpeed");
        settingsGrid.set(1, 3, "back");
    }

    public String getCurrentScreen() {
        return currentScreen;
    }

    public void startMenuScreen() {
        initiateMenuGrid();
        menuGrid.setPointer(1, 1);
        while (true) {
            if (keyboardManager.keyPressed(KeyCode.W) || keyboardManager.keyPressed(KeyCode.UP)) {
                menuGrid.movePointerUp();
            }
            if (keyboardManager.keyPressed(KeyCode.S) || keyboardManager.keyPressed(KeyCode.DOWN)) {
                menuGrid.movePointerDown();
            }
            if (keyboardManager.keyPressed(KeyCode.SPACE)) {
                if (menuGrid.getPointerValue().equals("newGame")) {
                    currentScreen = "game";
                    startNewGame();
                }
                if (menuGrid.getPointerValue().equals("settings")) {
                    currentScreen = "settings";
                    startSettingsScreen();
                }
                if (menuGrid.getPointerValue().equals("quit")) {
                    currentScreen = "quit";
                }
            }
        }
    }

    public void startSettingsScreen() {
        initiateSettingsGrid();
        settingsGrid.setPointer(1, 1);
        while (true) {
            if (keyboardManager.keyPressed(KeyCode.W) || keyboardManager.keyPressed(KeyCode.UP)) {
                settingsGrid.movePointerUp();
            }
            if (keyboardManager.keyPressed(KeyCode.S) || keyboardManager.keyPressed(KeyCode.DOWN)) {
                settingsGrid.movePointerDown();
            }
            if (keyboardManager.keyPressed(KeyCode.D) || keyboardManager.keyPressed(KeyCode.RIGHT)) {
                if (settingsGrid.getPointerValue().equals("paddleWidth")) {
                    paddleWidth = Math.min(60, paddleWidth + 10);
                }
                if (settingsGrid.getPointerValue().equals("paddleSpeed")) {
                    paddleSpeed = Math.min(3, paddleSpeed + 0.5);
                }
            }
            if (keyboardManager.keyPressed(KeyCode.A) || keyboardManager.keyPressed(KeyCode.LEFT)) {
                if (settingsGrid.getPointerValue().equals("paddleWidth")) {
                    paddleWidth = Math.max(10, paddleWidth - 10);
                }
                if (settingsGrid.getPointerValue().equals("paddleSpeed")) {
                    paddleSpeed = Math.max(0.5, paddleSpeed - 0.5);
                }
            }
            if (keyboardManager.keyPressed(KeyCode.SPACE)) {
                saveSettings();
                if (settingsGrid.getPointerValue().equals("back")) {
                    currentScreen = "menu";
                    startMenuScreen();
                }
            }
        }
    }

    public void startNewGame() {
        //Reset variables
        gameOn = false;
        winner = "none";
        points1 = 0;
        points2 = 0;
        tickCount = 0;

        //Add walls
        this.walls = new ArrayList<>();
        upperWall = new Wall(0, -10, (int) gameDimension.getWidth(), 40);
        lowerWall = new Wall(0, (int) gameDimension.getHeight() - 30, (int) gameDimension.getWidth(), 40);
        this.walls.add(upperWall);
        this.walls.add(lowerWall);

        //Add ball
        ball = new Ball(gameDimension.width / 2, gameDimension.height / 2, 5);
        ball.setRandomVelocity();

        //Add paddles
        player1 = new Paddle(50, 150, 8, paddleWidth);
        player2 = new Paddle(gameDimension.width - 50, 150, 8, paddleWidth);

        this.gameOn = true;
        roundStarting = true;

        while (gameOn) {
            tick();
            if (tickCount > 250) {
                this.updateBall();
                roundStarting = false;
            }

            updatePaddles();
            updateScores();

            if (hasWinner()) {
                roundStarting = false;
                break;
            }
        }
        gameOn = false;
        while (true) {
            if (keyboardManager.keyPressed(KeyCode.SPACE)) {
                break;
            }
        }
        currentScreen = "menu";
        keyboardManager.keyPressed(KeyCode.SPACE);
        startMenuScreen();
    }

    public boolean hasWinner() {
        return (points1 >= roundsToWin || points2 >= roundsToWin);
    }

    public void tick() {
        try {
            Thread.sleep(10);
            tickCount++;
        } catch (InterruptedException ex) {
            Logger.getLogger(PongGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateBall() {
        if (ball.hasCollisionWith(player1)) {
            if (ball.getXVel() < 0) {
                //reflect
                ball.setDirection(-ball.getDirection() + Math.PI);
                //paddle angle
                double dy = ball.getHitBox().getCenterY() - player1.getHitBox().getCenterY();
                ball.setDirection(ball.getDirection() + (dy / player1.getHitBox().getHeight()) * paddleAngleMultiplier * Math.PI);

                ball.setSpin(ball.getSpin() * paddleCurveMultiplier);
                ball.setSpin(ball.getSpin() - player1.getYVel() * speedCurveFactor);

                ball.setSpeed(ball.getSpeed() + 0.2);
            }
        }
        if (ball.hasCollisionWith(player2)) {
            if (ball.getXVel() > 0) {
                //reflect
                ball.setDirection(-ball.getDirection() + Math.PI);
                //paddle angle
                double dy = ball.getHitBox().getCenterY() - player2.getHitBox().getCenterY();
                ball.setDirection(ball.getDirection() - (dy / player2.getHitBox().getHeight()) * paddleAngleMultiplier * Math.PI);

                ball.setSpin(ball.getSpin() * paddleCurveMultiplier);
                ball.setSpin(ball.getSpin() - player2.getYVel() * speedCurveFactor);

                ball.setSpeed(ball.getSpeed() + 0.2);
            }
        }
        if (ball.hasCollisionWith(upperWall)) {
            if (ball.getYVel() < 0) {
                ball.setSpin(ball.getSpin() * wallCurveMultiplier);
                ball.setDirection(-ball.getDirection());
            }
        }
        if (ball.hasCollisionWith(lowerWall)) {
            if (ball.getYVel() > 0) {
                ball.setSpin(ball.getSpin() * wallCurveMultiplier);
                ball.setDirection(-ball.getDirection());
            }
        }

        ball.move();
    }

    public void updatePaddles() {
        //<editor-fold defaultstate="collapsed" desc="Update paddle 1 speed">
        if (keyboardManager.keyDown(KeyCode.W) && !keyboardManager.keyDown(KeyCode.S)) {
            player1.setYVel(-paddleSpeed);
        } else if (keyboardManager.keyDown(KeyCode.S) && !keyboardManager.keyDown(KeyCode.W)) {
            player1.setYVel(paddleSpeed);
        } else {
            player1.setYVel(0);
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Update paddle 2 speed">
        if (keyboardManager.keyDown(KeyCode.UP) && !keyboardManager.keyDown(KeyCode.DOWN)) {
            player2.setYVel(-paddleSpeed);
        } else if (keyboardManager.keyDown(KeyCode.DOWN) && !keyboardManager.keyDown(KeyCode.UP)) {
            player2.setYVel(paddleSpeed);
        } else {
            player2.setYVel(0);
        }
        //</editor-fold>

        this.player1.move();
        this.player2.move();

        if (player1.hasCollisionWith(upperWall) && player1.getYVel() < 0) {
            player1.setY(upperWall.getHitBox().getMaxY() + player1.getHitBox().getHeight() * 0.245);
            player1.setYVel(0);
        }
        if (player1.hasCollisionWith(lowerWall) && player1.getYVel() > 0) {
            player1.setY(lowerWall.getHitBox().getMinY() - player1.getHitBox().getHeight() * 0.737);
            player1.setYVel(0);
        }
        if (player2.hasCollisionWith(upperWall) && player2.getYVel() < 0) {
            player2.setY(upperWall.getHitBox().getMaxY() + player2.getHitBox().getHeight() * 0.245);
            player2.setYVel(0);
        }
        if (player2.hasCollisionWith(lowerWall) && player2.getYVel() > 0) {
            player2.setY(lowerWall.getHitBox().getMinY() - player2.getHitBox().getHeight() * 0.737);
            player2.setYVel(0);
        }
    }

    public void updateScores() {
        if (ball.getX() < 0) {
            points2++;
            ball = null;
            ball = new Ball(gameDimension.width / 2, 150, 5);
            ball.setRandomVelocity();
            tickCount = 0;
            roundStarting = true;
        }
        if (ball.getX() > gameDimension.width) {
            points1++;
            ball = null;
            ball = new Ball(gameDimension.width / 2, 150, 5);
            ball.setRandomVelocity();
            tickCount = 0;
            roundStarting = true;
        }
        if (points1 >= roundsToWin) {
            winner = "PLAYER 1";
        }
        if (points2 >= roundsToWin) {
            winner = "PLAYER 2";
        }

    }

    public Ball getBall() {
        return ball;
    }

    public Paddle getPlayer1() {
        return player1;
    }

    public Paddle getPlayer2() {
        return player2;
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public int getPoints1() {
        return points1;
    }

    public int getPoints2() {
        return points2;
    }

    public boolean getRoundStarting() {
        return roundStarting;
    }

    public boolean getGameOn() {
        return gameOn;
    }

    public String getWinner() {
        return winner;
    }

    public void setKeyboardManager(KeyboardManager keyboardManager) {
        this.keyboardManager = keyboardManager;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public Grid getMenuGrid() {
        return menuGrid;
    }

    public Grid getSettingsGrid() {
        return settingsGrid;
    }

    public int getPaddleWidth() {
        return paddleWidth;
    }

    public double getPaddleSpeed() {
        return paddleSpeed;
    }
}
