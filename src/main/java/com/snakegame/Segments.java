package com.snakegame;

public class Segments {
    private int grid = SnakeGame.getGridSize();
    private int segmentWidth = 1;
    private int segmentsHeight = 1;
    private int snakeX;
    private int snakeY;
    private double speedX = 1;
    private double speedY = 0;

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void setSnakeX(int snakeX) {
        this.snakeX = snakeX;
    }

    public void setSnakeY(int snakeY) {
        this.snakeY = snakeY;
    }

    Segments(int x, int y) {
        this.snakeX = x * grid;
        this.snakeY = y * grid;
    }

    public int getSnakeWidth() {
        return segmentWidth * grid;
    }

    public int getSnakeHeight() {
        return segmentsHeight * grid;
    }

    public int getSnakeX() {
        return snakeX;
    }

    public int getSnakeY() {
        return snakeY;
    }

    public void move() {
        this.snakeX += speedX * grid;
        this.snakeY += speedY * grid;
    }

    public void setSpeedX(double snakeSpeed) {
        this.speedX = snakeSpeed;
    }

    public void setSpeedY(double snakeSpeed) {
        this.speedY = snakeSpeed;
    }

    public void getPreviousSegment(Segments other) {
        this.speedX = other.speedX;
        this.speedY = other.speedY;
        this.snakeX = other.snakeX;
        this.snakeY = other.snakeY;
    }

}
