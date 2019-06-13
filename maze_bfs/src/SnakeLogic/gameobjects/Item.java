package SnakeLogic.gameobjects;

import javafx.scene.paint.Color;

/**
 * superclass for objects drawn
 */

public class Item {
    private Color Color;
    private int x;
    private int y;

    public Item(Color color, int x, int y) {
        Color = color;
        this.x = x;
        this.y = y;
    }

    public Color getColor() {
        return Color;
    }

    public int getX() { return x; }

    public int getY() {
        return y;
    }

}
