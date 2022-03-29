package edu.up.cs301.battleship;

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
