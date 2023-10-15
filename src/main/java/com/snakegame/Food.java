package com.snakegame;

public class Food {
    private int grid = SnakeGame.getGridSize();
    private int foodX;
    private int foodY;

    public Food() {
        setFoodX();
        setFoodY();
    }

    public int getFoodX() {
        return foodX;
    }

    public int getFoodY() {
        return foodY;
    }

    public void setFoodX() {
        int random = (int) (Math.random() * (SnakeGame.getPanelWidth() / grid)) + 0;
        this.foodX = random * grid;
    }

    public void setFoodY() {
        int random = (int) (Math.random() * (SnakeGame.getPanelHeight() / grid)) + 0;
        this.foodY = random * grid;
    }

    public void foodEaten() {
        setFoodX();
        setFoodY();
    }
}
