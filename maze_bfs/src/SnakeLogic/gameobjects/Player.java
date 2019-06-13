package SnakeLogic.gameobjects;
/**
 *superclass for ghost objects. still here in the case that other kinds of player should be used
 */
public class Player {

    private int X; // PlayerPos
    private int Y; // PlayerPos

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }
}
