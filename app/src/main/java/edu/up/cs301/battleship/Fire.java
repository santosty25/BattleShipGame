package edu.up.cs301.battleship;

import android.util.Log;

import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

public class Fire extends GameAction {

    private int x;
    private int y;
    private Coordinates coord;

    public Fire(GamePlayer player) {
        super(player);
        Log.i("fire action", "Fire: test");
        }
    public Fire(GamePlayer player, Coordinates coord) {
        super(player);
        Log.i("fire action", "Instance of fire action ");
        this.coord = new Coordinates(coord);
    }
    public void setX(int setX) {
        this.x = setX;
    }

    public void setY(int setY) {
        this.y = setY;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setCoord(Coordinates coord) {
        this.coord = coord;
    }

    public Coordinates getCoord() {
        return coord;
    }
}
