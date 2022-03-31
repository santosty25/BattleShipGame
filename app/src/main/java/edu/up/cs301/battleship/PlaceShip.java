package edu.up.cs301.battleship;

import android.util.Log;

import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.players.GamePlayer;

public class PlaceShip extends GameAction {

    private int playerNum;
    private BattleshipObj battleship;

    //make constructor param take a battleship obj
    public PlaceShip(GamePlayer player, BattleshipObj ship, int playerNum) {
        super(player);
        Log.i("Playernum", "PlaceShip: " + playerNum);
        this.battleship = new BattleshipObj(ship);
        this.playerNum = playerNum;
    }
    public PlaceShip(PlaceShip orig){
        super(orig.getPlayer());
        this.battleship = new BattleshipObj(orig.battleship);
        this.playerNum = orig.playerNum;
    }


    public BattleshipObj getBattleship() {
        return battleship;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public Coordinates[] getCoord() {
        Coordinates[] coords = this.battleship.getLocation();
        return coords;
    }

}
