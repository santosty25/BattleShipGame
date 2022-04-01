package edu.up.cs301.battleship;

import android.util.Log;

import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

public class Fire extends GameAction {

    private Coordinates coord;

    public Fire(GamePlayer player, Coordinates coord) {
        super(player);
        Log.i("fire action", "Instance of fire action ");
        this.coord = new Coordinates(coord);
    }

    public void setCoord(Coordinates coord) {
        this.coord = new Coordinates(coord);
    }

    public Coordinates getCoord() {
        return coord;
    }
}
