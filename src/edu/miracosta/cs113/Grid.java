package edu.miracosta.cs113;

import java.awt.*;

public class Grid {
    private Color color;
    private int width;
    private int height;

    // Constructors

    public Grid(Color color) {
        this.color = color;
        width = 5;
        height = 5;
    }

    public Grid(Color color, int width, int height) {
        this.color = color;
        this.width = width;
        this.height = height;
    }

    // accessor/mutator for grid's color
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    // accessor/mutator for grid's width
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    // accessor/mutator for grid's height
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
}
