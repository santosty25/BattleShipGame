package edu.up.cs301.battleship;

public class TapValues {
    private int x;
    private int y;

    public TapValues() {
        x = 0;
        y = 0;

    }
    public TapValues(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
