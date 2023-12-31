package com.snakegame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private static int panelHeight, panelWidth;
    private static int gridSize = 20;
    private static int scaleSizeX;
    private static int scaleSizeY;
    private int score, highScore;
    private double snakeSpeed = 1;
    private Timer gameLoop;
    private Food apple;
    private static ArrayList<Segments> snakeSegments;
    private boolean gameOver = false;

    public SnakeGame(int panelWidth, int panelHeight) {
        SnakeGame.panelWidth = panelWidth;
        SnakeGame.panelHeight = panelHeight;

        // Frame Configuration
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(SnakeGame.panelWidth, SnakeGame.panelHeight);
        frame.setVisible(true);
        frame.add(this);
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        // Snake Parts
        snakeSegments = new ArrayList<Segments>();
        Segments snakeHead = new Segments(1, 1);
        snakeSegments.add(snakeHead);

        // Food
        apple = new Food();

        // GameLoop
        score = 0;
        highScore = 0;
        gameLoop = new Timer(100, this);
        gameLoop.start();

    }

    public void restart() {
        if (score > highScore) {
            highScore = score;
        }
        score = 0;
        snakeSegments.clear();
        Segments snakeHead = new Segments(1, 1);
        snakeSegments.add(snakeHead);
        apple = new Food();
        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Grid
        // // Veritcal Lines
        // for (int i = 0; i <= (panelWidth / gridSize) - 2; i++) {
        // g.drawLine(i * gridSize, 0, i * gridSize, panelHeight - (2 * gridSize));
        // }

        // // Horizontal Lines
        // for (int i = 0; i <= (panelHeight / gridSize) - 2; i++) {
        // g.drawLine(0, i * gridSize, panelWidth - (gridSize * 1), i * gridSize);
        // }

        // Food
        g.setColor(Color.RED);
        g.fillRect(apple.getFoodX(), apple.getFoodY(), gridSize, gridSize);

        // Snake Head
        Segments head = snakeSegments.get(0);
        g.setColor(Color.YELLOW);
        g.fillRect(head.getSnakeX(), head.getSnakeY(), head.getSnakeWidth(), head.getSnakeHeight());

        // Snake Tail
        for (int i = 0; i < snakeSegments.size(); i++) {
            Segments tail = snakeSegments.get(i);
            g.setColor(Color.YELLOW);
            g.fillRect(tail.getSnakeX(), tail.getSnakeY(), tail.getSnakeWidth(), tail.getSnakeHeight());
        }

        // Score Board
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.setColor(Color.GREEN);
        g.drawString("Score = " + score, 50, 20);

        // Gameover
        if (gameOver) {
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g.setColor(Color.RED);
            g.drawString("GAMEOVER", (panelWidth / 4) + 50, panelHeight / 2);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g.setColor(Color.GREEN);
            g.drawString("press space to restart!", (panelWidth / 4) + 100, (panelHeight / 2) + 30);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g.setColor(Color.GREEN);
            g.drawString("highScore = " + highScore, 150, 20);
            gameLoop.stop();
        }

    }

    // Collisions
    public void checkFoodColisions() {
        Segments head = snakeSegments.get(0);

        if ((head.getSnakeX() / gridSize < (apple.getFoodX() / gridSize) + 0.4 &&
                head.getSnakeX() / gridSize > (apple.getFoodX() / gridSize) - 0.4)
                &&
                (head.getSnakeY() / gridSize < (apple.getFoodY() / gridSize) + 0.4 &&
                        head.getSnakeY() / gridSize > (apple.getFoodY() / gridSize) - 0.4))

        {

            snakeSegments.add(new Segments((apple.getFoodX() / gridSize) + 1, (apple.getFoodY() / gridSize) + 1));
            apple.foodEaten();

            score++;
        }
    }

    public boolean collision(Segments head, Segments part) {
        return head.getSnakeX() == part.getSnakeX() && head.getSnakeY() == part.getSnakeY();
    }

    public void mapLoop() {
        Segments head = snakeSegments.get(0);
        if (head.getSnakeX() == 0) {
            head.setSnakeX(panelWidth);
        } else if (head.getSnakeX() == panelWidth) {
            head.setSnakeX(0);
            ;
        }

        if (head.getSnakeY() == 0) {
            head.setSnakeY(panelHeight);
        } else if (head.getSnakeY() == panelHeight) {
            head.setSnakeY(0);
        }
    }

    // Getter and Setters
    public static int getGridSize() {
        return gridSize;
    }

    public static int getPanelHeight() {
        return panelHeight;
    }

    public static int getPanelWidth() {
        return panelWidth;
    }

    public static int getScaleSizeX() {
        return scaleSizeX;
    }

    public static int getScaleSizeY() {
        return scaleSizeY;
    }

    // Action Listener
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        Segments head = snakeSegments.get(0);
        head.move();
        for (int i = snakeSegments.size() - 1; i >= 0; i--) {
            Segments tail = snakeSegments.get(i);
            if (i == 0) {
                tail.getPreviousSegment(head);
            } else {
                Segments prev = snakeSegments.get(i - 1);
                tail.getPreviousSegment(prev);
            }
        }
        checkFoodColisions();
        // mapLoop();

        if (head.getSnakeX() == 0 || head.getSnakeX() == panelWidth ||
                head.getSnakeY() == 0 || head.getSnakeY() == panelHeight) {
            gameOver = true;
        }

        for (int i = 2; i < snakeSegments.size(); i++) {
            Segments part = snakeSegments.get(i);
            if (collision(head, part)) {
                System.out.println("collision detected");
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if ((keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT)) {
            for (Segments i : snakeSegments) {
                if (i.getSpeedX() == 0) {
                    i.setSpeedX(-snakeSpeed);
                    i.setSpeedY(0);
                }
            }
        } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            for (Segments i : snakeSegments) {
                if (i.getSpeedX() == 0) {
                    i.setSpeedX(snakeSpeed);
                    i.setSpeedY(0);
                }
            }
        }

        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            for (Segments i : snakeSegments) {
                if (i.getSpeedY() == 0) {
                    i.setSpeedX(0);
                    i.setSpeedY(-snakeSpeed);
                }
            }
        } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            for (Segments i : snakeSegments) {
                if (i.getSpeedY() == 0) {
                    i.setSpeedX(0);
                    i.setSpeedY(snakeSpeed);
                }
            }
        }

        if (keyCode == KeyEvent.VK_SPACE && gameOver) {
            // snakeSpeed += 0.5;
            gameOver = false;
            restart();
        }
    }

    // Method Dump
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
