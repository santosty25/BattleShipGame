package edu.up.cs301.battleship;

/**
 * TapValues - A class that represents the values of where the player
 * has touched on the grid.
 *
 * @author Austen Furutani
 * @author Keoni Han
 * @author Tyler Santos
 * @author Steven Lee
 * @version Spring 2022 - 3/31/22
 */
public class TapValues {
    private int x;
    private int y;
    private boolean hit;

    public TapValues() {
        x = 0;
        y = 0;
        hit = false;

    }
    public TapValues(int x, int y, boolean hit){
        this.x = x;
        this.y = y;
        this.hit = hit;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
