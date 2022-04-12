package edu.up.cs301.battleship;

import android.util.Log;

import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

/**
 * Fire - Action that represents firing at a coordinate on the grid.
 *
 * @author Tyler Santos
 * @author Austen Furutani
 * @author Keoni Han
 * @author Steven Lee
 */
public class Fire extends GameAction {

    private Coordinates coord; //the coordinates where a player has fired
    private static final long serialVersionUID = 040420021l;

    /**
     * Fire - constructor that initializes instance variables.
     * @param player - the player
     * @param coord - coordinates where the player wants to fire at
     */
    public Fire(GamePlayer player, Coordinates coord) {
        super(player);
        Log.i("fire action", "Instance of fire action ");
        this.coord = new Coordinates(coord);
    }

    /**
     * setCoord - Sets the coordinate for the Fire Action.
     * @param coord - a given coordinate
     */
    public void setCoord(Coordinates coord) {
        this.coord = new Coordinates(coord);
    }

    /**
     * getCoord - Gets the coordinate the player wants to fire at.
     * @return - the coordinate of the Fire Action
     */
    public Coordinates getCoord() {
        return coord;
    }
}
