package edu.up.cs301.battleship;

import android.util.Log;

import java.io.Serializable;

import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

/**
 * PlaceShip - Action that represents a player placing a ship on the board.
 *
 * @author Keoni Han
 * @author Austen Furutani
 * @author Tyler Santos
 * @author Steven Lee
 * @version Spring 2022 - 3/31/22
 */
public class PlaceShip extends GameAction implements Serializable  {

    private int playerNum; //the player number
    private BattleshipObj battleship; //a given battleship
    private static final long serialVersionUID = 040420022l;

    /**
     * PlaceShip - Constructor that initializes instance variables.
     * @param player - the player
     * @param ship - a ship the player wants to place
     * @param playerNum - the player number
     */
    public PlaceShip(GamePlayer player, BattleshipObj ship, int playerNum) {
        super(player);
        Log.i("Playernum", "PlaceShip: " + playerNum);
        this.battleship = new BattleshipObj(ship);
        this.playerNum = playerNum;
    }

    /**
     * Copy Constructor
     * @param orig - the original PlaceShip Action
     */
    public PlaceShip(PlaceShip orig){
        super(orig.getPlayer());
        this.battleship = new BattleshipObj(orig.battleship);
        this.playerNum = orig.playerNum;
    }

    /**
     * getBattleship - Gets the battleship the player wants to place.
     * @return - the battleship the player wants to place
     */
    public BattleshipObj getBattleship() {
        return battleship;
    }

    /**
     * getPlayerNum - Gets the player number of the player who is
     * placing a ship.
     * @return the player number
     */
    public int getPlayerNum() {
        return playerNum;
    }

    /**
     * getCoord - Gets the coordinates of where the player wants to
     * place a ship.
     * @return - an array of Corrdinates where the player wants to place
     *           a ship
     */
    public Coordinates[] getCoord() {
        Coordinates[] coords = this.battleship.getLocation();
        return coords;
    }

}
