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
    private Timer gameLoop;
    private Food apple;
    private static ArrayList<Segments> snakeSegments;

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

        // Snake Parts
        snakeSegments = new ArrayList<Segments>();
        Segments snakeHead = new Segments(1, 1);
        snakeSegments.add(snakeHead);

        // Food
        apple = new Food();

        // GameLoop
        gameLoop = new Timer(100, this);
        gameLoop.start();
        addKeyListener(this);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Grid
        // for (int i = 0; i <= panelWidth / gridSize; i++) {
        // g.drawLine(i * gridSize, 0, i * gridSize, panelHeight);
        // }
        // for (int i = 0; i <= panelHeight / gridSize; i++) {
        // g.drawLine(0, i * gridSize, panelWidth, i * gridSize);
        // }

        // Food
        g.setColor(Color.RED);
        g.fillRect(apple.getFoodX(), apple.getFoodY(), gridSize, gridSize);

        // Snake Head
        Segments head = snakeSegments.get(0);
        g.setColor(Color.YELLOW);
        g.fillRect(head.getSnakeX(), head.getSnakeY(), head.getSnakeWidth(), head.getSnakeHeight());

        // Snake Tail
        for (int i = 1; i < snakeSegments.size(); i++) {
            Segments tail = snakeSegments.get(i);
            g.setColor(Color.YELLOW);
            g.fillRect(tail.getSnakeX(), tail.getSnakeY(), tail.getSnakeWidth(), tail.getSnakeHeight());
        }

        // for (Segments i : snakeSegments) {
        // g.setColor(Color.YELLOW);
        // g.fillRect(i.getSnakeX(), i.getSnakeY(), i.getSnakeWidth(),
        // i.getSnakeHeight());
        // }

    }

    // Collisions
    public void checkFoodColisions() {
        Segments head = snakeSegments.get(0);

        if (head.getSnakeX() / gridSize == apple.getFoodX() / gridSize
                && head.getSnakeY() / gridSize == apple.getFoodY() / gridSize) {
            snakeSegments.add(new Segments(apple.getFoodX() / gridSize, apple.getFoodY() / gridSize));
            apple.foodEaten();
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
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            for (Segments i : snakeSegments) {
                i.setSpeedX(-1);
                i.setSpeedY(0);
            }
        } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            for (Segments i : snakeSegments) {
                i.setSpeedX(1);
                i.setSpeedY(0);
            }
        }

        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            for (Segments i : snakeSegments) {
                i.setSpeedX(0);
                i.setSpeedY(-1);
            }
        } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            for (Segments i : snakeSegments) {
                i.setSpeedX(0);
                i.setSpeedY(1);
            }
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
